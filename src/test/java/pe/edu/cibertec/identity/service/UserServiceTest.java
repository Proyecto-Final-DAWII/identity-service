package pe.edu.cibertec.identity.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.cibertec.identity.dto.UserRequest;
import pe.edu.cibertec.identity.entity.Role;
import pe.edu.cibertec.identity.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void createsAUserWithTheRequestedRole() {
        UserRequest request = new UserRequest("aperez", "Ana Pérez", "ana@assetflow.pe", Role.SOPORTE_TI);
        when(repository.existsByUsernameOrEmail("aperez", "ana@assetflow.pe")).thenReturn(false);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var user = service.create(request);

        assertThat(user.username()).isEqualTo("aperez");
        assertThat(user.role()).isEqualTo(Role.SOPORTE_TI);
    }

    @Test
    void rejectsDuplicateUsernameOrEmail() {
        UserRequest request = new UserRequest("aperez", "Ana Pérez", "ana@assetflow.pe", Role.SOPORTE_TI);
        when(repository.existsByUsernameOrEmail("aperez", "ana@assetflow.pe")).thenReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("registrado");
    }
}
