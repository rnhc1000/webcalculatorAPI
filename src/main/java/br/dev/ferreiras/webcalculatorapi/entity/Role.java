package br.dev.ferreiras.webcalculatorapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name="tb_roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long roleId;

    private String role;

    private String authority;

    public enum Roles {
        ROLE_ADMIN(1L),
        ROLE_USER(2L);
        final long roleId;
        Roles(long roleId) {
            this.roleId = roleId;
        }
    }

    /**
     * @return authority (READ | WRITE)
     */
    @Override
    public String getAuthority() {
        return authority;
    }
}
