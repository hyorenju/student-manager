package vn.edu.vnua.qlsvfita.fortesting;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("services")
@RequiredArgsConstructor
public class FirebaseController {
    private final Logger logger = LoggerFactory.getLogger(FirebaseController.class);
    private final FirebaseService fileService;

    @PostMapping("/profile/pic")
    public Object upload(MultipartFile multipartFile) {
        logger.info("HIT -/upload | File Name : {}", multipartFile.getOriginalFilename());
        return fileService.upload(multipartFile);
    }

    @PostMapping("/profile/pic/{fileName}")
    public Object download(@PathVariable String fileName) throws IOException {
        logger.info("HIT -/download | File Name : {}", fileName);
        return fileService.download(fileName);
    }
}
