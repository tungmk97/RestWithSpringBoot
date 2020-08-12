package mk.training.service;

import mk.training.config.FileStorageConfig;
import mk.training.exception.FileNotFoundException;
import mk.training.exception.FileStorageException;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStogareService {
  private final Path fileStogareLocaltion;

  @Autowired
  public FileStogareService(FileStorageConfig fileStorageConfig) {
    this.fileStogareLocaltion = Paths.get(fileStorageConfig.getUploadDir())
      .toAbsolutePath().normalize();

    try {
      Files.createDirectories(this.fileStogareLocaltion);
    } catch (Exception e) {
      throw new FileStorageException("Could not create directory", e);
    }
  }

  public String storeFile(MultipartFile file) {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    try {
      if(fileName.contains("..")) {
        throw new FileStorageException("Invalid fileName " + fileName);
      }
      Path targetLocation = this.fileStogareLocaltion.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      return fileName;
    } catch (Exception e) {
      throw new FileStorageException("Cannot storage this file " + fileName);
    }

  }

  public Resource loadFileAsResource(String fileName) {
    try {
      Path filePath = this.fileStogareLocaltion.resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if(resource.exists()) {
        return resource;
      } else {
        throw new FileNotFoundException("File not found " + fileName);
      }
    } catch (Exception e) {
      throw  new FileNotFoundException("File not found " + fileName, e);
    }
  }

}
