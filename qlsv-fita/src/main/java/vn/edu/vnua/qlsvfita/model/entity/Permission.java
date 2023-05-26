package vn.edu.vnua.qlsvfita.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Collection<Role> roles;
}
