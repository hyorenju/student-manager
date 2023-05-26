package vn.edu.vnua.qlsvfita.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "classes")
public class Class {
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(name = "class_name", length = 200)
    private String className;

    @Column(name = "num_of_stu")
    private Long numOfStu;

    @OneToMany(mappedBy = "classes", cascade = CascadeType.ALL)
    private Collection<Student> students;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "classes_terms",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id")
    )
    private Collection<Term> terms;
}
