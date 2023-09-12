package vn.edu.vnua.fita.student.entity.authorization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String type;

    @ManyToMany(mappedBy = "permissions")
    private Collection<Role> roles;
}
