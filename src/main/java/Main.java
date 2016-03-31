import accounts.AccountService;
import accounts.UserProfile;
import dbService.DBService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SessionServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;
import servlets.UserServlet;

import java.sql.Driver;
import java.sql.SQLException;

public class Main {

    protected static AccountService accountService;

    public static void main (String[] args) throws Exception {
        try {
            initServices();
            initServlets();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void initServices() throws SQLException {
        DBService dbService = new DBService();

        accountService = new AccountService(dbService.getSessionFactory());

        accountService.addNewUser(new UserProfile("admin"));
        accountService.addNewUser(new UserProfile("test"));

        System.out.println(accountService.getUserByLogin("test"));
    }

    protected static void initServlets() throws Exception {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        contextHandler.addServlet(new ServletHolder(new UserServlet(accountService)), "/api/users");
        contextHandler.addServlet(new ServletHolder(new SessionServlet(accountService)), "/api/sessions");
        contextHandler.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        contextHandler.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, contextHandler});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");

        server.join();
    }
}
