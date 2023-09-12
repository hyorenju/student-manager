package vn.edu.vnua.fita.student.service.admin.file;

import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;
import java.util.concurrent.Callable;

@Data
public class ReadExcelWorker implements Callable<String> {
    private Row row;

    public ReadExcelWorker(Row row) {
        this.row = row;
    }

    @Override
    public String call() {
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
        return stringBuilder.toString();
    }
}
