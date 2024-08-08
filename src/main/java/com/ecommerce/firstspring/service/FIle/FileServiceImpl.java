package com.ecommerce.firstspring.service.FIle;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
  @Override
  public String uploadFile(MultipartFile file, String path) throws IOException {
    String originalFileName = file.getOriginalFilename();
    String randomId = UUID.randomUUID().toString();
    assert originalFileName != null;
    String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
    String filePath = path + File.separator + fileName;
    File folder = new File(path);
    if (!folder.exists()) {
      folder.mkdir();
    }
    // Upload to server folder
    Files.copy(file.getInputStream(), Paths.get(filePath));
    return fileName;
  }
}
