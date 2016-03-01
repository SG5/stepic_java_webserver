package dbService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created on 22.02.16.
 */
public class Executor {

    private final Connection connection;

    public Executor(DBService dbService) {
        this.connection = dbService.connection;
    }

    public void execUpdate(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        stmt.close();
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);

        ResultSet resultSet = stmt.getResultSet();
        T values = handler.handle(resultSet);

        resultSet.close();
        stmt.close();

        return values;
    }
}
