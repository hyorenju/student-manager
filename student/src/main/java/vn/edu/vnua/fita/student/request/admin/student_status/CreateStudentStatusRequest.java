package vn.edu.vnua.fita.student.request.admin.student_status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import vn.edu.vnua.fita.student.domain.validator.InputDate;

@Data
public class CreateStudentStatusRequest {
    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(max = 10, message = "Mã sinh viên quá dài")
    private String studentId;

    @NotNull(message = "Mã trạng thái không được để trống")
    private Integer statusId;

    @NotBlank(message = "Thời gian không được để trống")
    @InputDate(message = "Thời gian phải đúng định dạng dd/MM/yyyy")
    @Size(min = 8, max = 10, message = "Thời gian chỉ được 8-10 ký tự")
    private String time;

    private String note;
}
