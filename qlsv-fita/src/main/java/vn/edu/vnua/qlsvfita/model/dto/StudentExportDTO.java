package vn.edu.vnua.qlsvfita.model.dto;

import lombok.Data;
import vn.edu.vnua.qlsvfita.model.dto.classic.ClassDTO;
import vn.edu.vnua.qlsvfita.model.dto.classic.CourseDTO;
import vn.edu.vnua.qlsvfita.model.dto.classic.MajorDTO;

@Data
public class StudentExportDTO {

    private String id;

    private String name;

    private String courseId;

    private String majorId;

    private String classId;

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
}
