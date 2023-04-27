package jm.task.core.jdbc.util;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class Util {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Загрузка драйвера JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
                Properties props = getProps();


                // Создание подключения к базе данных
                conn = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.username"), props.getProperty("db.password"));

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }
    private static Properties getProps() throws IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(Util.class.getResource("/database.properties").toURI()))) {
            props.load(in);
            return props;
        } catch (IOException | URISyntaxException e) {
            throw new IOException("Database config file not found", e);
        }
    }


}