package vn.edu.vnua.qlsvfita.request.admin.classification.course;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StatisticCourseRequest {
    @NotBlank(message = "Mã khóa không được để trống")
    private String courseId;

    @NotBlank(message = "Mã học kỳ không được để trống")
    private String termId;
}
