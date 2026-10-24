package pe.edu.cibertec.identity.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.cibertec.identity.dto.UserRequest;
import pe.edu.cibertec.identity.dto.UserResponse;
import pe.edu.cibertec.identity.entity.UserAccount;
import pe.edu.cibertec.identity.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UserResponse create(UserRequest request) {
        if (repository.existsByUsernameOrEmail(request.username(), request.email())) {
            throw new IllegalArgumentException("El usuario o correo ya está registrado");
        }
        UserAccount user = new UserAccount();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(request.username());
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setRole(request.role());
        return toResponse(repository.save(user));
    }

    @Transactional(readOnly = true)
    public UserResponse findById(String id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    private UserResponse toResponse(UserAccount user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getFullName(), user.getEmail(), user.getRole());
    }
}
