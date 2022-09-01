package ai.cryptocast.core.domain.exceptions;

public class NotFoundEmailException extends EmailException {
    public NotFoundEmailException(String emailAddress) {
        super(String.format("%s not found", emailAddress));
    }
}
