package mk.training.exception;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {

  private Date datetime;
  private String message;
  private String details;

  public ExceptionResponse(Date datetime, String message, String details) {
    this.datetime = datetime;
    this.message = message;
    this.details = details;
  }

  public Date getDatetime() {
    return datetime;
  }

  public String getMessage() {
    return message;
  }

  public String getDetails() {
    return details;
  }
}
