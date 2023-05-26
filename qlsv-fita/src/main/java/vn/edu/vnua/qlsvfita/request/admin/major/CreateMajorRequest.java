package vn.edu.vnua.qlsvfita.request.admin.major;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CreateMajorRequest {
    @NotBlank(message = "Mã chuyên ngành không được để trống")
    @Pattern(regexp = "[A-Z]+", message = "Mã chuyên ngành phải là một chuỗi in hoa không khoảng trắng")
    private String id;

    @NotBlank(message = "Tên chuyên ngành không được để trống")
    private String name;

    @NotNull(message = "Số lượng tín chỉ của chuyên ngành không được để trống")
    private Integer totalCredits;
}
