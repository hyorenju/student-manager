package vn.edu.vnua.qlsvfita.request.admin.classification.course;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdateCourseClassifiedRequest {
    @NotBlank(message = "Mã khóa không được để trống")
    @Pattern(regexp = "^K\\d+$", message = "Mã khóa phải có dạng Kx, trong đó x là số tuổi của học viện. (Ví dụ: Khóa 65 là K65)")
    private String courseId;

    @NotBlank(message = "Mã học kỳ không được để trống")
    @Pattern(regexp = "^\\d{4}[1-3]$", message = "Mã học kỳ phải có dạng 'năm + học kỳ' Ví dụ 'Học kỳ 1 năm 2020' được viết dưới dạng: 20201")
    private String termId;
}
