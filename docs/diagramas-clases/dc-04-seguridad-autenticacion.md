# DC-04: Seguridad y Autenticación

## Diagrama de Clases

```plantuml
@startuml
skinparam classAttributeIconSize 0

package "Spring Security Config" {
    class SecurityConfiguration {
        + filterChain(http: HttpSecurity): SecurityFilterChain
        + authenticationManager(config: AuthenticationConfiguration): AuthenticationManager
        + passwordEncoder(): PasswordEncoder
    }

    class JwtAuthenticationFilter {
        - jwtTokenProvider: JwtTokenProvider
        - userDetailsService: CustomUserDetailsService
        + doFilterInternal(request, response, chain): void
    }

    class JwtTokenProvider {
        - secretKey: String
        - validityInMilliseconds: long
        + createToken(authentication: Authentication): String
        + getUserIdFromJWT(token: String): Long
        + validateToken(token: String): boolean
    }
}

package "User Details Service" {
    interface UserDetailsService {
        + loadUserByUsername(username: String): UserDetails
    }

    class CustomUserDetailsService {
        - userRepository: UserRepository
        + loadUserByUsername(usernameOrEmail: String): UserDetails
        + loadUserById(id: Long): UserDetails
    }

    class UserPrincipal {
        - id: Long
        - name: String
        - username: String
        - email: String
        - password: String
        - authorities: Collection<GrantedAuthority>
        + create(user: User): UserPrincipal
    }

    UserDetailsService <|.. CustomUserDetailsService
    UserDetails <|.. UserPrincipal
}

package "Domain" {
    class User <<entity>> {
        - id: Long
        - username: String
        - password: String
        - roles: Set<Role>
    }
}

SecurityConfiguration --> JwtAuthenticationFilter
JwtAuthenticationFilter --> JwtTokenProvider
JwtAuthenticationFilter --> CustomUserDetailsService

CustomUserDetailsService --> UserPrincipal : creates
CustomUserDetailsService --> User : retrieves

JwtTokenProvider ..> UserPrincipal : uses claims from

note right of JwtAuthenticationFilter
  Intercepta cada petición HTTP,
  extrae el token JWT del header 'Authorization',
  lo valida y establece la autenticación en el contexto de seguridad.
end note

note right of UserPrincipal
  Implementación de UserDetails de Spring Security.
  Adapta la entidad 'User' del dominio al modelo de seguridad.
end note

@enduml
```

## Descripción de Componentes de Seguridad

### SecurityConfiguration

Clase de configuración principal de Spring Security. Define:

- Cadena de filtros de seguridad.
- Reglas de autorización por endpoint (quién puede acceder a qué).
- Configuración de manejo de sesiones (stateless para REST).
- Bean de encriptación de contraseñas (BCrypt).

### JwtAuthenticationFilter

Filtro personalizado que se ejecuta una vez por petición (`OncePerRequestFilter`).

1. Obtiene el token JWT del header.
2. Valida la firma y expiración del token usando `JwtTokenProvider`.
3. Si es válido, carga los detalles del usuario y lo autentica en `SecurityContextHolder`.

### JwtTokenProvider

Componente utilitario encargado de:

- Generar tokens JWT firmados con una clave secreta (HMAC SHA-256).
- Extraer claims (datos) como userId del token.
- Validar la integridad y vigencia del token.

### CustomUserDetailsService

Servicio que implementa la interfaz estándar de Spring Security `UserDetailsService`.

- Su única responsabilidad es cargar los datos del usuario desde la base de datos (`UserRepository`) y devolver una implementación de `UserDetails`.

### UserPrincipal

Adaptador (Adapter Pattern) que convierte nuestra entidad de dominio `User` en un objeto `UserDetails` que Spring Security entiende.

- Contiene los datos necesarios para autenticación y autorización (password, roles/authorities).
- Implementa `isEnabled`, `isAccountNonLocked`, etc., basándose en el estado del usuario.
