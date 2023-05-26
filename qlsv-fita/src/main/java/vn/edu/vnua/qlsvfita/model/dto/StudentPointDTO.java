package vn.edu.vnua.qlsvfita.model.dto;

import lombok.Data;

@Data
public class StudentPointDTO {
    private String termId;
    private Float medScore10;
    private Float medScore4;
    private Integer trainingPoint;
}
