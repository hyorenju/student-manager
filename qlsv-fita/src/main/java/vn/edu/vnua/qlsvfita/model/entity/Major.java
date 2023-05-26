package vn.edu.vnua.qlsvfita.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "majors")
public class Major {
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "num_of_stu")
    private Long numOfStu;

    @Column(name = "total_credits")
    private Integer totalCredits;

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL)
    private Collection<Student> students;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "majors_terms",
            joinColumns = @JoinColumn(name = "major_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id")
    )
    private Collection<Term> terms;
}

