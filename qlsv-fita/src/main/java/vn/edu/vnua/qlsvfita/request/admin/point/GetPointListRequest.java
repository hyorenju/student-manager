package vn.edu.vnua.qlsvfita.request.admin.point;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import vn.edu.vnua.qlsvfita.request.admin.student.GetStudentListRequest;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Data
public class GetPointListRequest {
    private String studentId;
    private String termId;

    @NotNull(message = "Số trang không được để trống")
    private Integer page;

    @NotNull(message = "Số bản ghi mỗi trang không được để trống")
    private Integer size;

    private FilterCondition filter;

    @Data
    @RequiredArgsConstructor
    public static class FilterCondition {
        private int point;
        private int accPoint;
        private int trainingPoint;
    }

//    @Data
//    @RequiredArgsConstructor
//    public static class ValueMedScore4{
//        private Float min;
//        private Float max;
//    }
//
//    @Data
//    @RequiredArgsConstructor
//    public static class ValueAccScore4{
//        private Float min;
//        private Float max;
//    }
//
//    @Data
//    @RequiredArgsConstructor
//    public static class ValueTrainingScore{
//        private Integer min;
//        private Integer max;
//    }

}
