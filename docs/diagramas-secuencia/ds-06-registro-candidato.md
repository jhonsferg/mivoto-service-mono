# DS-06: Registro de Candidato

## Diagrama de Secuencia

```plantuml
@startuml
autonumber
actor "Administrador" as admin
participant "Controller\nCandidateController" as controller
participant "Service\nCandidateService" as service
participant "Repository\nCandidateRepository" as repo
participant "Repository\nElectionRepository" as electionRepo
participant "DataStructure\nCandidateSearchTree" as bst
participant "Validator\nCandidateValidator" as validator
participant "Audit\nAuditService" as audit
database "PostgreSQL" as db

== Registro de Candidato ==
admin -> controller: POST /api/candidates\n{electionId, documentNumber, ...}
activate controller

controller -> controller: validateJwtToken()
controller -> controller: checkAdminRole()

alt No es ADMIN
    controller --> admin: 403 Forbidden
else Es ADMIN
    controller -> service: registerCandidate(CreateCandidateRequest)
    activate service

    == Validar Elección ==
    service -> electionRepo: findById(electionId)
    activate electionRepo
    electionRepo -> db: SELECT * FROM elections WHERE id = ?
    db --> electionRepo: Election entity
    electionRepo --> service: Optional<Election>
    deactivate electionRepo

    alt Elección no encontrada
        service --> controller: throw ElectionNotFoundException
        controller --> admin: 404 Not Found
    else Elección encontrada
        service -> service: checkElectionStatus()

        alt Elección cerrada o cancelada
            service --> controller: throw InvalidElectionStateException
            controller --> admin: 400 Bad Request
        else Estado válido
            == Validar Candidato ==
            service -> validator: validateCandidateData(request)
            activate validator
            validator -> validator: validateDocumentNumber()
            validator -> validator: validateNames()
            validator --> service: void
            deactivate validator

            service -> repo: existsByDocumentAndElection(doc, electionId)
            activate repo
            repo -> db: SELECT COUNT(*) FROM candidates\nWHERE document = ? AND election_id = ?
            db --> repo: count
            repo --> service: boolean
            deactivate repo

            alt Candidato duplicado
                service --> controller: throw DuplicateCandidateException
                controller --> admin: 409 Conflict
            else No existe duplicado
                == Crear Candidato ==
                service -> service: buildCandidate(request)
                note right: active = true\nvoteCount = 0

                service -> repo: save(candidate)
                activate repo
                repo -> db: INSERT INTO candidates VALUES (...)
                db --> repo: Candidate entity
                repo --> service: Candidate
                deactivate repo

                == Insertar en BST ==
                service -> bst: insert(candidate)
                activate bst
                bst -> bst: findInsertPosition(candidateId)
                note right: Búsqueda O(log n)\nen árbol balanceado
                bst -> bst: createNode(candidate)
                bst -> bst: insertNode(node)
                bst --> service: void
                deactivate bst

                == Auditoría ==
                service -> audit: logCandidateRegistered(candidate)
                activate audit
                audit -> db: INSERT INTO audit_logs\n(action='CANDIDATE_REGISTERED')
                db --> audit: OK
                audit --> service: void
                deactivate audit

                service --> controller: CandidateDto
            end
        end
    end
end

controller --> admin: 201 Created\n{id, fullName, electionName, active}
deactivate service
deactivate controller

@enduml
```

## Descripción del Flujo

### Actores y Componentes

- **Administrador**: Usuario con rol ADMIN
- **CandidateController**: Controlador REST para candidatos
- **CandidateService**: Servicio de gestión de candidatos
- **CandidateRepository**: Repositorio de candidatos
- **ElectionRepository**: Repositorio de elecciones
- **CandidateSearchTree**: BST para organización de candidatos
- **CandidateValidator**: Validador de datos
- **AuditService**: Servicio de auditoría
- **PostgreSQL**: Base de datos principal

### Pasos del Flujo

1. **Autenticación y Autorización**: Valida JWT y rol ADMIN
2. **Validación de Elección**: Verifica existencia y estado
3. **Validación de Datos**: Verifica formato de documento y nombres
4. **Verificación de Duplicados**: Consulta candidatos existentes
5. **Creación de Entidad**: Construye objeto Candidate
6. **Persistencia**: Guarda en PostgreSQL
7. **Inserción en BST**: Agrega al árbol de búsqueda
8. **Auditoría**: Registra el evento
9. **Respuesta**: Retorna candidato creado

### Estructura de Datos Utilizada

**CandidateSearchTree (BST)**:

Inserción de candidatos ordenados por ID:

```
Antes:           Después de insertar candidato ID=4:
    [5]                    [5]
   /   \                  /   \
 [3]   [7]             [3]    [7]
                         \
                         [4]
```

- **Operación**: `insert(candidate)` - O(log n) promedio
- **Propósito**: Búsqueda eficiente de candidatos
- **Criterio de ordenamiento**: ID del candidato

### Validaciones Aplicadas

**Documento**:

- Formato válido según tipo (DNI, CE, etc.)
- Longitud correcta
- Solo dígitos

**Nombres**:

- No vacíos
- Longitud mínima: 2 caracteres
- Solo letras y espacios

**Partido Político**:

- Nombre válido
- Registrado en el sistema (opcional)

### Reglas de Negocio Aplicadas

- RN-33: No duplicados en la misma elección
- RN-34: Organización en BST para búsqueda eficiente
- RN-35: No agregar a elecciones cerradas/canceladas
- RN-36: Documento único por elección

### Operaciones del BST

**Inserción**:

```java
// Pseudocódigo
insert(candidate):
    if tree is empty:
        root = new Node(candidate)
    else:
        current = root
        while true:
            if candidate.id < current.id:
                if current.left is null:
                    current.left = new Node(candidate)
                    break
                current = current.left
            else:
                if current.right is null:
                    current.right = new Node(candidate)
                    break
                current = current.right
```

**Complejidad**:

- Mejor caso: O(log n) - árbol balanceado
- Peor caso: O(n) - árbol degenerado
- Promedio: O(log n)
