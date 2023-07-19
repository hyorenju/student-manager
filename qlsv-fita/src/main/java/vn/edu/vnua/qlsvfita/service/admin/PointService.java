package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.qlsvfita.model.dto.StudentTermDTO;
import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;
import vn.edu.vnua.qlsvfita.request.admin.point.*;

import java.io.IOException;
import java.util.List;

public interface PointService {

    Page<StudentTerm> filterPointList(GetPointListRequest request);

    StudentTermDTO createPoint(CreatePointRequest request);

    StudentTermDTO updatePoint(UpdatePointRequest request);

    StudentTermDTO deletePoint(DeletePointRequest request);

    List<StudentTerm> exportPointList(ExportPointListRequest request);

    void importStudyPoint(MultipartFile file) throws IOException;
}
