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
@Table(name = "courses")
public class Course {
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(name = "name",length = 200)
    private String name;

    @Column(name = "num_of_stu")
    private Long numOfStu;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Collection<Student> students;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "courses_terms",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "term_id")
    )
    private Collection<Term> terms;



}
