package vn.edu.vnua.qlsvfita.model.dto;

import lombok.Data;

@Data
public class StudentAccumulationDTO {
    private Integer creditsAccumulated;
    private Integer totalCredits;
    private Float scoreAccumulated10;
    private Float scoreAccumulated4;
}
