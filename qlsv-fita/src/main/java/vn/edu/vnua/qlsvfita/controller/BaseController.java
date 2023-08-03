package vn.edu.vnua.qlsvfita.controller;

import org.springframework.http.ResponseEntity;
import vn.edu.vnua.qlsvfita.constant.ErrorCodeDefs;
import vn.edu.vnua.qlsvfita.response.BaseItemResponse;
import vn.edu.vnua.qlsvfita.response.BaseListItemResponse;
import vn.edu.vnua.qlsvfita.response.BasePageItemResponse;
import vn.edu.vnua.qlsvfita.response.BaseResponse;


import java.util.List;

public class BaseController {

    protected <T> ResponseEntity<?> buildItemResponse(T data) {
        BaseItemResponse<T> response = new BaseItemResponse<>();
        response.setData(data);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<?> buildListItemResponse(List<T> data, long total) {
        BaseListItemResponse<T> response = new BaseListItemResponse<>();
        response.setResult(data, total);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    protected <T> ResponseEntity<?> buildPageItemResponse(long page, long limit, long totalPage, List<T> items){
        BasePageItemResponse<T> response = new BasePageItemResponse<>();
        response.setPageItems(page, limit, totalPage, items);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
}
