package org.example;

import org.example.file.reading.FileExcelManager;
import org.example.file.FileManagerFactory;
import org.example.model.StudentManager;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("\nCHƯƠNG TRÌNH DANH SÁCH SINH VIÊN.\n");
//        String pathFile = "D:\\Boku no\\Studying\\Coding\\Java\\StudentExample1\\src\\main\\java\\org\\example\\file\\student.txt";
//        FileManagerFactory fileManagerFactory = new FileManagerFactory(new FileTextManager());
        String pathFile = "D:\\Boku no\\Studying\\Coding\\Java\\StudentExample1\\src\\main\\java\\org\\example\\file\\student.xlsx";


        FileManagerFactory fileManagerFactory = new FileManagerFactory(new FileExcelManager());
        Stream<String> stream = fileManagerFactory.getIfileManager().readFile(pathFile);

//        IFileManager iFileManager = new FileExcelManager();
//        Stream<String> stream = iFileManager.readFile(pathFile);


        StudentManager studentManager = new StudentManager();

//        stream.forEach(studentManager::readDataFromFile);
        AtomicInteger count = new AtomicInteger();
        stream.forEach(studentStr -> {
            count.getAndIncrement();
            studentManager.readDataFromFile(studentStr, count);
        });

        studentManager.showMenu();
    }
}