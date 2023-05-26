package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.repository.StudentRepository;
import vn.edu.vnua.qlsvfita.service.admin.StudentService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("admin/export")
@RequiredArgsConstructor
public class ExcelController {
    private static final String[] HEADERS = { "Id", "Name", "Email", "Gender" };
    private static final String FILE_NAME = "students.xlsx";
    private final StudentService studentService;
    private final StudentRepository studentRepository;


//    @GetMapping("/excel")
//    public ResponseEntity<ByteArrayResource> download() throws IOException {
//        List<Student> students = studentRepository.findAll();
//        ByteArrayInputStream in = studentService.writeToExcel(students);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FILE_NAME);
//        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel");
//        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
//        headers.add(HttpHeaders.PRAGMA, "no-cache");
//        headers.add(HttpHeaders.EXPIRES, "0");
//
//        return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(in.readAllBytes()));
//    }

//    private ByteArrayInputStream write(List<Student> students) throws IOException {
//        try(Workbook workbook = new XSSFWorkbook()) {
//            Sheet sheet = workbook.createSheet("Students");
//
//            // Create headers
//            int rowNum = 0;
//            Row headerRow = sheet.createRow(rowNum++);
//            for (int i = 0; i < HEADERS.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(HEADERS[i]);
//            }
//
//            // Create data rows
//            for (Student student : students) {
//                Row row = sheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(student.getId());
//                row.createCell(1).setCellValue(student.getName());
//                row.createCell(2).setCellValue(student.getEmail());
//                row.createCell(3).setCellValue(student.getGender());
//            }
//
//            // Write the workbook to a byte array
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            workbook.write(out);
//            return new ByteArrayInputStream(out.toByteArray());
//        }
//    }
}

