package miu.cs525.contactmgtv2.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiError> handleAccessDeniedException(
//            AccessDeniedException ex,
//            HttpServletRequest request
//    ) {
//        ApiError error = new ApiError(
//                LocalDateTime.now(),
//                HttpStatus.FORBIDDEN.value(),
//                HttpStatus.FORBIDDEN,
//                ex.getMessage(),
//                request.getRequestURI()
//        );
//        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
//    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleGenericException(HttpServletRequest request, Exception ex) {
//        ApiError error = new ApiError(
//                LocalDateTime.now(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                ex.getMessage(),
//                request.getRequestURI()
//        );
//        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
