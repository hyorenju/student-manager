package vn.edu.vnua.qlsvfita.request.admin.student;

import lombok.Data;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.model.entity.Major;
import vn.edu.vnua.qlsvfita.request.admin.classes.CreateClassRequest;
import vn.edu.vnua.qlsvfita.request.admin.course.CreateCourseRequest;
import vn.edu.vnua.qlsvfita.request.admin.major.CreateMajorRequest;
import vn.edu.vnua.qlsvfita.validator.validation.InputDate;

import javax.validation.constraints.*;

@Data
public class UpdateStudentRequest {
    private String id;

    @NotBlank(message = "Tên sinh viên không được để trống")
    @Size(max = 200, message = "Tên sinh viên chỉ được tối đa 200 ký tự")
    private String name;

    private CreateCourseRequest course;

    private CreateMajorRequest major;

    private CreateClassRequest classes;

    @InputDate(message = "Ngày sinh phải đúng định dạng dd/MM/yyyy")
    @Size(max = 10, message = "Ngày sinh phải đúng định dạng dd/MM/yyyy")
    private String dob;

    @NotBlank(message = "Giới tính không được để trống")
    private String gender;

    @NotBlank(message = "Trạng thái không được để trống")
    private String status;

    @Size(min = 8, max = 10, message = "Thời gian phải đúng định dạng dd/MM/yyyy")
    private String statusDate;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$", message = "Số điện thoại không đúng định dạng")
    private String phoneNumber;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(min = 5, max = 200, message = "Email chỉ được từ 5-200 ký tự")
    private String email;

    @Size(max = 200,message = "Quê quán không được quá 200 ký tự")
    private String homeTown;

    @Size(max = 200,message = "Nơi ở hiện tại không được quá 200 ký tự")
    private String residence;

    @Size(max = 200,message = "Tên bố không được quá 200 ký tự")
    private String fatherName;

    @Pattern(regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$", message = "Số điện thoại không đúng định dạng")
    private String fatherPhoneNumber;

    @Size(max = 200,message = "Tên mẹ không được quá 200 ký tự")
    private String motherName;

    @Pattern(regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$", message = "Số điện thoại không đúng định dạng")
    private String motherPhoneNumber;

    @Size(max = 32, message = "Mật khẩu chỉ được tối đa 32 ký tự")
    private String password;

    private String warning;
}
