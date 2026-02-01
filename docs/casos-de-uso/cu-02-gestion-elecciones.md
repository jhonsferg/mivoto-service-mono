# CU-02: Gestión de Elecciones

## Diagrama de Caso de Uso

```plantuml
@startuml
left to right direction
skinparam packageStyle rectangle

actor "Administrador" as admin
actor "Votante" as voter
actor "Sistema" as system

rectangle "Sistema de Votación MiVoto" {
  usecase "Crear Elección" as UC1
  usecase "Listar Elecciones" as UC2
  usecase "Obtener Elección" as UC3
  usecase "Actualizar Elección" as UC4
  usecase "Iniciar Elección" as UC5
  usecase "Cerrar Elección" as UC6
  usecase "Cancelar Elección" as UC7
  usecase "Listar Activas" as UC8
  usecase "Filtrar por Estado" as UC9
  usecase "Validar Fechas" as UC10
  usecase "Registrar en Grafo" as UC11
  usecase "Registrar Auditoría" as UC12
}

admin --> UC1
admin --> UC4
admin --> UC5
admin --> UC6
admin --> UC7

voter --> UC2
voter --> UC3
voter --> UC8
voter --> UC9

UC1 ..> UC10 : <<include>>
UC1 ..> UC11 : <<include>>
UC1 ..> UC12 : <<include>>
UC4 ..> UC10 : <<include>>
UC4 ..> UC12 : <<include>>
UC5 ..> UC12 : <<include>>
UC6 ..> UC12 : <<include>>
UC7 ..> UC12 : <<include>>

system --> UC11
system --> UC12

note right of UC1
  Endpoint: POST /api/elections
  Solo ADMIN
end note

note right of UC11
  Estructura: ElectionGraph
  Jerarquías de elecciones
end note

@enduml
```

## Especificación Detallada

### CU-02.1: Crear Elección

**ID**: CU-02.1
**Nombre**: Crear Elección
**Actor Principal**: Administrador
**Precondiciones**:

- El usuario debe estar autenticado con rol ADMIN
- Las fechas deben ser válidas y coherentes

**Flujo Principal**:

1. El administrador accede al endpoint `/api/elections`
2. El administrador proporciona los datos de la elección
3. El sistema valida las fechas (inicio < fin)
4. El sistema valida que no exista una elección con el mismo nombre y fechas
5. El sistema crea la elección con estado SCHEDULED
6. El sistema registra la elección en ElectionGraph
7. El sistema registra el evento en auditoría
8. El sistema retorna la elección creada

**Datos de Entrada**:

```json
{
  "name": "string",
  "description": "string",
  "type": "PRESIDENTIAL|CONGRESSIONAL|REGIONAL|LOCAL",
  "startDate": "datetime",
  "endDate": "datetime",
  "districtId": "long (opcional)",
  "parentElectionId": "long (opcional)"
}
```

**Datos de Salida**:

```json
{
  "success": true,
  "message": "Elección creada exitosamente",
  "data": {
    "id": "long",
    "name": "string",
    "description": "string",
    "type": "string",
    "status": "SCHEDULED",
    "startDate": "datetime",
    "endDate": "datetime",
    "totalVotes": 0,
    "createdAt": "datetime"
  },
  "timestamp": "datetime"
}
```

**Reglas de Negocio**:

- RN-24: La fecha de inicio debe ser posterior a la fecha actual
- RN-25: La fecha de fin debe ser posterior a la fecha de inicio
- RN-26: El nombre de la elección debe ser único para el mismo período
- RN-27: Las elecciones se organizan en jerarquías mediante ElectionGraph

---

### CU-02.2: Iniciar Elección

**ID**: CU-02.2
**Nombre**: Iniciar Elección
**Actor Principal**: Administrador
**Precondiciones**:

- El usuario debe estar autenticado con rol ADMIN
- La elección debe estar en estado SCHEDULED
- Debe tener al menos 2 candidatos registrados

**Flujo Principal**:

1. El administrador accede al endpoint `/api/elections/{id}/start`
2. El sistema valida que la elección esté en estado SCHEDULED
3. El sistema verifica que tenga candidatos suficientes
4. El sistema cambia el estado a ACTIVE
5. El sistema registra el evento en auditoría
6. El sistema retorna confirmación

**Reglas de Negocio**:

- RN-28: Una elección debe tener mínimo 2 candidatos para iniciarse
- RN-29: Solo se puede iniciar elecciones en estado SCHEDULED

---

### CU-02.3: Cerrar Elección

**ID**: CU-02.3
**Nombre**: Cerrar Elección
**Actor Principal**: Administrador
**Precondiciones**:

- El usuario debe estar autenticado con rol ADMIN
- La elección debe estar en estado ACTIVE

**Flujo Principal**:

1. El administrador accede al endpoint `/api/elections/{id}/close`
2. El sistema valida que la elección esté en estado ACTIVE
3. El sistema cambia el estado a CLOSED
4. El sistema procesa los votos pendientes en VoteQueue
5. El sistema calcula los resultados finales
6. El sistema registra el evento en auditoría
7. El sistema retorna confirmación

**Reglas de Negocio**:

- RN-30: Solo se pueden cerrar elecciones en estado ACTIVE
- RN-31: Al cerrar, se procesan todos los votos pendientes
- RN-32: Los resultados quedan disponibles para consulta
