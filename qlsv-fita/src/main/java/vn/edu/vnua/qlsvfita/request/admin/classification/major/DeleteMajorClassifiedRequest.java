package vn.edu.vnua.qlsvfita.request.admin.classification.major;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class DeleteMajorClassifiedRequest {
    @NotBlank(message = "Mã chuyên ngành không được để trống")
    @Pattern(regexp = "[A-Z]+", message = "Mã chuyên ngành phải là một chuỗi in hoa không khoảng trắng")
    private String majorId;

    @NotBlank(message = "Mã học kỳ không được để trống")
    @Pattern(regexp = "^\\d{4}[1-3]$", message = "Mã học kỳ phải có dạng 'năm + học kỳ' Ví dụ 'Học kỳ 1 năm 2020' được viết dưới dạng: 20201")
    private String termId;
}
