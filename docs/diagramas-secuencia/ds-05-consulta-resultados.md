# DS-05: Consulta de Resultados

## Diagrama de Secuencia

```plantuml
@startuml
autonumber
actor "Administrador" as admin
participant "Controller\nElectionController" as controller
participant "Service\nElectionService" as service
participant "Repository\nElectionRepository" as electionRepo
participant "Repository\nCandidateRepository" as candidateRepo
participant "DataStructure\nCandidateSearchTree" as bst
participant "Service\nResultCalculator" as calculator
participant "Audit\nAuditService" as audit
participant "Cache\nRedis" as redis
database "PostgreSQL" as db

== Consulta de Resultados ==
admin -> controller: GET /api/elections/{id}/results
activate controller

controller -> controller: validateJwtToken()
controller -> controller: checkPermissions()
note right: Requiere rol\nADMIN o SUPERVISOR

alt Sin permisos
    controller --> admin: 403 Forbidden
else Con permisos
    controller -> service: getElectionResults(electionId)
    activate service

    == Verificar Cache ==
    service -> redis: get("results:" + electionId)
    activate redis

    alt Resultados en cache
        redis --> service: cachedResults
        deactivate redis
        service --> controller: ElectionResultsResponse
        controller --> admin: 200 OK (from cache)
    else No hay cache
        redis --> service: null
        deactivate redis

        == Obtener Elección ==
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
            == Obtener Candidatos ==
            service -> bst: findByElection(electionId)
            activate bst
            bst -> bst: inOrderTraversal()
            note right: Recorrido in-order\ndel BST O(n)
            bst --> service: List<Candidate>
            deactivate bst

            == Calcular Resultados ==
            service -> calculator: calculateResults(election, candidates)
            activate calculator

            loop Para cada candidato
                calculator -> candidateRepo: getVoteCount(candidateId)
                activate candidateRepo
                candidateRepo -> db: SELECT vote_count FROM candidates\nWHERE id = ?
                db --> candidateRepo: voteCount
                candidateRepo --> calculator: long
                deactivate candidateRepo

                calculator -> calculator: calculatePercentage(voteCount, totalVotes)
            end

            calculator -> calculator: sortByVoteCount(results)
            calculator -> calculator: determineWinner()
            calculator -> calculator: calculateParticipation()

            calculator --> service: ElectionResults
            deactivate calculator

            == Guardar en Cache ==
            service -> redis: set("results:" + electionId, results, TTL=5min)
            activate redis
            note right: Cache con TTL\npara elecciones activas
            redis --> service: OK
            deactivate redis

            == Auditoría ==
            service -> audit: logResultsQuery(electionId, userId)
            activate audit
            audit -> db: INSERT INTO audit_logs\n(action='RESULTS_QUERY')
            db --> audit: OK
            audit --> service: void
            deactivate audit

            service --> controller: ElectionResultsResponse
        end
    end
end

controller --> admin: 200 OK\n{results, winner, statistics}
deactivate service
deactivate controller

@enduml
```

## Descripción del Flujo

### Actores y Componentes

- **Administrador**: Usuario con rol ADMIN o SUPERVISOR
- **ElectionController**: Controlador REST para elecciones
- **ElectionService**: Servicio de gestión de elecciones
- **ElectionRepository**: Repositorio de elecciones
- **CandidateRepository**: Repositorio de candidatos
- **CandidateSearchTree**: BST para búsqueda de candidatos
- **ResultCalculator**: Calculador de resultados
- **AuditService**: Servicio de auditoría
- **Redis**: Cache para optimización
- **PostgreSQL**: Base de datos principal

### Pasos del Flujo

1. **Autenticación y Autorización**: Valida JWT y permisos
2. **Verificación de Cache**: Consulta Redis
3. **Obtención de Elección**: Busca en PostgreSQL
4. **Búsqueda de Candidatos**: Recorre BST in-order
5. **Cálculo de Resultados**: Procesa votos y porcentajes
6. **Ordenamiento**: Ordena por cantidad de votos
7. **Determinación de Ganador**: Identifica al candidato con más votos
8. **Almacenamiento en Cache**: Guarda en Redis con TTL
9. **Auditoría**: Registra la consulta
10. **Respuesta**: Retorna resultados completos

### Estructura de Datos Utilizada

**CandidateSearchTree (BST)**:

```
           [Candidato C]
          /             \
    [Candidato A]    [Candidato E]
         \               /
      [Candidato B] [Candidato D]
```

- **Operación**: `inOrderTraversal()` - O(n)
- **Propósito**: Obtener candidatos ordenados
- **Resultado**: Lista ordenada por criterio (ej: ID, nombre)

### Cálculo de Resultados

**Métricas Calculadas**:

```java
percentage = (voteCount / totalVotes) * 100
participationRate = (totalVotes / eligibleVoters) * 100
winner = candidate with max(voteCount)
```

**Ordenamiento**:

- Por cantidad de votos (descendente)
- Empates se resuelven por timestamp de último voto

### Optimización con Cache

**Estrategia de Cache**:

- **Key**: `results:{electionId}`
- **TTL**:
  - Elecciones activas: 5 minutos
  - Elecciones cerradas: 24 horas
- **Invalidación**: Al registrar nuevo voto

### Reglas de Negocio Aplicadas

- RN-39: Solo ADMIN y SUPERVISOR pueden consultar resultados
- RN-40: Resultados ordenados por votos (descendente)
- RN-41: Ganador es el candidato con más votos
- RN-42: Todas las consultas son auditadas

### Respuesta de Resultados

```json
{
  "electionId": 1,
  "electionName": "Elecciones Presidenciales 2025",
  "status": "CLOSED",
  "totalVotes": 15000,
  "totalEligibleVoters": 20000,
  "participationRate": 75.0,
  "results": [
    {
      "candidateId": 5,
      "candidateName": "Juan Pérez",
      "politicalParty": "Partido A",
      "voteCount": 8000,
      "percentage": 53.33,
      "position": 1
    },
    {
      "candidateId": 3,
      "candidateName": "María García",
      "politicalParty": "Partido B",
      "voteCount": 7000,
      "percentage": 46.67,
      "position": 2
    }
  ],
  "winner": {
    "candidateId": 5,
    "candidateName": "Juan Pérez",
    "voteCount": 8000,
    "percentage": 53.33
  }
}
```
