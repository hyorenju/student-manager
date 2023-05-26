package vn.edu.vnua.qlsvfita.request.admin.point;

import lombok.Data;

import javax.validation.constraints.*;
@Data
public class UpdatePointRequest {
    @NotBlank(message = "Mã sinh viên không được để trống")
    @Size(max = 10, message = "Mã sinh viên chỉ được tối đa 10 ký tự")
    private String studentId;

    @NotBlank(message = "Mã học kỳ không được để trống")
    @Size(min = 5, max = 5, message = "Mã học kỳ phải đủ 5 ký tự")
    private String termId;
    @NotNull(message = "Điểm trung bình hệ 10 không được để trống")
    @Min(value = 0, message = "Điểm trung bình hệ 10 không được dưới 0")
    @Max(value = 10, message = "Điểm trung bình hệ 10 không được quá 10")
    private Float medScore10;

    @NotNull(message = "Điểm trung bình hệ 4 không được để trống")
    @Min(value = 0, message = "Điểm trung bình hệ 4 không được dưới 0")
    @Max(value = 4, message = "Điểm trung bình hệ 4 không được quá 4")
    private Float medScore4;

    @NotNull(message = "Điểm rèn luyện không được để trống")
    @Min(value = 0, message = "Điểm rèn luyện không được dưới 0")
    @Max(value = 100, message = "Điểm rèn luyện không được quá 100")
    private Integer trainingPoint;

    @NotNull(message = "Tín chỉ tích lũy không được để trống")
    @Min(value = 0, message = "Tín chỉ tích lũy không được dưới 0")
    private Integer creditsAccumulated;

    @NotNull(message = "Điểm trung bình tích lũy hệ 10 không được để trống")
    @Min(value = 0, message = "Điểm trung bình tích lũy hệ 10 không được dưới 0")
    @Max(value = 10, message = "Điểm trung bình tích lũy hệ 10 không được quá 10")
    private Float scoreAccumulated10;

    @NotNull(message = "Điểm trung bình tích lũy hệ 4 không được để trống")
    @Min(value = 0, message = "Điểm trung bình tích lũy hệ 4 không được dưới 0")
    @Max(value = 4, message = "Điểm trung bình tích lũy hệ 4 không được quá 4")
    private Float scoreAccumulated4;
}
