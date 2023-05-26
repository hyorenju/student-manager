package vn.edu.vnua.qlsvfita.request.admin.classification.classes;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetClassClassifiedRequest {
    private String classId;

    private String termId;

    @NotNull(message = "Số trang không được để trống")
    private Integer page;


    @NotNull(message = "Số bản ghi mỗi trang không được để trống")
    private Integer size;
}
