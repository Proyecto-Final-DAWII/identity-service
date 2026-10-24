package pe.edu.cibertec.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.cibertec.identity.entity.UserAccount;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, String> {
    boolean existsByUsernameOrEmail(String username, String email);
    Optional<UserAccount> findByEmail(String email);
    Optional<UserAccount> findByUsername(String username);
}
