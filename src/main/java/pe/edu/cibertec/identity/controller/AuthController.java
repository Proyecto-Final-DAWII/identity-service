package pe.edu.cibertec.identity.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.cibertec.identity.dto.AuthResponse;
import pe.edu.cibertec.identity.dto.LoginRequest;
import pe.edu.cibertec.identity.dto.UserResponse;
import pe.edu.cibertec.identity.entity.UserAccount;
import pe.edu.cibertec.identity.repository.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        UserAccount user = userRepository.findByEmail(request.email())
                .or(() -> userRepository.findByUsername(request.email()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));

        String token = "jwt." + user.getRole().name().toLowerCase() + "." + UUID.randomUUID();
        String expiresAt = Instant.now().plus(8, ChronoUnit.HOURS).toString();

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(new AuthResponse(token, "Bearer", expiresAt, userResponse));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        UserAccount user = userRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay usuarios registrados"));

        return ResponseEntity.ok(new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada correctamente"));
    }
}
