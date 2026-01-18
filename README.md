# 🗳️ Sistema de Votación Electrónica - Backend

API REST desarrollada con Spring Boot 3.x que implementa estructuras de datos personalizadas para un sistema de votación electrónica seguro y eficiente.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Arquitectura](#-arquitectura)
- [Estructuras de Datos](#-estructuras-de-datos)
- [Tecnologías](#-tecnologías)
- [Requisitos](#-requisitos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Ejecución](#-ejecución)
- [API Documentation](#-api-documentation)
- [Testing](#-testing)

## ✨ Características

- **Arquitectura Hexagonal**: Separación clara de responsabilidades
- **Estructuras de Datos Personalizadas**: Implementación desde cero
    - Lista Enlazada para registro de votos
    - Cola FIFO para procesamiento de votos
    - Árbol BST para búsqueda de candidatos
    - Grafo para jerarquías de elecciones
- **Seguridad JWT**: Autenticación y autorización con tokens
- **API REST**: Endpoints RESTful bien documentados
- **Auditoría**: Sistema completo de logs de auditoría
- **Caché**: Optimización con Caffeine
- **Documentación Swagger**: API interactiva

## 🏗️ Arquitectura
```
mivoto-service-mono/
├── domain/                 # Capa de dominio (entidades, puertos)
│   ├── model/             # Modelos de dominio
│   ├── enums/             # Enumeraciones
│   ├── exceptions/        # Excepciones de negocio
│   └── ports/             # Interfaces (in/out)
├── application/           # Capa de aplicación (casos de uso)
│   ├── services/          # Servicios de aplicación
│   └── usecases/          # Implementación de casos de uso
├── infrastructure/        # Capa de infraestructura
│   ├── persistence/       # Repositorios JPA
│   ├── security/          # Configuración de seguridad
│   ├── mappers/           # Mappers entidad-dominio
│   └── config/            # Configuraciones
├── datastructures/        # Estructuras de datos personalizadas
│   ├── interfaces/        # Interfaces
│   ├── linear/            # LinkedList, Queue
│   ├── nonlinear/         # BST, Graph
│   └── implementations/   # Uso en el sistema
└── presentation/          # Capa de presentación
    ├── controllers/       # Controladores REST
    ├── dto/               # DTOs request/response
    └── mappers/           # Mappers DTO-dominio
```

## 📊 Estructuras de Datos

### 1. Lista Enlazada (LinkedList)
- **Uso**: Registro cronológico de votos
- **Complejidad**: O(1) addFirst, O(n) búsqueda
- **Implementación**: `VoteRecordList`

### 2. Cola (Queue)
- **Uso**: Procesamiento FIFO de votos
- **Complejidad**: O(1) enqueue/dequeue
- **Implementación**: `VoteQueue`

### 3. Árbol Binario de Búsqueda (BST)
- **Uso**: Búsqueda eficiente de candidatos
- **Complejidad**: O(log n) búsqueda/inserción
- **Implementación**: `CandidateSearchTree`

### 4. Grafo (Graph)
- **Uso**: Jerarquías entre elecciones
- **Complejidad**: O(V+E) DFS/BFS
- **Implementación**: `ElectionGraph`

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 4.0.1**
- **Spring Security** con JWT
- **Spring Data JPA**
- **PostgreSQL** (producción)
- **H2** (desarrollo)
- **Flyway** (migraciones)
- **Caffeine** (caché)
- **Lombok**
- **SpringDoc OpenAPI** (Swagger)
- **JUnit 5** & **Mockito**

## 📦 Requisitos

- Java 17 o superior
- Maven 3.8+
- PostgreSQL 14+ (para producción)
- Docker (opcional)

## 🚀 Instalación
```bash
# Clonar repositorio
git clone https://github.com/jhonsferg/mivoto-service-mono.git
cd mivoto-service-mono

# Compilar proyecto
mvn clean install

# Saltar tests (opcional)
mvn clean install -DskipTests
```

## ⚙️ Configuración

### Variables de Entorno
```bash
# Base de datos
export DATABASE_URL=jdbc:postgresql://localhost:5432/voting_system
export DATABASE_USER=postgres
export DATABASE_PASSWORD=postgres

# JWT
export JWT_SECRET=your-secret-key-here

# Puerto
export PORT=8080
```

### application.yml

Configurar en `src/main/resources/application.yml`

## 🎯 Ejecución

### Desarrollo (H2)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Producción (PostgreSQL)
```bash
# Iniciar PostgreSQL
docker-compose up -d postgres

# Ejecutar aplicación
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Docker
```bash
# Construir imagen
docker build -t mivoto-service-mono .

# Ejecutar contenedor
docker run -p 8080:8080 mivoto-service-mono
```

## 📚 API Documentation

### Swagger UI
```
http://localhost:8080/swagger-ui.html
```

### OpenAPI Spec
```
http://localhost:8080/v3/api-docs
```

### Principales Endpoints

#### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/logout` - Cerrar sesión
- `POST /api/auth/refresh` - Refrescar token

#### Votación
- `POST /api/votes` - Emitir voto
- `GET /api/votes/verify/{hash}` - Verificar voto
- `GET /api/votes/history` - Historial de votos

#### Elecciones
- `GET /api/elections` - Listar elecciones
- `GET /api/elections/active` - Elecciones activas
- `POST /api/elections` - Crear elección (ADMIN)
- `GET /api/elections/{id}/results` - Resultados

#### Candidatos
- `GET /api/candidates/election/{id}` - Candidatos de elección
- `POST /api/candidates` - Registrar candidato (ADMIN)

## 🧪 Testing
```bash
# Ejecutar todos los tests
mvn test

# Tests de integración
mvn verify

# Reporte de cobertura
mvn jacoco:report
```

## 👥 Usuarios por Defecto

### Administrador
- **Email**: admin@mivoto.com
- **Contraseña**: admin123

### Votante
- **Email**: juan.perez@email.com
- **Contraseña**: voter123
