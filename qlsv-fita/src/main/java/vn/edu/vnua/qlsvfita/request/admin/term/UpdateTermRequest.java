package vn.edu.vnua.qlsvfita.request.admin.term;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdateTermRequest {
    private String id;
    @NotBlank(message = "Tên học kỳ không được để trống")
    @Pattern(regexp = "Học kỳ [1-3] - năm \\d{4}", message = "Tên học kỳ phải có dạng 'Học kỳ x - năm y'")
    private String name;
}
