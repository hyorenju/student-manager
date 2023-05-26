package vn.edu.vnua.qlsvfita.request.admin.point;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class DeletePointRequest {
    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(max = 10, message = "Mã sinh viên chỉ được tối đa 10 ký tự")
    private String studentId;

    @NotBlank(message = "Mã học kỳ không được để trống")
    @Size(min = 5, max = 5, message = "Mã học kỳ phải đủ 5 ký tự")
    private String termId;

}
