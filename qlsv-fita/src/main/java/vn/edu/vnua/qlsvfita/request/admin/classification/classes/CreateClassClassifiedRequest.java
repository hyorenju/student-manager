package vn.edu.vnua.qlsvfita.request.admin.classification.classes;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CreateClassClassifiedRequest {
    @NotBlank(message = "Mã lớp không được để trống")
    @Pattern(regexp = "^K\\d+[A-Z]+$", message = "Mã lớp phải có dạng 'Mã_khóa + mã_chuyên_ngành + định_danh_lớp'. (Ví dụ: K65CNTTA)")
    private String classId;

    @NotBlank(message = "Mã học kỳ không được để trống")
    @Pattern(regexp = "^\\d{4}[1-3]$", message = "Mã học kỳ phải có dạng 'năm + học kỳ' Ví dụ 'Học kỳ 1 năm 2020' được viết dưới dạng: 20201")
    private String termId;
}
