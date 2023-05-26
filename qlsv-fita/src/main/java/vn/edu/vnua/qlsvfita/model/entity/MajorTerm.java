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
@Table(name = "majors_terms")
public class MajorTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "major_id", length = 100)
    private String majorId;

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
