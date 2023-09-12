package vn.edu.vnua.fita.student.request.admin.classes;

import lombok.Data;
import jakarta.validation.constraints.*;


@Data
public class UpdateClassRequest {
    private String id;

    @NotBlank(message = "Tên lớp không được để trống")
    private String name;

}
