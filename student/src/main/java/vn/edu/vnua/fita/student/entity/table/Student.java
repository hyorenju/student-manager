package vn.edu.vnua.fita.student.entity.table;

import lombok.*;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.domain.validator.StudentValidator;
import vn.edu.vnua.fita.student.entity.authorization.Role;
import vn.edu.vnua.fita.student.service.admin.file.ExcelData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student {
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(length = 200)
    private String surname;

    @Column(name = "last_name", length = 200)
    private String lastName;

    @Column(name = "avatar", length = 1000)
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class classes;

    @Column
    private Timestamp dob;

    @Column(length = 200)
    private String gender;

    @Column(name = "phone_number", length = 200)
    private String phoneNumber;

    @Column(length = 200)
    private String email;

    @Column(name = "home_town", length = 200)
    private String homeTown;

    @Column(length = 200)
    private String residence;

    @Column(name = "father_name", length = 200)
    private String fatherName;

    @Column(name = "father_phone_number", length = 200)
    private String fatherPhoneNumber;

    @Column(name = "mother_name", length = 200)
    private String motherName;

    @Column(name = "mother_phone_number", length = 200)
    private String motherPhoneNumber;

    @Column(length = 200)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role = Role.builder().id(RoleConstant.STUDENT).build();

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "student")
    private Collection<Document> documents;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "students_terms",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id")
    )
    private Collection<Term> terms;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "students_statuses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id")
    )
    private Collection<Status> statuses;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role.getId()));
            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getId())));
        }
        return authorities;
    }

    public List<ExcelData.ErrorDetail> validateInformationDetailError(List<ExcelData.ErrorDetail> errorDetailList){
        if (!StudentValidator.validateId(id)) {
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã không hợp lệ").build());
        }
        if (!StudentValidator.validateName(surname)) {
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(1).errorMsg("Họ đệm không hợp lệ").build());
        }
        if (!StudentValidator.validateName(lastName)) {
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(2).errorMsg("Tên hợp lệ").build());
        }
        if (!StudentValidator.validateCourse(course.getId())) {
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(3).errorMsg("Khoá không hợp lệ").build());
        }
        if (!StudentValidator.validateMajor(major.getId())) {
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(4).errorMsg("Ngành không hợp lệ").build());
        }
        if (!StudentValidator.validateClass(classes.getId())) {
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(5).errorMsg("Lớp không hợp lệ").build());
        }
        if (!StudentValidator.validateDob(dob)) {
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(6).errorMsg("Dạng dd/MM/yyyy").build());
        }
        if (!StudentValidator.validateGender(gender)) {
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(7).errorMsg("Giới tính không hợp lệ").build());
        }
        if (!StudentValidator.validatePhoneNumber(phoneNumber)) {
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(8).errorMsg("Sđt không hợp lệ").build());
        }
        if (!StudentValidator.validateEmail(email)) {
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(9).errorMsg("Email không hợp lệ").build());
        }
        if (!StudentValidator.validateHomeTown(homeTown)) {
            errorDetailList.add(ExcelData.ErrorDetail.builder().columnIndex(10).errorMsg("Quê quán không hợp lệ").build());
        }

        return errorDetailList;
    }




}
