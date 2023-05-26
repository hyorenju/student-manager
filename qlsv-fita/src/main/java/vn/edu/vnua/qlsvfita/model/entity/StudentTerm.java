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
@Table(name = "students_terms")
public class StudentTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", length = 100)
    private String studentId;

    @Column(name = "student_name", length = 200)
    private String studentName;

//    @ManyToOne
//    @JoinColumn(name = "student_id")
//    private Student student;

    @Column(name = "term_id", length = 100)
    private String termId;

//    @ManyToOne
//    @JoinColumn(name = "term_id")
//    private Term term;

    @Column(name = "med_score_10")
    private Float medScore10;

    @Column(name = "med_score_4")
    private Float medScore4;

    @Column(name = "training_point")
    private Integer trainingPoint;

    @Column(name = "credits_accumulated")
    private Integer creditsAccumulated;

    @Column(name = "score_accumulated_10")
    private Float scoreAccumulated10;

    @Column(name = "score_accumulated_4")
    private Float scoreAccumulated4;















}
