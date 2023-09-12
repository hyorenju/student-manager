package vn.edu.vnua.fita.student.common;

import java.time.LocalDateTime;

public class FileNameConstant {
    public static final String STUDENT_FILE = "Danh_sach_sinh_vien " + LocalDateTime.now() + ".xlsx";
    public static final String ERROR_STUDENT_FILE = "Danh_sach_sinh_vien_sai " + LocalDateTime.now() + ".xlsx";
}
