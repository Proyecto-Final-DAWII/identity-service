package pe.edu.cibertec.identity.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        String expiresAt,
        UserResponse user
) {
}
