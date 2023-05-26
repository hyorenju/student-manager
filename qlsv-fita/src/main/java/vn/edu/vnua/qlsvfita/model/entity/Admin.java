package vn.edu.vnua.qlsvfita.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "password", length = 200)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getId())));
        }
        return authorities;
    }
}
