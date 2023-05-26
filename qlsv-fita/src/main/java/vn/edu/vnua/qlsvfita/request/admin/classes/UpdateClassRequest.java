package vn.edu.vnua.qlsvfita.request.admin.classes;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateClassRequest {
    private String id;

    @NotBlank(message = "Tên lớp không được để trống")
    private String name;

}
