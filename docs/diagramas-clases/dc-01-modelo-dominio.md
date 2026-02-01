# DC-01: Modelo de Dominio

## Diagrama de Clases

```plantuml
@startuml
skinparam classAttributeIconSize 0
skinparam linetype ortho

' Entidades Principales
class User <<entity>> {
  - id: Long
  - documentNumber: String
  - firstName: String
  - lastName: String
  - email: String
  - password: String
  - role: UserRole
  - active: Boolean
  - createdAt: LocalDateTime
  - lastLogin: LocalDateTime
  --
  + getFullName(): String
  + isAdmin(): Boolean
  + isVoter(): Boolean
}

class Election <<entity>> {
  - id: Long
  - name: String
  - description: String
  - type: ElectionType
  - status: ElectionStatus
  - startDate: LocalDateTime
  - endDate: LocalDateTime
  - totalVotes: Long
  - districtId: Long
  - createdAt: LocalDateTime
  --
  + isActive(): Boolean
  + canAcceptVotes(): Boolean
  + incrementVoteCount(): void
}

class Candidate <<entity>> {
  - id: Long
  - documentNumber: String
  - firstName: String
  - lastName: String
  - politicalParty: String
  - photoUrl: String
  - biography: String
  - proposals: String
  - voteCount: Long
  - active: Boolean
  - electionId: Long
  --
  + getFullName(): String
  + incrementVoteCount(): void
}

class Vote <<entity>> {
  - id: Long
  - userId: Long
  - electionId: Long
  - candidateId: Long
  - voteHash: String
  - timestamp: LocalDateTime
  - ipAddress: String
  --
  + generateHash(): String
  + verify(): Boolean
}

class VoteRecord <<value object>> {
  - voteId: Long
  - voteHash: String
  - electionId: Long
  - timestamp: LocalDateTime
  - verified: Boolean
  --
  + toDto(): VoteRecordDto
}

class VotingSession <<entity>> {
  - id: Long
  - userId: Long
  - electionId: Long
  - startTime: LocalDateTime
  - endTime: LocalDateTime
  - ipAddress: String
  - userAgent: String
  --
  + isActive(): Boolean
  + getDuration(): Duration
}

class District <<entity>> {
  - id: Long
  - name: String
  - code: String
  - level: DistrictLevel
  - parentDistrictId: Long
  - population: Long
  --
  + getEligibleVoters(): Long
}

class AuditLog <<entity>> {
  - id: Long
  - userId: Long
  - action: AuditAction
  - entityType: String
  - entityId: Long
  - details: String
  - ipAddress: String
  - timestamp: LocalDateTime
  --
  + toJson(): String
}

' Enumeraciones
enum UserRole {
  VOTER
  ADMIN
  SUPERVISOR
}

enum ElectionType {
  PRESIDENTIAL
  CONGRESSIONAL
  REGIONAL
  LOCAL
}

enum ElectionStatus {
  SCHEDULED
  ACTIVE
  CLOSED
  CANCELLED
}

enum DistrictLevel {
  NATIONAL
  REGIONAL
  PROVINCIAL
  DISTRICT
}

enum AuditAction {
  LOGIN
  LOGOUT
  VOTE_CAST
  VOTE_VERIFIED
  ELECTION_CREATED
  CANDIDATE_REGISTERED
}

' Relaciones
User "1" -- "0..*" Vote : emite >
User "1" -- "0..*" VotingSession : tiene >
User "1" -- "0..*" AuditLog : genera >

Election "1" -- "0..*" Candidate : contiene >
Election "1" -- "0..*" Vote : recibe >
Election "1" -- "0..*" VotingSession : permite >
Election "*" -- "0..1" District : se realiza en >

Candidate "1" -- "0..*" Vote : recibe >

Vote "1" -- "1" VoteRecord : genera >

District "1" -- "0..*" District : contiene >

' Composiciones
User *-- UserRole
Election *-- ElectionType
Election *-- ElectionStatus
District *-- DistrictLevel
AuditLog *-- AuditAction

note right of Vote
  El voto es anónimo:
  No se almacena relación
  directa user-candidate
  Solo se usa para validación
end note

note right of VoteRecord
  Value Object inmutable
  para verificación pública
  sin revelar identidad
end note

@enduml
```

## Descripción del Modelo

### Entidades Principales

#### User (Usuario)

- **Propósito**: Representa a los usuarios del sistema
- **Roles**: VOTER (votante), ADMIN (administrador), SUPERVISOR
- **Características**:
  - Autenticación con email/documento y contraseña
  - Control de estado activo/inactivo
  - Registro de último acceso

#### Election (Elección)

- **Propósito**: Representa un proceso electoral
- **Estados**: SCHEDULED, ACTIVE, CLOSED, CANCELLED
- **Tipos**: PRESIDENTIAL, CONGRESSIONAL, REGIONAL, LOCAL
- **Características**:
  - Control de fechas de inicio y fin
  - Contador de votos totales
  - Asociación con distrito

#### Candidate (Candidato)

- **Propósito**: Representa a un candidato en una elección
- **Características**:
  - Información personal y política
  - Contador de votos recibidos
  - Estado activo/inactivo
  - Asociación con elección

#### Vote (Voto)

- **Propósito**: Registro de un voto emitido
- **Características**:
  - Hash SHA-256 para verificación
  - Timestamp de emisión
  - Anonimato garantizado
  - Trazabilidad de IP

#### VoteRecord (Registro de Voto)

- **Propósito**: Value Object para verificación pública
- **Características**:
  - Inmutable
  - No revela identidad del votante
  - Permite verificación de integridad

#### VotingSession (Sesión de Votación)

- **Propósito**: Rastrea sesiones de votación
- **Características**:
  - Control de tiempo de sesión
  - Información de navegador y IP
  - Detección de anomalías

#### District (Distrito)

- **Propósito**: Organización territorial
- **Niveles**: NATIONAL, REGIONAL, PROVINCIAL, DISTRICT
- **Características**:
  - Jerarquía de distritos
  - Población y votantes elegibles

#### AuditLog (Registro de Auditoría)

- **Propósito**: Trazabilidad completa del sistema
- **Acciones**: LOGIN, LOGOUT, VOTE_CAST, VOTE_VERIFIED, etc.
- **Características**:
  - Registro inmutable
  - Información completa de contexto

### Relaciones

**User - Vote**: Un usuario puede emitir múltiples votos (en diferentes elecciones)

**Election - Candidate**: Una elección contiene múltiples candidatos

**Election - Vote**: Una elección recibe múltiples votos

**Candidate - Vote**: Un candidato recibe múltiples votos

**Vote - VoteRecord**: Cada voto genera un registro de verificación

**District - District**: Jerarquía de distritos (auto-referencia)

### Reglas de Integridad

1. **Unicidad de Voto**: Un usuario solo puede votar una vez por elección
2. **Anonimato**: No se almacena relación directa user-candidate
3. **Inmutabilidad**: Los votos no pueden ser modificados una vez emitidos
4. **Verificabilidad**: Todo voto genera un hash único para verificación
5. **Auditoría**: Todas las acciones críticas se registran en AuditLog

### Consideraciones de Diseño

- **Separación de Concerns**: Entidades de dominio puras sin dependencias de infraestructura
- **Value Objects**: VoteRecord es inmutable y sin identidad propia
- **Enumeraciones**: Tipos y estados bien definidos
- **Auditoría**: Sistema completo de trazabilidad
- **Privacidad**: Diseño que garantiza anonimato del voto
