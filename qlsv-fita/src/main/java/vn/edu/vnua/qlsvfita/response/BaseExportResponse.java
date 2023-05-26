package vn.edu.vnua.qlsvfita.response;

import lombok.Data;

@Data
public class BaseExportResponse<T> extends BaseResponse {
    private T data;

}
