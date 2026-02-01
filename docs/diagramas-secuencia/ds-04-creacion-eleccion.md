# DS-04: Creación de Elección

## Diagrama de Secuencia

```plantuml
@startuml
autonumber
actor "Administrador" as admin
participant "Controller\nElectionController" as controller
participant "Service\nElectionService" as service
participant "Repository\nElectionRepository" as repo
participant "DataStructure\nElectionGraph" as graph
participant "Validator\nElectionValidator" as validator
participant "Audit\nAuditService" as audit
database "PostgreSQL" as db

== Creación de Elección ==
admin -> controller: POST /api/elections\n{name, type, dates, ...}
activate controller

controller -> controller: validateJwtToken()
controller -> controller: checkAdminRole()

alt No es ADMIN
    controller --> admin: 403 Forbidden
else Es ADMIN
    controller -> service: createElection(CreateElectionRequest)
    activate service

    == Validaciones ==
    service -> validator: validateDates(startDate, endDate)
    activate validator
    validator -> validator: checkStartDateFuture()
    validator -> validator: checkEndDateAfterStart()

    alt Fechas inválidas
        validator --> service: throw InvalidDateException
        service --> controller: ValidationException
        controller --> admin: 400 Bad Request
    else Fechas válidas
        validator --> service: void
        deactivate validator

        service -> repo: existsByNameAndDates(name, dates)
        activate repo
        repo -> db: SELECT COUNT(*) FROM elections\nWHERE name = ? AND ...
        db --> repo: count
        repo --> service: boolean
        deactivate repo

        alt Elección duplicada
            service --> controller: throw DuplicateElectionException
            controller --> admin: 409 Conflict
        else No existe duplicado
            == Creación de Entidad ==
            service -> service: buildElection(request)
            note right: Status = SCHEDULED\nTotalVotes = 0

            service -> repo: save(election)
            activate repo
            repo -> db: INSERT INTO elections VALUES (...)
            db --> repo: Election entity
            repo --> service: Election
            deactivate repo

            == Registro en Grafo ==
            service -> graph: addVertex(election)
            activate graph
            graph -> graph: createNode(electionId)

            alt Tiene elección padre
                service -> graph: addEdge(parentId, electionId)
                graph -> graph: createEdge(parent, child)
                note right: Jerarquía de elecciones\nEj: Nacional -> Regional
            end

            graph --> service: void
            deactivate graph

            == Auditoría ==
            service -> audit: logElectionCreated(election)
            activate audit
            audit -> db: INSERT INTO audit_logs\n(action='ELECTION_CREATED')
            db --> audit: OK
            audit --> service: void
            deactivate audit

            service --> controller: ElectionResponse
        end
    end
end

controller --> admin: 201 Created\n{id, name, status, dates}
deactivate service
deactivate controller

@enduml
```

## Descripción del Flujo

### Actores y Componentes

- **Administrador**: Usuario con rol ADMIN
- **ElectionController**: Controlador REST para elecciones
- **ElectionService**: Servicio de gestión de elecciones
- **ElectionRepository**: Repositorio de elecciones
- **ElectionGraph**: Grafo para jerarquías de elecciones
- **ElectionValidator**: Validador de reglas de negocio
- **AuditService**: Servicio de auditoría
- **PostgreSQL**: Base de datos principal

### Pasos del Flujo

1. **Autenticación y Autorización**: Valida JWT y rol ADMIN
2. **Validación de Fechas**: Verifica coherencia temporal
3. **Verificación de Duplicados**: Consulta elecciones existentes
4. **Creación de Entidad**: Construye objeto Election
5. **Persistencia**: Guarda en PostgreSQL
6. **Registro en Grafo**: Agrega vértice y aristas
7. **Auditoría**: Registra el evento
8. **Respuesta**: Retorna elección creada

### Estructura de Datos Utilizada

**ElectionGraph (Grafo Dirigido)**:

```
         [Elección Nacional]
                |
        +-------+-------+
        |               |
   [Regional A]    [Regional B]
        |               |
    +---+---+       +---+---+
    |       |       |       |
 [Local1][Local2][Local3][Local4]
```

- **Vértices**: Elecciones
- **Aristas**: Relaciones jerárquicas (padre -> hijo)
- **Operaciones**:
  - `addVertex(election)` - O(1)
  - `addEdge(parent, child)` - O(1)
  - `getChildren(electionId)` - O(V+E)

### Validaciones Aplicadas

**Fechas**:

- startDate > NOW()
- endDate > startDate
- Duración mínima: 1 hora
- Duración máxima: 30 días

**Duplicados**:

- Nombre único en el mismo período
- No solapamiento de fechas para mismo tipo y distrito

### Reglas de Negocio Aplicadas

- RN-24: Fecha de inicio posterior a fecha actual
- RN-25: Fecha de fin posterior a fecha de inicio
- RN-26: Nombre único para el mismo período
- RN-27: Jerarquías mediante ElectionGraph

### Jerarquías de Elecciones

El grafo permite modelar:

- **Elecciones Nacionales** → **Regionales** → **Locales**
- **Elecciones Generales** → **Elecciones Específicas**
- Consultas de elecciones relacionadas
- Validación de dependencias
