package miu.cs525.contactmgtv2.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpServletResponse.SC_FORBIDDEN,
                HttpStatus.FORBIDDEN,
                "Access Denied: You don’t have permission to access this resource.",
                request.getRequestURI()
        );

        ObjectMapper mapper = new ObjectMapper();
        String jsonError = mapper.writeValueAsString(apiError);
        response.getWriter().write(jsonError);
    }
}
