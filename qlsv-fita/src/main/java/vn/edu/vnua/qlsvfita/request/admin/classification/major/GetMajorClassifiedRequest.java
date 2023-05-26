package vn.edu.vnua.qlsvfita.request.admin.classification.major;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetMajorClassifiedRequest {
    private String majorId;

    private String termId;

    @NotNull(message = "Số trang không được để trống")
    private Integer page;


    @NotNull(message = "Số bản ghi mỗi trang không được để trống")
    private Integer size;
}
