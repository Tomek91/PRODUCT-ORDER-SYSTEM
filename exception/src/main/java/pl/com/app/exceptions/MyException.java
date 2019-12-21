package pl.com.app.exceptions;

import java.time.LocalDateTime;

public class MyException extends RuntimeException {
    private String exceptionMessage;
    private LocalDateTime exceptionDateTime;

    public MyException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        this.exceptionDateTime = LocalDateTime.now();
    }

    @Override
    public String getMessage() {
        return String.join(" ", "[EXCEPTION]:", exceptionMessage, exceptionDateTime.toString());
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public LocalDateTime getExceptionDateTime() {
        return exceptionDateTime;
    }
}
