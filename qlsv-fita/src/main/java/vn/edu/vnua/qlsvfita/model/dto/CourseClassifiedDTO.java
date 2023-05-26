package vn.edu.vnua.qlsvfita.model.dto;

import lombok.Data;

@Data
public class CourseClassifiedDTO {
    private String courseId;
    private String termId;
    private Long numOfStu;
    private Long excellent;
    private Long good;
    private Long fair;
    private Long medium;
    private Long weak;
    private Long worst;
}
