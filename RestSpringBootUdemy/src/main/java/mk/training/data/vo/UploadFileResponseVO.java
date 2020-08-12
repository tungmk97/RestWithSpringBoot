package mk.training.data.vo;

import java.util.Objects;

public class UploadFileResponseVO {

  private String fileName;
  private String fileType;
  private String fileDownloadUri;
  private long fileSize;

  public UploadFileResponseVO(String fileName, String fileType, String fileDownloadUri, long fileSize) {
    this.fileName = fileName;
    this.fileType = fileType;
    this.fileDownloadUri = fileDownloadUri;
    this.fileSize = fileSize;
  }


  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getFileDownloadUri() {
    return fileDownloadUri;
  }

  public void setFileDownloadUri(String fileDownloadUri) {
    this.fileDownloadUri = fileDownloadUri;
  }

  public long getFileSize() {
    return fileSize;
  }

  public void setFileSize(long fileSize) {
    this.fileSize = fileSize;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UploadFileResponseVO that = (UploadFileResponseVO) o;
    return fileSize == that.fileSize &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(fileType, that.fileType) &&
            Objects.equals(fileDownloadUri, that.fileDownloadUri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileName, fileType, fileDownloadUri, fileSize);
  }
}
