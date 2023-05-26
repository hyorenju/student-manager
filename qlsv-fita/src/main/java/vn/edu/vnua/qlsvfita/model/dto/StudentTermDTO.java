package vn.edu.vnua.qlsvfita.model.dto;

import lombok.Data;
import vn.edu.vnua.qlsvfita.model.dto.classic.StudentDTO;
import vn.edu.vnua.qlsvfita.model.dto.classic.TermDTO;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Data
public class StudentTermDTO {
    private String studentId;
    private String studentName;
    private String termId;
    private Float medScore10;
    private Float medScore4;
    private Integer trainingPoint;
    private Integer creditsAccumulated;
    private Float scoreAccumulated10;
    private Float scoreAccumulated4;
}
