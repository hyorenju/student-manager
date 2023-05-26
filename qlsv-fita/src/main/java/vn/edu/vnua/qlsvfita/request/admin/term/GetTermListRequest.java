package vn.edu.vnua.qlsvfita.request.admin.term;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetTermListRequest {
    private String id;

    @NotNull(message = "Số trang không được để trống")
    private Integer page;

    @NotNull(message = "Số bản ghi mỗi trang không được để trống")
    private Integer size;
}
