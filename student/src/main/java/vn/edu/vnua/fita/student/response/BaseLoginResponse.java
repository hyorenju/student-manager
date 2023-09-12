package vn.edu.vnua.fita.student.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import vn.edu.vnua.fita.student.common.DateTimeConstant;
import vn.edu.vnua.fita.student.entity.dto.basic.ClassBasicDTO;
import vn.edu.vnua.fita.student.entity.dto.basic.CourseBasicDTO;
import vn.edu.vnua.fita.student.entity.dto.basic.MajorBasicDTO;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class BaseLoginResponse {
    private String jwt;

    private String id;

    private String name;

    private String avatar;

    private CourseBasicDTO course;

    private MajorBasicDTO major;

    private ClassBasicDTO classes;

    @JsonFormat(pattern = DateTimeConstant.DATE_FORMAT)
    private Timestamp dob;

    private String gender;

    private String phoneNumber;

    private String email;

    private String homeTown;

    private String residence;

    private String fatherName;

    private String fatherPhoneNumber;

    private String motherName;

    private String motherPhoneNumber;

    private String roleId;
}
