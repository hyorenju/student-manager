package vn.edu.vnua.fita.student.request.admin.classes;

import lombok.Data;
import jakarta.validation.constraints.*;


@Data
public class CreateClassRequest {
    @NotBlank(message = "Mã lớp không được để trống")
    @Pattern(regexp = "^K\\d+[A-Z]+$", message = "Mã lớp phải có dạng 'Mã_khóa + mã_chuyên_ngành + định_danh_lớp'. (Ví dụ: K65CNTTA)")
    private String id;

    @NotBlank(message = "Tên lớp không được để trống")
    private String name;
}
