package ai.cryptocast.core.enums;

public enum OAuth2Provider {
    LOCAL("local"),
    GITHUB("github"),
    GOOGLE("google"),
    FACEBOOK("facebook");

    private final String value;

    OAuth2Provider(String value) {
        this.value = value;
    }

    public static OAuth2Provider fromString(String provider) {
        for (OAuth2Provider oAuth2Provider : OAuth2Provider.values()) {
            if (oAuth2Provider.toString().equalsIgnoreCase(provider)) {
                return oAuth2Provider;
            }
        }
        throw new IllegalArgumentException("No enum constant " + provider);
    }

    @Override
    public String toString() {
        return value;
    }
}
