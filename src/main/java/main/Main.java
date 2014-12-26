package main;

import admin.*;
import base.AccountService;
import base.AccountServiceDBImpl;
import base.DBServiceImpl;
import base.DatabaseService;
import mechanics.GameMechanics;
import mechanics.GameMechanicsImpl;
import servlets.*;
import sockets.WebSocketGameServlet;
import sockets.WebSocketService;
import sockets.WebSocketServiceImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resources.ResourceFactory;
import resources.ServerResources;

import javax.servlet.Servlet;

/**
 * Created by Dmitry
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ServerResources res = ResourceFactory.getInstance().getServerResources();

        int port = res.getPort();
        if (args.length > 0) {
            port = new Integer(args[0]);
        }

        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

        DatabaseService databaseService = new DBServiceImpl(res.getDb_name());
        AccountService accountService = new AccountServiceDBImpl(databaseService);
        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);

        Servlet signupServlet = new SignupServletImpl(accountService);
        Servlet signinServlet = new SigninServletImpl(accountService);
        Servlet mainServlet = new MainPageServletImpl(accountService);
        Servlet logoutServlet = new LogoutServletImpl(accountService);
        Servlet adminServlet = new AdminPageServlet(accountService);
        Servlet profileServlet = new ProfileServletImpl(accountService);
        Servlet gameServlet = new GameServlet(accountService, port);
        Servlet webSocketGameServlet = new WebSocketGameServlet(accountService, port, gameMechanics, webSocketService);

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signupServlet),        "/auth/signup");
        context.addServlet(new ServletHolder(signinServlet),        "/auth/signin");
        context.addServlet(new ServletHolder(logoutServlet),        "/auth/logout");
        context.addServlet(new ServletHolder(mainServlet),          "/main");
        context.addServlet(new ServletHolder(profileServlet),       "/profile");
        context.addServlet(new ServletHolder(adminServlet),         "/admin");
        context.addServlet(new ServletHolder(gameServlet),          "/game");
        context.addServlet(new ServletHolder(webSocketGameServlet), "/gameplay");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        //gameMechanics.run();
    }
}