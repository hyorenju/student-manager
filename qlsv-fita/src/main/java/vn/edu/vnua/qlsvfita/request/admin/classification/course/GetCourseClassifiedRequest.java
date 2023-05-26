package vn.edu.vnua.qlsvfita.request.admin.classification.course;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GetCourseClassifiedRequest {
    private String courseId;

    private String termId;

    @NotNull(message = "Số trang không được để trống")
    private Integer page;


    @NotNull(message = "Số bản ghi mỗi trang không được để trống")
    private Integer size;
}
