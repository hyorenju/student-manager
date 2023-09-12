package vn.edu.vnua.fita.student.service.admin.file;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.FileNameConstant;
import vn.edu.vnua.fita.student.common.ThreadsNumConstant;
import vn.edu.vnua.fita.student.entity.table.Student;
import vn.edu.vnua.fita.student.repository.jparepo.ClassRepository;
import vn.edu.vnua.fita.student.repository.jparepo.CourseRepository;
import vn.edu.vnua.fita.student.repository.jparepo.MajorRepository;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.service.iservice.IExcelService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class ExcelService implements IExcelService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final MajorRepository majorRepository;
    private final String errorStudentHadFound = "Có ít nhất 1 sinh viên sai. Kiểm tra file %s";
    private final String dataNotFound = "Không tìm thấy dữ liệu. Hãy chắc chắn rằng file excel được nhập từ ô A1";
    private final List<ExcelData> excelDataList = new CopyOnWriteArrayList<>();


    @Override
    public void importStudentFromExcel(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        List<String> studentStrList = readStudentList(file);
        storeData(studentStrList);
        if (!isContinue()) {
            exportErrorList(excelDataList);
            throw new RuntimeException(String.format(errorStudentHadFound, FileNameConstant.ERROR_STUDENT_FILE));
        }
    }

    public List<String> readStudentList(MultipartFile file) throws IOException, ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(ThreadsNumConstant.MAX_THREADS);
        List<String> stringList = new CopyOnWriteArrayList<>();

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        int totalRows = sheet.getPhysicalNumberOfRows();

        for (int i = 0; i < totalRows; i++) {
            Row row = sheet.getRow(i);
            if (i != 0) {
                Callable<String> callable = new ReadExcelWorker(row);
                Future<String> future = executor.submit(callable);
                stringList.add(future.get());
            } else if (row == null) {
                throw new RuntimeException(dataNotFound);
            }
        }

        workbook.close();
        return stringList;
    }

    private void storeData(List<String> studentStrList) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(ThreadsNumConstant.MAX_THREADS);
        List<ExcelData.ErrorDetail> errorDetails = new CopyOnWriteArrayList<>();

        for (int i = 0; i < studentStrList.size(); i++) {
            String studentStr = studentStrList.get(i);
            Callable<ExcelData> callable = new ExcelReaderWorker(studentRepository, courseRepository, classRepository, majorRepository, studentStr, i, errorDetails);
            Future<ExcelData> future = executor.submit(callable);
            excelDataList.add(future.get());
        }

        excelDataList.forEach(excelData -> studentRepository.saveAndFlush(excelData.getStudent()));
//        executor.close();
    }

    private boolean isContinue() {
        for (ExcelData excelData :
                excelDataList) {
            if (!excelData.isValid()) {
                return false;
            }
        }
        return true;
    }

    public void exportErrorList(List<ExcelData> excelDataList) {

    }
}
