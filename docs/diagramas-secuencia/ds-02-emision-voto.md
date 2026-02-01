# DS-02: Emisión de Voto

## Diagrama de Secuencia

```plantuml
@startuml
autonumber
actor "Votante" as voter
participant "Controller\nVoteController" as controller
participant "Service\nVoteService" as service
participant "Repository\nVoteRepository" as voteRepo
participant "Repository\nElectionRepository" as electionRepo
participant "Repository\nCandidateRepository" as candidateRepo
participant "DataStructure\nVoteQueue" as queue
participant "DataStructure\nVoteRecordList" as recordList
participant "Security\nHashGenerator" as hash
participant "Audit\nAuditService" as audit
database "PostgreSQL" as db
participant "Cache\nRedis" as redis

== Emisión de Voto ==
voter -> controller: POST /api/votes\n{electionId, candidateId}
activate controller

controller -> controller: validateJwtToken()
controller -> controller: extractUserId()

controller -> service: castVote(userId, VoteRequest)
activate service

== Validaciones ==
service -> electionRepo: findById(electionId)
activate electionRepo
electionRepo -> db: SELECT * FROM elections WHERE id = ?
db --> electionRepo: Election entity
electionRepo --> service: Optional<Election>
deactivate electionRepo

alt Elección no encontrada
    service --> controller: throw ElectionNotFoundException
    controller --> voter: 404 Not Found
else Elección encontrada
    service -> service: validateElectionStatus()

    alt Elección no activa
        service --> controller: throw ElectionNotActiveException
        controller --> voter: 400 Bad Request
    else Elección activa
        service -> voteRepo: existsByUserIdAndElectionId(userId, electionId)
        activate voteRepo
        voteRepo -> db: SELECT COUNT(*) FROM votes WHERE...
        db --> voteRepo: count
        voteRepo --> service: boolean
        deactivate voteRepo

        alt Usuario ya votó
            service --> controller: throw DuplicateVoteException
            controller --> voter: 409 Conflict
        else Usuario no ha votado
            service -> candidateRepo: findById(candidateId)
            activate candidateRepo
            candidateRepo -> db: SELECT * FROM candidates WHERE id = ?
            db --> candidateRepo: Candidate entity
            candidateRepo --> service: Optional<Candidate>
            deactivate candidateRepo

            alt Candidato no encontrado
                service --> controller: throw CandidateNotFoundException
                controller --> voter: 404 Not Found
            else Candidato encontrado
                service -> service: validateCandidateBelongsToElection()

                alt Candidato no pertenece a elección
                    service --> controller: throw InvalidCandidateException
                    controller --> voter: 400 Bad Request
                else Validación exitosa
                    == Registro de Voto ==
                    service -> service: createVote(userId, electionId, candidateId)

                    service -> hash: generateVoteHash(vote)
                    activate hash
                    hash -> hash: SHA256(voteId + timestamp + salt)
                    hash --> service: voteHash
                    deactivate hash

                    service -> voteRepo: save(vote)
                    activate voteRepo
                    voteRepo -> db: INSERT INTO votes VALUES (...)
                    db --> voteRepo: Vote entity
                    voteRepo --> service: Vote
                    deactivate voteRepo

                    == Estructuras de Datos ==
                    service -> queue: enqueue(vote)
                    activate queue
                    queue -> queue: addLast(vote)
                    queue --> service: void
                    deactivate queue

                    service -> recordList: addFirst(voteRecord)
                    activate recordList
                    recordList -> recordList: insertAtHead(voteRecord)
                    recordList --> service: void
                    deactivate recordList

                    == Actualización de Contadores ==
                    service -> candidateRepo: incrementVoteCount(candidateId)
                    activate candidateRepo
                    candidateRepo -> db: UPDATE candidates\nSET vote_count = vote_count + 1
                    db --> candidateRepo: OK
                    candidateRepo --> service: void
                    deactivate candidateRepo

                    service -> electionRepo: incrementVoteCount(electionId)
                    activate electionRepo
                    electionRepo -> db: UPDATE elections\nSET total_votes = total_votes + 1
                    db --> electionRepo: OK
                    electionRepo --> service: void
                    deactivate electionRepo

                    == Cache y Auditoría ==
                    service -> redis: set("vote:" + userId + ":" + electionId, true)
                    activate redis
                    redis --> service: OK
                    deactivate redis

                    service -> audit: logVoteCast(vote)
                    activate audit
                    audit -> db: INSERT INTO audit_logs
                    db --> audit: OK
                    audit --> service: void
                    deactivate audit

                    service --> controller: VoteResponse(voteHash, details)
                end
            end
        end
    end
end

controller --> voter: 201 Created\n{voteHash, timestamp, verificationUrl}
deactivate service
deactivate controller

@enduml
```

## Descripción del Flujo

### Actores y Componentes

- **Votante**: Usuario autenticado con rol VOTER
- **VoteController**: Controlador REST para votación
- **VoteService**: Servicio de aplicación que orquesta el voto
- **VoteRepository**: Repositorio de votos
- **ElectionRepository**: Repositorio de elecciones
- **CandidateRepository**: Repositorio de candidatos
- **VoteQueue**: Cola FIFO para procesamiento de votos
- **VoteRecordList**: Lista enlazada para registro cronológico
- **HashGenerator**: Generador de hash SHA-256
- **AuditService**: Servicio de auditoría
- **PostgreSQL**: Base de datos principal
- **Redis**: Cache para optimización

### Pasos del Flujo

1. **Autenticación**: Validación del token JWT
2. **Validación de Elección**: Verifica que exista y esté activa
3. **Verificación de Voto Duplicado**: Consulta si ya votó
4. **Validación de Candidato**: Verifica existencia y pertenencia
5. **Creación de Voto**: Genera el registro de voto
6. **Generación de Hash**: Crea hash SHA-256 único
7. **Persistencia**: Guarda en PostgreSQL
8. **Encolado**: Agrega a VoteQueue (FIFO)
9. **Registro Cronológico**: Inserta en VoteRecordList
10. **Actualización de Contadores**: Incrementa votos del candidato y elección
11. **Cache**: Marca en Redis que el usuario votó
12. **Auditoría**: Registra el evento
13. **Respuesta**: Retorna hash de verificación

### Estructuras de Datos Utilizadas

**VoteQueue (Cola FIFO)**:

- Operación: `enqueue(vote)` - O(1)
- Propósito: Procesamiento ordenado de votos
- Implementación: Cola con nodos enlazados

**VoteRecordList (Lista Enlazada)**:

- Operación: `addFirst(voteRecord)` - O(1)
- Propósito: Registro cronológico inverso
- Implementación: Lista doblemente enlazada

### Reglas de Negocio Aplicadas

- RN-11: Un usuario solo puede votar una vez por elección
- RN-12: El voto es anónimo
- RN-13: El hash permite verificación sin revelar identidad
- RN-14: Procesamiento FIFO mediante VoteQueue
- RN-15: Todos los votos son auditados

### Consideraciones de Seguridad

- Token JWT validado en cada petición
- Hash SHA-256 con salt para unicidad
- Voto anónimo (no se almacena relación directa)
- Auditoría completa de eventos
- Cache en Redis para prevenir votos duplicados
