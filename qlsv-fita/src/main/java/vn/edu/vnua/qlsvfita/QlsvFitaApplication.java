package vn.edu.vnua.qlsvfita;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.edu.vnua.qlsvfita.service.admin.FilesStorageService;

import javax.annotation.Resource;

@SpringBootApplication
public class QlsvFitaApplication implements CommandLineRunner {
    @Resource
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(QlsvFitaApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
//    storageService.deleteAll();
        storageService.init();
    }
}
