package vn.edu.vnua.qlsvfita.request.admin.term;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateTermRequest {
    @NotBlank(message = "Mã học kỳ không được để trống")
    @Pattern(regexp = "^\\d{4}[1-3]$", message = "Mã học kỳ phải có dạng 'năm + học kỳ' Ví dụ 'Học kỳ 1 năm 2020' được viết dưới dạng: 20201")
    private String id;

}
