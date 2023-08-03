package org.example.file.reading;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@Log4j2
public class FileExcelManager implements IFileManager {
    @Override
    public Stream<String> readFile(String pathFile) throws IOException {
        try {
            FileInputStream file = new FileInputStream(new File(pathFile));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            // Tìm vị trí hàng đầu tiên chứa dữ liệu
            Iterator<Row> rowIterator = sheet.iterator();
            int startRow = findStartRow(rowIterator);

            if (startRow == -1) {
                // Không tìm thấy vị trí hợp lệ để bắt đầu đọc dữ liệu
                workbook.close();
                file.close();
                return Stream.empty();
            }

            List<String> rowData = new ArrayList<>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() < startRow) {
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                Iterator<Cell> cellIterator = row.iterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cell.setCellType(CellType.STRING); // Xác định kiểu dữ liệu là STRING
                    stringBuilder.append(cell.getStringCellValue());
                    if (cellIterator.hasNext()) {
                        stringBuilder.append(",");
                    }
                }
                rowData.add(stringBuilder.toString());
            }

            workbook.close();
            file.close();

            return rowData.stream();
        } catch (IOException e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    private static int findStartRow(Iterator<Row> rowIterator) {
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().trim().length() > 0) {
                    // Tìm thấy vị trí hàng đầu tiên chứa dữ liệu
                    return row.getRowNum();
                }
            }
        }
        return -1;
    }
}
