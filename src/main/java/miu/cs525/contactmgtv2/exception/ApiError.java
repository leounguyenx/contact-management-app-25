package miu.cs525.contactmgtv2.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Getter
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private HttpStatus error;
    private String message;
    private String path;
}
