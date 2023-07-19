package vn.edu.vnua.qlsvfita.request.admin.student;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import vn.edu.vnua.qlsvfita.request.GetPageBaseRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class GetStudentListRequest extends GetPageBaseRequest {
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
