package pe.edu.cibertec.identity.dto;

import pe.edu.cibertec.identity.entity.Role;

public record UserResponse(String id, String username, String fullName, String email, Role role) {
}
