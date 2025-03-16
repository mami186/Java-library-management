// This class handles the database connection
// It uses the Singleton pattern to ensure we only have one connection instance
public class DatabaseConnection {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    // The single connection instance
    private static Connection connection = null;
    
    // Private constructor to prevent direct instantiation
    private DatabaseConnection() {}
    
    // Method to get the database connection
    public static Connection getConnection() {
        try {
            // If connection doesn't exist or is closed, create a new one
            if (connection == null || connection.isClosed()) {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Create the connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection to database failed!");
            e.printStackTrace();
        }
        return connection;
    }
    
    // Method to close the database connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection!");
            e.printStackTrace();
        }
    }
} 