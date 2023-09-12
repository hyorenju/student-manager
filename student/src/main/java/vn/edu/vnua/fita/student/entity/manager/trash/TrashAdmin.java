package vn.edu.vnua.fita.student.entity.manager.trash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.vnua.fita.student.entity.table.Admin;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trash_admins")
public class TrashAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Column
    private Timestamp time;

    @Column(name = "by_whom", length = 100)
    private String byWhom;
}
