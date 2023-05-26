package vn.edu.vnua.qlsvfita.request.admin.student;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.model.entity.Major;
import vn.edu.vnua.qlsvfita.model.entity.Permission;
import vn.edu.vnua.qlsvfita.request.admin.classes.CreateClassRequest;
import vn.edu.vnua.qlsvfita.request.admin.course.CreateCourseRequest;
import vn.edu.vnua.qlsvfita.request.admin.major.CreateMajorRequest;
import vn.edu.vnua.qlsvfita.validator.validation.InputDate;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;
import java.sql.Timestamp;

@Data
public class CreateStudentRequest {
    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(max = 10, message = "Mã sinh viên chỉ được tối đa 10 ký tự")
    @Pattern(regexp = "^[0-9]+", message = "Mã sinh viên phải là một chuỗi ký tự số")
    private String id;

    @NotBlank(message = "Tên sinh viên không được để trống")
    @Size(max = 200, message = "Tên sinh viên chỉ được tối đa 200 ký tự")
    private String name;

    private CreateCourseRequest course;

    private CreateMajorRequest major;

    private CreateClassRequest classes;

    @NotBlank(message = "Ngày sinh không được để trống")
    @InputDate(message = "Ngày sinh phải đúng định dạng dd/MM/yyyy")
    @Size(min = 10, max = 10, message = "Ngày sinh phải đúng định dạng dd/MM/yyyy")
    private String dob;

    @NotBlank(message = "Giới tính không được để trống")
    private String gender;

    @NotBlank(message = "Trạng thái không được để trống")
    private String status;

    @Size(min = 8, max = 10, message = "Thời gian phải đúng định dạng dd/MM/yyyy")
    private String statusDate;

    @Pattern(regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$", message = "Số điện thoại không đúng định dạng")
    private String phoneNumber;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(min = 5, max = 200, message = "Email chỉ được từ 5-200 ký tự")
    private String email;

    @NotBlank(message = "Quê quán không được để trống")
    @Size(max = 200,message = "Quê quán không được quá 200 ký tự")
    private String homeTown;

    @Size(max = 200,message = "Nơi ở hiện tại không được quá 200 ký tự")
    private String residence;

    @Size(max = 200,message = "Tên bố không được quá 200 ký tự")
    private String fatherName;

//    @Pattern(regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$", message = "Số điện thoại không đúng định dạng")
    private String fatherPhoneNumber;

    @Size(max = 200,message = "Tên mẹ không được quá 200 ký tự")
    private String motherName;

    private String motherPhoneNumber;

    @Size(max = 32, message = "Mật khẩu chỉ được tối đa 32 ký tự")
    private String password;

    @Size(max = 200, message = "Quá nhiều ký tự")
    private String warning;

}
