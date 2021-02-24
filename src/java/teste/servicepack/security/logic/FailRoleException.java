package teste.servicepack.security.logic;

public class FailRoleException extends AccessDeniedException
{

    public FailRoleException() {
        super();
    }

    public FailRoleException(String message) {
        super(message);
    }

    public FailRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailRoleException(Throwable cause) {
        super(cause);
    }

    public FailRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
