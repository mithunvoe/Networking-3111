/**
 * Constants used throughout the TCP implementation
 */
public class Constants {

    public static final int SERVER_PORT = 8080;
    public static final int WINDOW_SIZE = 4096;
    public static final String OUTPUT_FILE_PREFIX = "received_file_";
    public static final int BUFFER_SIZE = 8192;
    public static final int MAX_SEGMENT_SIZE = 730;

    public static final String SERVER_HOST = "localhost";
    public static final int CLIENT_PORT = 12345;
    public static final int CLIENT_WINDOW_SIZE = 4096;
    public static final String FILE_PATH = "hehe.txt";
    public static final int TIMEOUT_MS = 1000;
    public static final int MAX_RETRIES = 5;

    public static final int FAST_RETRANSMIT_THRESHOLD = 3;
    public static final double RTT_ALPHA = 0.125;
    public static final double RTT_BETA = 0.25;    // TCP Tahoe Congestion Control Constants
    public static final int INITIAL_SLOW_START_THRESHOLD = MAX_SEGMENT_SIZE * 8; // 8 segments instead of 64
    public static final int MIN_CONGESTION_WINDOW = MAX_SEGMENT_SIZE; // 1 MSS
    public static final int DEFAULT_CONGESTION_WINDOW = MAX_SEGMENT_SIZE; // Start with 1 MSS
    public static final int MIN_SLOW_START_THRESHOLD = 2 * MAX_SEGMENT_SIZE; // Minimum ssthresh = 2*MSS

    private Constants() {
        throw new UnsupportedOperationException("This is a constants class");
    }
}
