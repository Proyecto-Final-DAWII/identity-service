package pe.edu.cibertec.identity.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.edu.cibertec.identity.entity.Role;
import pe.edu.cibertec.identity.entity.UserAccount;
import pe.edu.cibertec.identity.repository.UserRepository;

import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                UserAccount admin = new UserAccount();
                admin.setId("usr-admin-01");
                admin.setUsername("admin");
                admin.setFullName("Carlos Medina");
                admin.setEmail("admin@assetflow.com");
                admin.setRole(Role.ADMINISTRADOR_TI);
                userRepository.save(admin);

                UserAccount soporte = new UserAccount();
                soporte.setId("usr-soporte-01");
                soporte.setUsername("soporte");
                soporte.setFullName("Ana Morales");
                soporte.setEmail("soporte@assetflow.com");
                soporte.setRole(Role.SOPORTE_TI);
                userRepository.save(soporte);

                UserAccount colaborador = new UserAccount();
                colaborador.setId("usr-colaborador-01");
                colaborador.setUsername("colaborador");
                colaborador.setFullName("Juan Pérez");
                colaborador.setEmail("colaborador@assetflow.com");
                colaborador.setRole(Role.COLABORADOR);
                userRepository.save(colaborador);
            }
        };
    }
}
