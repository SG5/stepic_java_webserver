package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 27.01.16.
 */
public class SessionServlet extends HttpServlet {

    private final AccountService accountService;

    public SessionServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        super.service(req, resp);
    }

    //get logged user profile
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySession(sessionId);

        if (null == profile) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(profile);
            resp.getWriter().print(json);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (null == login || null == password) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile profile = accountService.getUserByLogin(login);
        if (null == profile || !profile.getPassword().equals(password)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        accountService.addNewSession(req.getSession().getId(), profile);
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        resp.getWriter().print(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String session = req.getSession().getId();
        UserProfile profile = accountService.getUserBySession(session);

        if (null == profile) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        accountService.deleteSession(session);
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        resp.getWriter().print(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
