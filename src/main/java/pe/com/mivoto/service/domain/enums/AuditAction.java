package pe.com.mivoto.service.domain.enums;

import lombok.Getter;

/**
 * Enum representing various auditable actions in the system.
 */
@Getter
public enum AuditAction {
    /** User successfully logged in. */
    LOGIN("Inicio de Sesión"),
    /** User logged out. */
    LOGOUT("Cierre de Sesión"),
    /** A login attempt failed. */
    LOGIN_FAILED("Intento de Login Fallido"),
    /** User successfully changed their password. */
    PASSWORD_CHANGED("Contraseña Cambiada"),

    /** A vote was successfully cast. */
    VOTE_CAST("Voto Emitido"),
    /** A vote was successfully verified. */
    VOTE_VERIFIED("Voto Verificado"),
    /** A vote was rejected. */
    VOTE_REJECTED("Voto Rechazado"),

    /** A new election was created. */
    ELECTION_CREATED("Elección Creada"),
    /** An existing election was updated. */
    ELECTION_UPDATED("Elección Actualizada"),
    /** An election has officially started. */
    ELECTION_STARTED("Elección Iniciada"),
    /** An election has officially closed. */
    ELECTION_CLOSED("Elección Cerrada"),
    /** An election was cancelled. */
    ELECTION_CANCELLED("Elección Cancelada"),

    /** A new candidate was added to an election. */
    CANDIDATE_ADDED("Candidato Agregado"),
    /** Candidate details were updated. */
    CANDIDATE_UPDATED("Candidato Actualizado"),
    /** A candidate was removed from an election. */
    CANDIDATE_REMOVED("Candidato Removido"),

    /** A new user account was created. */
    USER_CREATED("Usuario Creado"),
    /** User details were updated. */
    USER_UPDATED("Usuario Actualizado"),
    /** A user account was deleted. */
    USER_DELETED("Usuario Eliminado"),
    /** A user account was activated. */
    USER_ACTIVATED("Usuario Activado"),
    /** A user account was deactivated. */
    USER_DEACTIVATED("Usuario Desactivado"),

    /** An unauthorized access attempt was detected. */
    UNAUTHORIZED_ACCESS("Acceso No Autorizado"),
    /** sensitive data was accessed. */
    DATA_ACCESSED("Datos Accedidos"),
    /** Data was modified outside normal flows. */
    DATA_MODIFIED("Datos Modificados"),
    /** A request was denied due to insufficient permissions. */
    PERMISSION_DENIED("Permiso Denegado");

    /** The human-readable name for the action. */
    private final String displayName;

    AuditAction(String displayName) {
        this.displayName = displayName;
    }
}
