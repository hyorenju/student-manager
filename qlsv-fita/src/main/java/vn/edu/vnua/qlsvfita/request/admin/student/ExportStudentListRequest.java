package vn.edu.vnua.qlsvfita.request.admin.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import vn.edu.vnua.qlsvfita.request.GetPageBaseRequest;
@Data
@AllArgsConstructor
public class ExportStudentListRequest{
    private String studentId;
    private FilterCondition filter;

    @Data
    @RequiredArgsConstructor
    public static class FilterCondition {
        private String courseId;
        private String majorId;
        private String classId;
        private String status;
        private Boolean warning;
        private SortStudentListRequest sort;
    }

    @Data
    public static class SortStudentListRequest {
        private boolean name;
    }
}
