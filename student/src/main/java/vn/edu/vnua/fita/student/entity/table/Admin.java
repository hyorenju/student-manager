package vn.edu.vnua.fita.student.entity.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import vn.edu.vnua.fita.student.entity.authorization.Role;

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
    @Column(length = 100)
    private String id;

    @Column(length = 200)
    private String name;

    @Column(length = 200)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(length = 1000)
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role.getId()));
            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getId())));
        }
        return authorities;
    }
}
