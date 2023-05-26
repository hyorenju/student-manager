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
@Table(name = "terms")
public class Term {
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(name = "term_name", length = 200)
    private String termName;

    @Column(name = "num_of_stu")
    private Long numOfStu;

    @Column
    private Long excellent;

    @Column
    private Long good;

    @Column
    private Long fair;

    @Column
    private Long medium;

    @Column
    private Long weak;

    @Column
    private Long worst;

    @ManyToMany(mappedBy = "terms")
    private Collection<Student> students;

//    @OneToMany(mappedBy = "term")
//    private Collection<Term> terms;

    @ManyToMany(mappedBy = "terms")
    private Collection<Class> classes;

    @ManyToMany(mappedBy = "terms")
    private Collection<Course> courses;

    @ManyToMany(mappedBy = "terms")
    private Collection<Major> majors;




}
