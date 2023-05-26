package vn.edu.vnua.qlsvfita.model.dto;

import lombok.Data;

@Data
public class PointListDTO {
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
