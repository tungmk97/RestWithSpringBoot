package mk.training.controller;

import com.google.common.net.HttpHeaders;
import io.swagger.annotations.Api;
import mk.training.data.vo.UploadFileResponseVO;
import mk.training.service.FileStogareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "FileEndpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

  @Autowired
  private FileStogareService fileStogareService;

  @PostMapping("uploadFile")
  public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile multipartFile) {
    String fileName = fileStogareService.storeFile(multipartFile);
    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/file/v1/downloadFile")
            .path(fileName)
            .toUriString();

    return new UploadFileResponseVO(fileName, fileDownloadUri, multipartFile.getContentType(), multipartFile.getSize());
  }

  @PostMapping("/uploadMultipleFile")
  public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    return Arrays.asList(files)
            .stream().map(file -> uploadFile(file))
            .collect(Collectors.toList());
  }

  @GetMapping("/downloadFile/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
    Resource resource = fileStogareService.loadFileAsResource(fileName);
    String contentType = null;
    try {
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (Exception e) {
      LOGGER.info("Could not determine file type");
    }

    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=/" + resource.getFilename() + "/")
            .body(resource);
  }
}