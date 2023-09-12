package vn.edu.vnua.fita.student.service.iservice;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.service.admin.file.ExcelData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IExcelService {
    void importStudentFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException;
}
