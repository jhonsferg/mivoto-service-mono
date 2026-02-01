# 📚 Documentación UML - Sistema de Votación Electrónica

Esta documentación contiene los diagramas UML completos del sistema de votación electrónica MiVoto, desarrollados con PlantUML.

## 📋 Contenido

### 1. [Casos de Uso](./casos-de-uso/)

Especificaciones detalladas de los casos de uso del sistema:

- [CU-01: Autenticación de Usuario](./casos-de-uso/cu-01-autenticacion.md)
- [CU-02: Gestión de Elecciones](./casos-de-uso/cu-02-gestion-elecciones.md)
- [CU-03: Gestión de Candidatos](./casos-de-uso/cu-03-gestion-candidatos.md)
- [CU-04: Emisión de Voto](./casos-de-uso/cu-04-emision-voto.md)
- [CU-05: Verificación de Voto](./casos-de-uso/cu-05-verificacion-voto.md)
- [CU-06: Consulta de Resultados](./casos-de-uso/cu-06-consulta-resultados.md)

### 2. [Diagramas de Secuencia](./diagramas-secuencia/)

Flujos de interacción entre componentes del sistema:

- [DS-01: Autenticación JWT](./diagramas-secuencia/ds-01-autenticacion-jwt.md)
- [DS-02: Emisión de Voto](./diagramas-secuencia/ds-02-emision-voto.md)
- [DS-03: Verificación de Voto](./diagramas-secuencia/ds-03-verificacion-voto.md)
- [DS-04: Creación de Elección](./diagramas-secuencia/ds-04-creacion-eleccion.md)
- [DS-05: Consulta de Resultados](./diagramas-secuencia/ds-05-consulta-resultados.md)
- [DS-06: Registro de Candidato](./diagramas-secuencia/ds-06-registro-candidato.md)

### 3. [Diagramas de Clases](./diagramas-clases/)

Estructura de clases del sistema:

- [DC-01: Modelo de Dominio](./diagramas-clases/dc-01-modelo-dominio.md)
- [DC-02: Arquitectura Hexagonal](./diagramas-clases/dc-02-arquitectura-hexagonal.md)
- [DC-03: Estructuras de Datos](./diagramas-clases/dc-03-estructuras-datos.md)
- [DC-04: Seguridad y Autenticación](./diagramas-clases/dc-04-seguridad-autenticacion.md)

## 🔧 Herramientas

### PlantUML

Todos los diagramas están escritos en PlantUML. Para visualizarlos:

1. **Online**: [PlantUML Web Server](http://www.plantuml.com/plantuml/uml/)
2. **VS Code**: Instalar extensión "PlantUML"
3. **IntelliJ IDEA**: Plugin "PlantUML Integration"

### Generar Imágenes

```bash
# Instalar PlantUML
brew install plantuml

# Generar PNG desde archivo .puml
plantuml diagrama.puml

# Generar SVG
plantuml -tsvg diagrama.puml
```

## 📐 Convenciones

### Actores

- **Votante**: Usuario con rol VOTER
- **Administrador**: Usuario con rol ADMIN
- **Supervisor**: Usuario con rol SUPERVISOR
- **Sistema**: Componentes automatizados

### Estereotipos

- `<<entity>>`: Entidades de dominio
- `<<value object>>`: Objetos de valor
- `<<service>>`: Servicios de aplicación
- `<<repository>>`: Repositorios de persistencia
- `<<controller>>`: Controladores REST
- `<<port>>`: Interfaces de puertos (Hexagonal)
- `<<adapter>>`: Adaptadores de infraestructura

## 🏗️ Arquitectura del Sistema

El sistema implementa **Arquitectura Hexagonal** con las siguientes capas:

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│    (REST Controllers, DTOs)             │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│        Application Layer                │
│    (Use Cases, Services)                │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│          Domain Layer                   │
│  (Entities, Ports, Business Logic)      │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│      Infrastructure Layer               │
│  (JPA, Security, Config, Mappers)       │
└─────────────────────────────────────────┘
```

## 🔐 Seguridad

El sistema implementa:

- **JWT (JSON Web Tokens)** para autenticación
- **Spring Security** para autorización basada en roles
- **Hashing SHA-256** para verificación de votos
- **Auditoría completa** de todas las operaciones

## 📊 Estructuras de Datos Personalizadas

El sistema implementa estructuras de datos desde cero:

1. **Lista Enlazada** (`VoteRecordList`): Registro cronológico de votos
2. **Cola FIFO** (`VoteQueue`): Procesamiento de votos
3. **Árbol BST** (`CandidateSearchTree`): Búsqueda de candidatos
4. **Grafo** (`ElectionGraph`): Jerarquías de elecciones

## 📝 Notas

- Todos los diagramas están en **español**
- Los diagramas siguen el estándar **UML 2.5**
- La documentación se actualiza con cada cambio significativo en el sistema
