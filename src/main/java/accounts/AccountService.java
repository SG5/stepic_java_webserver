package accounts;

import java.util.Map;
import java.util.HashMap;

/**
 * Created on 27.01.16.
 */
public class AccountService {

    private static final Map<String, UserProfile> loginToProfile = new HashMap<>();
    private static final Map<String, UserProfile> sessionToProfile = new HashMap<>();

    public void addNewUser(UserProfile userProfile) {
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
