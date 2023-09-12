package vn.edu.vnua.fita.student.request.admin.classes;

import lombok.Data;
import vn.edu.vnua.fita.student.request.GetPageBaseRequest;


@Data
public class GetClassListRequest extends GetPageBaseRequest {
    private String id;
}
