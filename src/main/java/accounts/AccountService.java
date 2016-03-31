package accounts;

import dbService.dataSets.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;

/**
 * Created on 27.01.16.
 */
public class AccountService {

    private static final Map<String, UserProfile> loginToProfile = new HashMap<>();
    private static final Map<String, UserProfile> sessionToProfile = new HashMap<>();

    protected final SessionFactory sessionFactory;


    public AccountService (SessionFactory sessionFactory) throws SQLException {
        this.sessionFactory = sessionFactory;
    }

    public void addNewUser(UserProfile userProfile) {
        Session session = sessionFactory.openSession();
        session.save(new UsersDataSet(userProfile.getLogin()));
        session.close();
    }

    public void addNewSession(String session, UserProfile userProfile) {
        sessionToProfile.put(session, userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        Criteria criteria = sessionFactory.openSession().createCriteria(UsersDataSet.class);
        UsersDataSet dataSet = ((UsersDataSet) criteria.add(Restrictions.eq("name", login)).uniqueResult());
        return new UserProfile(dataSet.getName());
    }

    public UserProfile getUserBySession(String session) {
        return sessionToProfile.get(session);
    }

    public void deleteSession(String session) {
        sessionToProfile.remove(session);
    }
}
