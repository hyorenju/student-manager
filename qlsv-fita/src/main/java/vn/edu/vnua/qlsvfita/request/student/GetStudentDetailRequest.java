package vn.edu.vnua.qlsvfita.request.student;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GetStudentDetailRequest {
    @NotBlank(message = "Mã sinh viên không được để trống")
    private String id;
}
