package com.ecommerce.firstspring.service.FIle;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
  String uploadFile(MultipartFile file, String path) throws IOException;
}
