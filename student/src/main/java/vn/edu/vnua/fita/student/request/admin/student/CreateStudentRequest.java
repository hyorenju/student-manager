package vn.edu.vnua.fita.student.request.admin.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import vn.edu.vnua.fita.student.domain.validator.InputDate;
import vn.edu.vnua.fita.student.request.admin.classes.CreateClassRequest;
import vn.edu.vnua.fita.student.request.admin.course.CreateCourseRequest;
import vn.edu.vnua.fita.student.request.admin.major.CreateMajorRequest;


@Data
public class CreateStudentRequest {
    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(max = 100, message = "Mã sinh viên quá dài")
    @Pattern(regexp = "^[0-9]+", message = "Mã sinh viên phải là một chuỗi ký tự số")
    private String id;

    @NotBlank(message = "Họ đệm không được để trống")
    @Size(max = 200, message = "Họ đệm quá dài")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Họ đệm không được chứa ký tự đặc biệt")
    private String surname;

    @NotBlank(message = "Tên sinh viên không được để trống")
    @Size(max = 200, message = "Tên sinh viên quá dài")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Tên sinh viên không được chứa ký tự đặc biệt")
    private String lastName;

    private CreateCourseRequest course;

    private CreateMajorRequest major;

    private CreateClassRequest classes;

    @NotBlank(message = "Ngày sinh không được để trống")
    @InputDate(message = "Ngày sinh phải đúng định dạng dd/MM/yyyy")
    @Size(min = 8, max = 10, message = "Ngày sinh chỉ được 8-10 ký tự")
    private String dob;

    @NotBlank(message = "Giới tính không được để trống")
    private String gender;

    @Pattern(regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$", message = "Số điện thoại không đúng định dạng")
    private String phoneNumber;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(min = 5, max = 200, message = "Email chỉ được từ 5-200 ký tự")
    private String email;

    @NotBlank(message = "Quê quán không được để trống")
    @Size(max = 200,message = "Quê quán không được quá dài")
    private String homeTown;

    @Size(max = 200,message = "Nơi ở hiện tại không được quá dài")
    private String residence;

    @Size(max = 200,message = "Tên bố không được quá dài")
    private String fatherName;

    private String fatherPhoneNumber;

    @Size(max = 200, message = "Tên mẹ không được quá dài")
    private String motherName;

    private String motherPhoneNumber;

    private String password;
}
