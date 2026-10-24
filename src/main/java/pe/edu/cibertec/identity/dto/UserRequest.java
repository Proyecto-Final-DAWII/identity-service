package pe.edu.cibertec.identity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.cibertec.identity.entity.Role;

public record UserRequest(
        @NotBlank String username,
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @NotNull Role role
) {
}
