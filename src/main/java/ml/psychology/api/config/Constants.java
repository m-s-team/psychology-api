package ml.psychology.api.config;

public final class Constants {
    // Regex for acceptable username
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    // WAIS-IV required time for picture reasoning subtest
    public static final int PICTURE_REASONING_REQUIRED_MINUTE = 20;
}
