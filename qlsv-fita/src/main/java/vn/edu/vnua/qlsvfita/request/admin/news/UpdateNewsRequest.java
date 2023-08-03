package vn.edu.vnua.qlsvfita.request.admin.news;

import lombok.Data;
import vn.edu.vnua.qlsvfita.validator.validation.InputDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
public class UpdateNewsRequest {
    private Long id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 1000, message = "Tiêu đề tin chỉ được tối đa 1000 ký tự")
    private String title;

    @NotBlank(message = "Ngày đăng không được để trống")
    @InputDate(message = "Ngày đăng phải đúng định dạng dd/MM/yyyy")
    private String postDate;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 8000, message = "Mô tả chỉ được tối đa 8000 ký tự")
    private String description;

    @NotBlank(message = "Nội dung bài đăng không được để trống")
    @Size(max = 2100000000, message = "Nội dung bài đăng quá dài")
    private String content;

    private String type;
}
