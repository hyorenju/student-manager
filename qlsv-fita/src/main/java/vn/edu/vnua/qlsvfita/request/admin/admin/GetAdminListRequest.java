package vn.edu.vnua.qlsvfita.request.admin.admin;

import lombok.Data;
import vn.edu.vnua.qlsvfita.request.GetPageBaseRequest;

import javax.validation.constraints.NotNull;

@Data
public class GetAdminListRequest {
//    private GetPageBaseRequest setPage;
    private String id;

    @NotNull(message = "Số trang không được để trống")
    private Integer page;

    @NotNull(message = "Số bản ghi mỗi trang không được để trống")
    private Integer size;
}
