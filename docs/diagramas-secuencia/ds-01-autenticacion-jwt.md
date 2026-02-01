# DS-01: Autenticación JWT

## Diagrama de Secuencia

```plantuml
@startuml
autonumber
actor "Usuario" as user
participant "Controller\nAuthController" as controller
participant "Service\nAuthService" as service
participant "Repository\nUserRepository" as repo
participant "Security\nJwtTokenProvider" as jwt
participant "Security\nPasswordEncoder" as encoder
participant "Cache\nRedis" as redis
participant "Audit\nAuditService" as audit
database "PostgreSQL" as db

== Inicio de Sesión ==
user -> controller: POST /api/auth/login\n{username, password}
activate controller

controller -> service: login(LoginRequest)
activate service

service -> repo: findByUsernameOrEmail(username)
activate repo
repo -> db: SELECT * FROM users WHERE...
db --> repo: User entity
repo --> service: Optional<User>
deactivate repo

alt Usuario no encontrado
    service --> controller: throw UserNotFoundException
    controller --> user: 404 Not Found
else Usuario encontrado
    service -> encoder: matches(rawPassword, encodedPassword)
    activate encoder
    encoder --> service: boolean
    deactivate encoder

    alt Contraseña incorrecta
        service -> audit: logFailedLogin(username)
        service --> controller: throw InvalidCredentialsException
        controller --> user: 401 Unauthorized
    else Contraseña correcta
        service -> service: checkUserActive()

        alt Usuario inactivo
            service --> controller: throw UserInactiveException
            controller --> user: 403 Forbidden
        else Usuario activo
            service -> jwt: generateAccessToken(user)
            activate jwt
            jwt -> jwt: createToken(claims, expiration=1h)
            jwt --> service: accessToken
            deactivate jwt

            service -> jwt: generateRefreshToken(user)
            activate jwt
            jwt -> jwt: createToken(claims, expiration=7d)
            jwt --> service: refreshToken
            deactivate jwt

            service -> redis: set("session:" + userId, sessionData, TTL=7d)
            activate redis
            redis --> service: OK
            deactivate redis

            service -> repo: updateLastLogin(userId)
            activate repo
            repo -> db: UPDATE users SET last_login = NOW()
            db --> repo: OK
            repo --> service: void
            deactivate repo

            service -> audit: logSuccessfulLogin(user)
            activate audit
            audit -> db: INSERT INTO audit_logs
            db --> audit: OK
            audit --> service: void
            deactivate audit

            service --> controller: LoginResponse(tokens, user)
        end
    end
end

controller --> user: 200 OK\n{accessToken, refreshToken, user}
deactivate service
deactivate controller

@enduml
```

## Descripción del Flujo

### Actores y Componentes

- **Usuario**: Actor que inicia sesión
- **AuthController**: Controlador REST que recibe la petición
- **AuthService**: Servicio de aplicación que orquesta la lógica
- **UserRepository**: Repositorio de acceso a datos de usuarios
- **JwtTokenProvider**: Proveedor de tokens JWT
- **PasswordEncoder**: Codificador de contraseñas (BCrypt)
- **Redis**: Cache para sesiones y blacklist
- **AuditService**: Servicio de auditoría
- **PostgreSQL**: Base de datos principal

### Pasos del Flujo

1. **Recepción de Credenciales**: El usuario envía username y password
2. **Búsqueda de Usuario**: Se busca el usuario por username o email
3. **Validación de Existencia**: Si no existe, retorna 404
4. **Validación de Contraseña**: Se compara con BCrypt
5. **Registro de Intento Fallido**: Si falla, se audita y retorna 401
6. **Validación de Estado**: Se verifica que el usuario esté activo
7. **Generación de Access Token**: JWT con expiración de 1 hora
8. **Generación de Refresh Token**: JWT con expiración de 7 días
9. **Almacenamiento en Redis**: Se guarda la sesión en cache
10. **Actualización de Último Acceso**: Se actualiza last_login en BD
11. **Registro de Auditoría**: Se registra el login exitoso
12. **Respuesta al Usuario**: Se retornan los tokens y datos del usuario

### Consideraciones de Seguridad

- Las contraseñas se almacenan con BCrypt (factor 12)
- Los tokens JWT están firmados con HS256
- Las sesiones se almacenan en Redis con TTL
- Todos los eventos se auditan en PostgreSQL
- Rate limiting aplicado en el controller

### Estructuras de Datos

**Claims del JWT**:

```json
{
  "sub": "userId",
  "username": "string",
  "role": "VOTER|ADMIN|SUPERVISOR",
  "iat": "timestamp",
  "exp": "timestamp"
}
```

**Sesión en Redis**:

```json
{
  "userId": "long",
  "username": "string",
  "role": "string",
  "loginTime": "datetime",
  "lastActivity": "datetime"
}
```
