package vn.edu.vnua.fita.student.service.admin.file;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.service.iservice.IFirebaseService;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FirebaseService implements IFirebaseService {
    @Override
    public Blob uploadImage(MultipartFile file, String bucketName) throws IOException {
        // Xác thực google firebase
        InputStream serviceAccountKey = getClass().getResourceAsStream("/serviceAccountKey.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountKey);

        // Nhận storage service
        StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
        Storage storage = storageOptions.getService();

        // Tạo một tên file random
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID() + getFileExtension(originalFilename);

        //Tải file lên Firebase
        return storage.create(BlobInfo.newBuilder(bucketName, filename)
                .setContentType(file.getContentType())
                .build(), file.getInputStream());
    }

    @Override
    public Blob uploadFileExcel(MultipartFile file, String bucketName) throws IOException {
        // Xác thực google firebase
        InputStream serviceAccountKey = getClass().getResourceAsStream("/serviceAccountKey.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountKey);

        // Nhận storage service
        StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
        Storage storage = storageOptions.getService();

        // Tạo một tên file random
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID() + getFileExtension(originalFilename);

        //Tải file lên Firebase
        return storage.create(BlobInfo.newBuilder(bucketName, filename)
                .setContentType(file.getContentType())
                .build(), file.getInputStream());
    }

    public String getFileExtension(String filename) {
        if (filename != null && filename.lastIndexOf(".") != -1) {
            return filename.substring(filename.lastIndexOf("."));
        }
        return "";
    }
}
