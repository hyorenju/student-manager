package vn.edu.vnua.qlsvfita.request.admin.point;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
public class ExportPointListRequest {
    private String studentId;
    private String termId;
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
