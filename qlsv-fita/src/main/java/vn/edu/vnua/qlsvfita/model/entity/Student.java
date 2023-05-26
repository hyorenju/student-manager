package vn.edu.vnua.qlsvfita.model.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(name = "name", length = 200)
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classes;

    @Column
    private Timestamp dob;

    @Column(name = "gender", length = 200)
    private String gender;

    @Column(name = "status", length = 200)
    private String status;

    @Column(name = "status_date")
    private Timestamp statusDate;

    @Column(name = "phone_number", length = 200)
    private String phoneNumber;

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "home_town", length = 200)
    private String homeTown;

    @Column(name = "residence", length = 200)
    private String residence;

    @Column(name = "father_name", length = 200)
    private String fatherName;

    @Column(name = "father_phone_number", length = 200)
    private String fatherPhoneNumber;

    @Column(name = "mother_name", length = 200)
    private String motherName;

    @Column(name = "mother_phone_number", length = 200)
    private String motherPhoneNumber;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "warning", length = 200)
    private String warning;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

//    @OneToMany(mappedBy = "student")
//    private Collection<Student> students;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "students_terms",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id")
    )
    private Collection<Term> terms;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getId())));
        }
        return authorities;
    }






}
