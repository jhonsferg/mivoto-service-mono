# 🗳️ Sistema de Votación Electrónica - Backend

API REST desarrollada con Spring Boot 3.x que implementa estructuras de datos personalizadas para un sistema de votación electrónica seguro y eficiente.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=black)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

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
- **Caché**: Distribuida con Redis (Dev, QA, Prod, Local)
- **Documentación Swagger**: API interactiva

## 🏗️ Arquitectura

```txt
mivoto-service-mono/
├── docker/                    # Configuraciones de entorno Docker
│   ├── local/                 # Entorno Local (Solo dependencias)
│   ├── dev/                   # Entorno de Desarrollo
│   ├── qa/                    # Entorno de QA
│   └── prod/                  # Entorno de Producción
├── src/main/java/pe/com/mivoto/service/
│   ├── domain/                # Capa de Dominio (Lógica de Negocio Pura)
│   │   ├── model/             # Entidades del dominio
│   │   ├── ports/             # Interfaces (In/Out) para desacoplamiento
│   │   ├── enums/             # Enumeraciones del dominio
│   │   └── exceptions/        # Excepciones de negocio
│   ├── application/           # Capa de Aplicación (Orquestación)
│   │   ├── usecases/          # Implementación de Casos de Uso
│   │   └── services/          # Servicios de aplicación
│   ├── infrastructure/        # Capa de Infraestructura (Adaptadores)
│   │   ├── persistence/       # Adaptadores de persistencia (JPA)
│   │   ├── security/          # Configuración de Spring Security y JWT
│   │   ├── config/            # Configuraciones de Spring (OpenAPI, Beans)
│   │   └── mappers/           # Conversión Entidad <-> Dominio
│   ├── datastructures/        # Estructuras de Datos Personalizadas
│   │   ├── linear/            # Estructuras lineales (LinkedList, Queue)
│   │   ├── nonlinear/         # Estructuras no lineales (BST, Graph)
│   │   ├── interfaces/        # Contratos de estructuras de datos
│   │   └── implementations/   # Implementaciones concretas
│   └── presentation/          # Capa de Presentación (REST)
│       ├── controllers/       # Controladores API REST
│       ├── dto/               # Objetos de Transferencia de Datos (Request/Response)
│       └── mappers/           # Conversión DTO <-> Dominio
└── pom.xml                    # Gestor de dependencias Maven
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
- **Spring Boot 3.2.1**
- **Spring Security** con JWT
- **Spring Data JPA**
- **PostgreSQL** (producción)
- **Redis** (caché distribuida)
- **Flyway** (migraciones)
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

El sistema utiliza variables de entorno estandarizadas para todos los entornos (Local, Dev, QA, Prod).

| Variable | Descripción | Valor por Defecto (Local/Dev) |
|----------|-------------|-------------------------------|
| `DATABASE_URL` | URL de conexión JDBC | `jdbc:postgresql://localhost:5432/mivoto` |
| `DATABASE_USER` | Usuario de BD | `postgres` |
| `DATABASE_PASSWORD` | Contraseña de BD | `postgres` |
| `REDIS_HOST` | Host de Redis | `localhost` |
| `REDIS_PORT` | Puerto de Redis | `6379` |
| `REDIS_PASSWORD` | Contraseña de Redis | (vacío) |
| `JWT_SECRET` | Clave secreta para firma | (secreto por defecto) |
| `PORT` | Puerto del servidor | `8080` |

### Entornos Docker
El proyecto cuenta con configuraciones Docker optimizadas por entorno en `docker/`:

- **Local** (`docker/local`): Solo dependencias (DB, Redis, Tools). La app corre en IDE.
- **Dev** (`docker/dev`): Entorno completo de desarrollo.
- **QA** (`docker/qa`): Entorno de pruebas con credenciales dedicadas.
- **Prod** (`docker/prod`): Entorno de producción endurecido.

### application.yml

Configurar en `src/main/resources/application.yml`

## 🎯 Ejecución

### 1. Desarrollo Local (IDE + Docker Dependencies)
Esta es la forma recomendada para desarrollo diario.

1. **Iniciar dependencias (DB, Redis, Tools):**
   ```bash
   docker-compose -f docker/local/docker-compose.yml up -d
   ```
2. **Ejecutar la aplicación (Desde el IDE):**
   - El perfil `local` está activo por defecto en `pom.xml`.
   - La aplicación conectará automáticamente a los servicios en `localhost`.

### 2. Entornos Completos (Docker Compose)
Para levantar el entorno completo (incluida la app containerizada):

**Dev:**
```bash
docker-compose -f docker/dev/docker-compose.yml up -d --build
```

**QA:**
```bash
docker-compose -f docker/qa/docker-compose.yml up -d --build
```

**Producción:**
```bash
# Asegúrate de configurar variables de entorno seguras antes de ejecutar
docker-compose -f docker/prod/docker-compose.yml up -d --build
```

### Troubleshooting Docker

#### La aplicación no inicia
```bash
# Verificar logs
docker-compose logs mivoto-app

# Verificar que PostgreSQL esté saludable
docker-compose ps postgres

# Reintentar construcción limpia
docker-compose down -v
docker-compose build --no-cache mivoto-app
docker-compose up -d
```

#### Error de conexión a base de datos
```bash
# Verificar que PostgreSQL esté corriendo
docker-compose ps postgres

# Verificar logs de PostgreSQL
docker-compose logs postgres

# Probar conexión manual
docker-compose exec postgres psql -U postgres -d mivoto -c "SELECT 1;"
```

#### Problemas con Flyway
```bash
# Limpiar la base de datos y reiniciar
docker-compose down -v
docker-compose up -d postgres
# Esperar a que PostgreSQL esté listo
sleep 10
docker-compose up -d mivoto-app
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
