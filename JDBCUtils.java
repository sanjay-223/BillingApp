import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class JDBCUtils {
    
    private static final String PROPERTIES_FILE = "db.properties";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/BillingDB";
    
    private static String jdbcUser;
    private static String jdbcPassword;
    
    static {
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE)) {
            Properties properties = new Properties();
            properties.load(input);
            jdbcUser = properties.getProperty("username");
            jdbcPassword = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to load database properties file.");
        }
    }
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, jdbcUser, jdbcPassword);
    }
}
