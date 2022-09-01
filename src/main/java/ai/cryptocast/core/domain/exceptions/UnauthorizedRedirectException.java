package ai.cryptocast.core.domain.exceptions;

public class UnauthorizedRedirectException extends RuntimeException {
    public UnauthorizedRedirectException(String message) {
        super(message);
    }
}
