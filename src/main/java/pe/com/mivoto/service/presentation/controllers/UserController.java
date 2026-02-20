package pe.com.mivoto.service.presentation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.mivoto.service.application.usecases.user.GetAllUsersUseCaseImpl;
import pe.com.mivoto.service.domain.model.User;
import pe.com.mivoto.service.presentation.dto.response.ApiResponseDto;
import pe.com.mivoto.service.presentation.dto.response.UserDto;
import pe.com.mivoto.service.presentation.mappers.AuthDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for User Management.
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints de gestión de usuarios")
public class UserController {

    private final GetAllUsersUseCaseImpl getAllUsersUseCase;
    private final AuthDtoMapper authDtoMapper;

    /**
     * Retrieves all users. Only accessible by ADMIN.
     *
     * @return ResponseEntity containing a list of all users.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios del sistema")
    public ResponseEntity<ApiResponseDto<List<UserDto>>> getAllUsers() {
        log.info("Obteniendo todos los usuarios");
        List<User> users = getAllUsersUseCase.execute();
        List<UserDto> response = users.stream()
                .map(authDtoMapper::toUserDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponseDto.success("Usuarios obtenidos exitosamente", response));
    }
}
