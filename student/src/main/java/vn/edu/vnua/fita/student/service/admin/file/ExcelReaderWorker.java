package vn.edu.vnua.fita.student.service.admin.file;

import java.util.List;
import java.util.concurrent.Callable;

import vn.edu.vnua.fita.student.common.RoleConstant;
import vn.edu.vnua.fita.student.entity.authorization.Role;
import vn.edu.vnua.fita.student.entity.table.Class;
import vn.edu.vnua.fita.student.entity.table.Course;
import vn.edu.vnua.fita.student.entity.table.Major;
import vn.edu.vnua.fita.student.entity.table.Student;
import vn.edu.vnua.fita.student.repository.jparepo.ClassRepository;
import vn.edu.vnua.fita.student.repository.jparepo.CourseRepository;
import vn.edu.vnua.fita.student.repository.jparepo.MajorRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.util.MyUtils;

public class ExcelReaderWorker implements Callable<ExcelData> {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final MajorRepository majorRepository;
    private final String studentStr;
    private final int row;
    private final List<ExcelData.ErrorDetail> errorDetails;

    public ExcelReaderWorker(StudentRepository studentRepository, CourseRepository courseRepository, ClassRepository classRepository, MajorRepository majorRepository, String studentStr, int row, List<ExcelData.ErrorDetail> errorDetails) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.classRepository = classRepository;
        this.majorRepository = majorRepository;
        this.studentStr = studentStr;
        this.row = row;
        this.errorDetails = errorDetails;
    }

    @Override
    public ExcelData call() {
        ExcelData excelData = new ExcelData();

        if (!studentStr.isEmpty()) {
            String[] infoList = studentStr.strip().split(",");
            String id = infoList[0].strip();
            String surname = infoList[1].strip();
            String lastName = infoList[2].strip();
            Long courseId = parse;
            String majorId = infoList[4].strip();
            String classId = infoList[5].strip();
            String dob = infoList[6].strip();
            String gender = infoList[7].strip();
            String phoneNumber = infoList[8].strip();
            String email = infoList[9].strip();
            String homeTown = infoList[10].strip();

            Student student = Student.builder()
                    .id(id)
                    .surname(surname)
                    .lastName(lastName)
                    .course(Course.builder().id(courseId).build())
                    .major(Major.builder().id(majorId).build())
                    .classes(Class.builder().id(classId).build())
                    .dob(MyUtils.convertTimestampFromString(dob))
                    .gender(gender)
                    .phoneNumber(phoneNumber)
                    .email(email)
                    .homeTown(homeTown)
                    .isDeleted(false)
                    .role(Role.builder().id(RoleConstant.STUDENT).build())
                    .build();

            if (infoList.length != 11) {
                errorDetails.add(ExcelData.ErrorDetail.builder().columnIndex(11).errorMsg("Định dạng sinh viên không hợp lệ").build());
            }

            List<ExcelData.ErrorDetail> errorDetailList = student.validateInformationDetailError(errorDetails);
            if (studentRepository.existsById(id)) {
                errorDetails.add(ExcelData.ErrorDetail.builder().columnIndex(0).errorMsg("Mã đã tồn tại").build());
            }
            if (courseRepository.existsById(courseId)) {
                errorDetails.add(ExcelData.ErrorDetail.builder().columnIndex(3).errorMsg("Khoá đã tồn tại").build());
            }
            if (classRepository.existsById(classId)) {
                errorDetails.add(ExcelData.ErrorDetail.builder().columnIndex(5).errorMsg("Lớp đã tồn tại").build());
            }
            if (majorRepository.existsById(majorId)){
                errorDetails.add(ExcelData.ErrorDetail.builder().columnIndex(4).errorMsg("Ngành đã tồn tại").build());
            }

            excelData.setStudent(student);
            if (!errorDetailList.isEmpty()) {
                excelData.setErrorDetailList(errorDetailList);
                excelData.setValid(false);
            }
            excelData.setRowIndex(row);
        }

        return excelData;
    }
}
