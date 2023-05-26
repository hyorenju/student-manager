package vn.edu.vnua.qlsvfita.model.dto;

import lombok.Data;
import vn.edu.vnua.qlsvfita.model.dto.classic.ClassDTO;
import vn.edu.vnua.qlsvfita.model.dto.classic.CourseDTO;
import vn.edu.vnua.qlsvfita.model.dto.classic.MajorDTO;

import java.sql.Timestamp;

@Data
public class StudentListDTO {
    private String id;

    private String name;

    private CourseDTO course;

    private MajorDTO major;

    private ClassDTO classes;

    private String dob;

    private String gender;

    private String phoneNumber;

    private String email;

    private String homeTown;

    private String residence;

    private String fatherName;

    private String fatherPhoneNumber;

    private String motherName;

    private String motherPhoneNumber;

    private String status;

    private String statusDate;

    private String warning;

}
