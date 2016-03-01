package dbService;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;

/**
 * Created on 15.02.16.
 */
public class DBService {

    protected final Connection connection;

    public DBService() {
        this.connection = getH2Connection();
    }

    public void test() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS my_table");
            statement.execute("CREATE TABLE my_table (id int PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255))");
            statement.execute("INSERT INTO my_table (name) VALUES ('qwe'), ('qw'), ('asd')");
            statement.close();

            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM my_table WHERE name LIKE ?");
            pStmt.setString(1, "qa%");
            ResultSet result = pStmt.executeQuery();

            while (result.next()) {
                System.out.print("id: " + result.getInt("id"));
                System.out.println(" name: " + result.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db";
            String name = "test";
            String pass = "test";

            return DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
