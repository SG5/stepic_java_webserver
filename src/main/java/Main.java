import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.AllRequestsServlet;

import javax.servlet.http.HttpServlet;

public class Main {

    public static void main (String[] args) throws Exception {

        HttpServlet allRequestServlet = new AllRequestsServlet();

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(allRequestServlet), "/*");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        server.start();
        System.out.println("Server started");

        server.join();
    }
}
