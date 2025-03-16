// This class handles the librarian key validation
public class LibrarianKey {
    // The secret key that librarians need to use
    private static final String SECRET_KEY = "LIB123";
    
    // The number of failed attempts allowed before temporary lockout
    private static final int MAX_ATTEMPTS = 3;
    
    // Counter for failed attempts
    private static int failedAttempts = 0;
    
    // Time when the lockout started
    private static long lockoutStartTime = 0;
    
    // Lockout duration in milliseconds (5 minutes)
    private static final long LOCKOUT_DURATION = 5 * 60 * 1000;
    
    // Method to validate the librarian key
    public static boolean validateKey(String inputKey) {
        // Check if system is in lockout
        if (isLockedOut()) {
            long remainingTime = (lockoutStartTime + LOCKOUT_DURATION - System.currentTimeMillis()) / 1000;
            throw new SecurityException("Too many failed attempts. Please try again in " + remainingTime + " seconds.");
        }
        
        // Check if the input key matches the secret key
        if (SECRET_KEY.equals(inputKey)) {
            // Reset failed attempts on successful validation
            failedAttempts = 0;
            return true;
        } else {
            // Increment failed attempts
            failedAttempts++;
            
            // Check if max attempts reached
            if (failedAttempts >= MAX_ATTEMPTS) {
                lockoutStartTime = System.currentTimeMillis();
                throw new SecurityException("Maximum attempts reached. System locked for 5 minutes.");
            }
            
            return false;
        }
    }
    
    // Method to check if the system is currently locked out
    private static boolean isLockedOut() {
        if (failedAttempts >= MAX_ATTEMPTS) {
            // Check if lockout duration has passed
            if (System.currentTimeMillis() - lockoutStartTime >= LOCKOUT_DURATION) {
                // Reset if lockout duration has passed
                failedAttempts = 0;
                return false;
            }
            return true;
        }
        return false;
    }
    
    // Method to get remaining attempts
    public static int getRemainingAttempts() {
        return MAX_ATTEMPTS - failedAttempts;
    }
    
    // Method to reset attempts (used for testing)
    public static void resetAttempts() {
        failedAttempts = 0;
        lockoutStartTime = 0;
    }
} 