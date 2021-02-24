package teste.servicepack.security.logic;

public class NotAuthenticatedException extends AccessDeniedException {
    public NotAuthenticatedException() { super(); }

    public NotAuthenticatedException(String message) { super(message); }

    public NotAuthenticatedException(String message, Throwable cause) { super(message, cause); }

    public NotAuthenticatedException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace) {
        super(message, cause, enableSupression, writableStackTrace);
    }

    public String toString() { return "Not authenticated."; }
}
