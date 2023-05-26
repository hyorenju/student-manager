package vn.edu.vnua.qlsvfita.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses_terms")
public class CourseTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id", length = 100)
    private String courseId;

    @Column(name = "term_id", length = 100)
    private String termId;

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
}
