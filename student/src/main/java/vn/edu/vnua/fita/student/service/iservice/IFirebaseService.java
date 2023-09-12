package vn.edu.vnua.fita.student.service.iservice;

import com.google.cloud.storage.Blob;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFirebaseService {
    Blob uploadImage(MultipartFile file, String bucketName) throws IOException;
    Blob uploadFileExcel(MultipartFile file, String bucketName) throws IOException;
}
