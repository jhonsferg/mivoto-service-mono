# Docker Environment Setup Guide

This document provides comprehensive documentation for the Docker environment configuration of the MiVoto Electronic Voting System.

## 📦 Docker Components

### Services Overview

| Service | Image | Purpose | Port |
|---------|-------|---------|------|
| **mivoto-app** | Custom (built from Dockerfile) | Spring Boot Application | 8080 |
| **postgres** | postgres:16.1-alpine | PostgreSQL Database | 5432 |
| **redis** | redis:7.2.4-alpine | Cache & Session Store | 6379 |
| **pgadmin** | dpage/pgadmin4:8.2 | PostgreSQL Admin UI | 5050 |
| **redis-commander** | rediscommander/redis-commander | Redis Admin UI | 8082 |

## 🐳 Dockerfile

### Multi-Stage Build Architecture

The Dockerfile uses a two-stage build process:

**Stage 1: Builder**
- Base image: `maven:3.9.6-eclipse-temurin-17-alpine`
- Downloads Maven dependencies (cached layer)
- Compiles the Spring Boot application
- Produces executable JAR file

**Stage 2: Runtime**
- Base image: `eclipse-temurin:17-jre-alpine` (smaller, JRE only)
- Non-root user for security (`mivoto` user)
- Health check configuration
- Optimized JVM settings for containers

### Security Features
- ✅ Non-root user execution
- ✅ Minimal base image (Alpine)
- ✅ No build tools in runtime image
- ✅ Health check endpoint

### Build Optimization
- ✅ Layer caching for dependencies
- ✅ Multi-stage build reduces image size
- ✅ .dockerignore excludes unnecessary files

## 🔧 Configuration Files

### docker-compose.yaml

**Key Features:**
- All services use health checks
- Service dependencies ensure proper startup order
- Environment variables support (via .env file)
- Named volumes for data persistence
- Custom network for service communication
- Optimized PostgreSQL performance parameters

**Network Configuration:**
```yaml
networks:
  mivoto-dev-network:
    driver: bridge
```

All services communicate through this internal network.

**Volume Configuration:**
```yaml
volumes:
  postgres_data:      # PostgreSQL database files
  redis_data:         # Redis persistence
  pgadmin_data:       # pgAdmin configuration
```

### Environment Variables (.env)

The `.env` file configures all services:

**Application Variables:**
- `DATABASE_URL` - JDBC connection string
- `DATABASE_USER` - PostgreSQL username
- `DATABASE_PASSWORD` - PostgreSQL password
- `REDIS_HOST` - Redis hostname (use `redis` for Docker)
- `REDIS_PORT` - Redis port
- `JWT_SECRET` - JWT signing secret
- `PORT` - Application port
- `SPRING_PROFILES_ACTIVE` - Active Spring profile

**PostgreSQL Variables:**
- `POSTGRES_DB` - Database name (default: `mivoto`)
- `POSTGRES_USER` - Superuser name
- `POSTGRES_PASSWORD` - Superuser password

**Admin Tools:**
- `PGADMIN_EMAIL` - pgAdmin login email
- `PGADMIN_PASSWORD` - pgAdmin login password
- `REDIS_COMMANDER_PORT` - Redis Commander port

### PostgreSQL Initialization

**File:** `docker/postgres/init.sql`

Executed when the PostgreSQL container is first created:
- Enables `uuid-ossp` extension for UUID generation
- Enables `pgcrypto` extension for cryptographic functions
- Sets timezone to `America/Lima`
- Flyway handles all schema migrations

### pgAdmin Configuration

**File:** `docker/pgadmin/servers.json`

Pre-configures pgAdmin with the PostgreSQL server connection:
- Server name: "MiVoto DB"
- Host: `postgres` (Docker network hostname)
- Database: `mivoto`
- Auto-connects on pgAdmin startup

## 🚀 Usage

### Quick Start
```bash
# 1. Create .env file
cp .env.example .env

# 2. Start all services
docker-compose up -d

# 3. Check status
docker-compose ps

# 4. View application logs
docker-compose logs -f mivoto-app
```

### Application URLs
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health
- **pgAdmin**: http://localhost:5050
- **Redis Commander**: http://localhost:8082

### Common Commands

**View Logs:**
```bash
docker-compose logs -f mivoto-app
docker-compose logs -f postgres
docker-compose logs -f redis
```

**Restart Services:**
```bash
docker-compose restart mivoto-app
docker-compose restart postgres
```

**Rebuild Application:**
```bash
docker-compose build --no-cache mivoto-app
docker-compose up -d mivoto-app
```

**Database Access:**
```bash
# Connect to PostgreSQL
docker-compose exec postgres psql -U postgres -d mivoto

# Run SQL file
docker-compose exec -T postgres psql -U postgres -d mivoto < backup.sql
```

**Clean Restart:**
```bash
docker-compose down -v
docker-compose up -d
```

## 🔍 Health Checks

### Application Health Check
```bash
wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health
```
- Interval: 30s
- Timeout: 10s
- Start period: 60s (allows Flyway migrations to complete)
- Retries: 3

### PostgreSQL Health Check
```bash
pg_isready -U postgres -d mivoto
```
- Interval: 5s
- Timeout: 3s
- Retries: 5

### Redis Health Check
```bash
redis-cli ping
```
- Interval: 5s
- Timeout: 3s
- Retries: 5

## 🛠️ Troubleshooting

### Application Won't Start

**Check Dependencies:**
```bash
docker-compose ps postgres redis
```

Both should show "healthy" status.

**Check Application Logs:**
```bash
docker-compose logs mivoto-app
```

Look for:
- Database connection errors
- Flyway migration failures
- Port conflicts

### Database Connection Issues

**Verify PostgreSQL is Running:**
```bash
docker-compose exec postgres pg_isready -U postgres -d mivoto
```

**Test Connection:**
```bash
docker-compose exec postgres psql -U postgres -d mivoto -c "SELECT 1;"
```

**Check Network:**
```bash
docker network inspect mivoto-dev-network
```

### Flyway Migration Failures

**Clean Restart:**
```bash
docker-compose down -v  # WARNING: Deletes all data
docker-compose up -d postgres
sleep 10
docker-compose up -d mivoto-app
```

**Check Migration History:**
```bash
docker-compose exec postgres psql -U postgres -d mivoto -c "SELECT * FROM flyway_schema_history;"
```

### Port Conflicts

If ports are already in use:

**Edit `.env` file:**
```env
PORT=8081
POSTGRES_PORT=5433
REDIS_PORT=6380
PGADMIN_PORT=5051
REDIS_COMMANDER_PORT=8083
```

**Restart services:**
```bash
docker-compose down
docker-compose up -d
```

## 🔐 Security Considerations

### Production Recommendations

1. **Change Default Passwords:**
   - Update `POSTGRES_PASSWORD` in .env
   - Update `JWT_SECRET` with a strong random key
   - Update `PGADMIN_PASSWORD`

2. **Use Docker Secrets:**
   For production deployments, consider using Docker secrets instead of .env files.

3. **Restrict Network Access:**
   - Don't expose PostgreSQL/Redis ports publicly
   - Use reverse proxy for the application
   - Enable SSL/TLS for all connections

4. **Regular Updates:**
   - Keep Docker images updated
   - Monitor security advisories

## 📊 Performance Tuning

### PostgreSQL Configuration

Optimized for development (in docker-compose.yaml):
```yaml
max_connections=200
shared_buffers=256MB
effective_cache_size=1GB
work_mem=4MB
```

### Redis Configuration

```yaml
maxmemory 256mb
maxmemory-policy allkeys-lru
appendonly yes
```

### JVM Configuration

```yaml
JAVA_OPTS: "-Xmx512m -Xms256m"
```

Adjust based on available system memory.

## 📝 Notes

- All containers use `restart: unless-stopped` policy
- Volumes are named for easy identification
- All services use health checks for reliability
- Network is isolated for security
- Logs are available via `docker-compose logs`
