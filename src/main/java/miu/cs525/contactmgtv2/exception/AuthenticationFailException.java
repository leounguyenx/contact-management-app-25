package miu.cs525.contactmgtv2.exception;

public class AuthenticationFailException extends RuntimeException {
    public AuthenticationFailException(String message) {
        super(message);
    }
}
