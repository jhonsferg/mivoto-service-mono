# DS-03: Verificación de Voto

## Diagrama de Secuencia

```plantuml
@startuml
autonumber
actor "Usuario" as user
participant "Controller\nVoteController" as controller
participant "Service\nVoteService" as service
participant "DataStructure\nVoteRecordList" as recordList
participant "Security\nHashValidator" as validator
participant "Audit\nAuditService" as audit
database "PostgreSQL" as db

== Verificación de Voto ==
user -> controller: GET /api/votes/verify/{voteHash}
activate controller
note right: No requiere autenticación\n(endpoint público)

controller -> controller: validateHashFormat(voteHash)

alt Formato inválido
    controller --> user: 400 Bad Request\n"Formato de hash inválido"
else Formato válido
    controller -> service: verifyVote(voteHash)
    activate service

    == Búsqueda en Lista Enlazada ==
    service -> recordList: findByHash(voteHash)
    activate recordList
    recordList -> recordList: traverseList()
    note right: Búsqueda lineal O(n)\nen VoteRecordList

    alt Voto no encontrado
        recordList --> service: null
        service --> controller: throw VoteNotFoundException
        controller --> user: 404 Not Found
    else Voto encontrado
        recordList --> service: VoteRecord
        deactivate recordList

        == Validación de Integridad ==
        service -> validator: validateIntegrity(voteRecord)
        activate validator
        validator -> validator: recalculateHash()
        validator -> validator: compareHashes()
        validator --> service: boolean
        deactivate validator

        alt Integridad comprometida
            service -> audit: logIntegrityViolation(voteHash)
            service --> controller: throw IntegrityException
            controller --> user: 500 Internal Server Error
        else Integridad válida
            == Obtener Detalles ==
            service -> service: buildVerificationResponse(voteRecord)

            service -> audit: logVoteVerification(voteHash)
            activate audit
            audit -> db: INSERT INTO audit_logs\n(action='VOTE_VERIFICATION')
            db --> audit: OK
            audit --> service: void
            deactivate audit

            service --> controller: VoteVerificationResponse
        end
    end
end

controller --> user: 200 OK\n{voteHash, electionDetails, timestamp, verified}
deactivate service
deactivate controller

@enduml
```

## Descripción del Flujo

### Actores y Componentes

- **Usuario**: Cualquier persona (no requiere autenticación)
- **VoteController**: Controlador REST para verificación
- **VoteService**: Servicio de verificación de votos
- **VoteRecordList**: Lista enlazada con registros de votos
- **HashValidator**: Validador de integridad de hash
- **AuditService**: Servicio de auditoría
- **PostgreSQL**: Base de datos para auditoría

### Pasos del Flujo

1. **Recepción de Hash**: Usuario proporciona hash del voto
2. **Validación de Formato**: Verifica patrón hexadecimal de 64 caracteres
3. **Búsqueda en VoteRecordList**: Recorre la lista enlazada
4. **Validación de Integridad**: Recalcula hash y compara
5. **Registro de Auditoría**: Registra la verificación
6. **Respuesta**: Retorna detalles del voto verificado

### Estructura de Datos Utilizada

**VoteRecordList (Lista Enlazada)**:

```
[Head] -> [VoteRecord] -> [VoteRecord] -> [VoteRecord] -> null
          hash: abc123    hash: def456    hash: ghi789
          timestamp       timestamp       timestamp
          electionId      electionId      electionId
```

- **Operación**: `findByHash(hash)` - O(n)
- **Propósito**: Búsqueda de votos por hash
- **Implementación**: Recorrido secuencial desde head

### Validación de Integridad

El hash se recalcula usando:

```
SHA256(voteId + timestamp + electionId + salt)
```

Si el hash recalculado coincide con el almacenado, el voto es íntegro.

### Reglas de Negocio Aplicadas

- RN-19: Verificación pública (sin autenticación)
- RN-20: Hash único e inmutable
- RN-21: No revela identidad del votante
- RN-22: Todas las verificaciones son auditadas

### Consideraciones de Privacidad

- No se expone información del votante
- Solo se muestra: elección, timestamp, hash
- Opcionalmente se puede mostrar el candidato (configurable)
