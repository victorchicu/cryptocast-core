package ai.cryptocast.core.domain.exceptions;

public class OAuth2AuthenticationProcessingException extends RuntimeException {
    public OAuth2AuthenticationProcessingException(String message) {
        super(message);
    }
}
