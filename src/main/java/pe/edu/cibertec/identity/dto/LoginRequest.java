package pe.edu.cibertec.identity.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "El email no puede estar vacío")
        String email,
        @NotBlank(message = "La contraseña no puede estar vacía")
        String password
) {
}
