package pe.com.mivoto.service.domain.enums;

import lombok.Getter;

@Getter
public enum AuditAction {
    LOGIN("Inicio de Sesión"),
    LOGOUT("Cierre de Sesión"),
    LOGIN_FAILED("Intento de Login Fallido"),
    PASSWORD_CHANGED("Contraseña Cambiada"),

    VOTE_CAST("Voto Emitido"),
    VOTE_VERIFIED("Voto Verificado"),
    VOTE_REJECTED("Voto Rechazado"),

    ELECTION_CREATED("Elección Creada"),
    ELECTION_UPDATED("Elección Actualizada"),
    ELECTION_STARTED("Elección Iniciada"),
    ELECTION_CLOSED("Elección Cerrada"),
    ELECTION_CANCELLED("Elección Cancelada"),

    CANDIDATE_ADDED("Candidato Agregado"),
    CANDIDATE_UPDATED("Candidato Actualizado"),
    CANDIDATE_REMOVED("Candidato Removido"),

    USER_CREATED("Usuario Creado"),
    USER_UPDATED("Usuario Actualizado"),
    USER_DELETED("Usuario Eliminado"),
    USER_ACTIVATED("Usuario Activado"),
    USER_DEACTIVATED("Usuario Desactivado"),

    UNAUTHORIZED_ACCESS("Acceso No Autorizado"),
    DATA_ACCESSED("Datos Accedidos"),
    DATA_MODIFIED("Datos Modificados"),
    PERMISSION_DENIED("Permiso Denegado");

    private final String displayName;

    AuditAction(String displayName) {
        this.displayName = displayName;
    }
}
