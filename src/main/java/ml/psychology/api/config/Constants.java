package ml.psychology.api.config;

public final class Constants {
    // Regex for acceptable username
    public static final String NUMERICAL_REASONING_QUESTION = "^([0-9]*|-|\\?)";

    // Required time for visual reasoning subtest of James Barret test
    public static final int VISUAL_REASONING_REQUIRED_MINUTE = 10;
    // Required time for numerical reasoning subtest of James Barret test
    public static final int NUMERICAL_REASONING_REQUIRED_MINUTE = 8;
    public static final int VERBAL_ANALYSIS_REQUIRED_MINUTE = 10;
    public static final int SEQUENTIAL_REASONING_REQUIRED_MINUTE = 15;
    public static final int SPATIAL_RECOGNITION_REQUIRED_MINUTE = 10;
}
