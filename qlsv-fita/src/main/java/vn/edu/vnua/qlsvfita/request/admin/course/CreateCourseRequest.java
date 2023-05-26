package vn.edu.vnua.qlsvfita.request.admin.course;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CreateCourseRequest {
    @NotBlank(message = "Mã khóa không được để trống")
    @Pattern(regexp = "^K\\d+$", message = "Mã khóa phải có dạng Kx, trong đó x là số tuổi của học viện. (Ví dụ: Khóa 65 là K65)")
    private String id;
}
