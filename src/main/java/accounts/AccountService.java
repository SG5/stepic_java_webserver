package accounts;

import dbService.DBService;
import dbService.Executor;

import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;

/**
 * Created on 27.01.16.
 */
public class AccountService {

    private static final Map<String, UserProfile> loginToProfile = new HashMap<>();
    private static final Map<String, UserProfile> sessionToProfile = new HashMap<>();

    protected final Executor executor;

    public AccountService (DBService dbService) throws SQLException {
        executor = new Executor(dbService);

        executor.execUpdate("CREATE TABLE IF NOT EXISTS users ("
            +"id int PRIMARY KEY AUTO_INCREMENT"
            +", login VARCHAR(255)"
            +", password VARCHAR(255)"
            +", email VARCHAR(255)"
            +");"
        );
    }

    public void addNewUser(UserProfile userProfile) {
        try {
            executor.execUpdate(String.format(
                "INSERT INTO users (login, password, email) VALUES ('%s', '%s', '%s')",
                userProfile.getLogin(), userProfile.getEmail(), userProfile.getPassword()
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public void addNewSession(String session, UserProfile userProfile) {
        sessionToProfile.put(session, userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySession(String session) {
        return sessionToProfile.get(session);
    }

    public void deleteSession(String session) {
        sessionToProfile.remove(session);
    }
}
