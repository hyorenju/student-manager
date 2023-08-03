package org.example.file;

import lombok.Getter;
import lombok.Setter;
import org.example.file.reading.IFileManager;
import org.example.file.writing.OFileManager;

@Getter
@Setter
public class FileManagerFactory {
    private IFileManager ifileManager;
    private OFileManager oFileManager;

    public FileManagerFactory(IFileManager ifileManager) {
        this.ifileManager = ifileManager;
    }

    public FileManagerFactory(OFileManager oFileManager) {
        this.oFileManager = oFileManager;
    }
}
