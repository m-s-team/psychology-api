package ml.psychology.api.config;

public final class Constants {
    // Regex for acceptable username
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    // Required time for visual reasoning subtest of James Barret test
    public static final int VISUAL_REASONING_REQUIRED_MINUTE = 10;
    // Required time for numerical reasoning subtest of James Barret test
    public static final int NUMERICAL_REASONING_REQUIRED_MINUTE = 8;
}
