package by.epam.java.training.lifehacks.weblayer.controller;

import by.epam.java.training.lifehacks.exception.ServiceException;
import by.epam.java.training.assanoooovi4k.lifehacks.weblayer.command.*;
import by.epam.java.training.lifehacks.dao.connection.ConnectionPool;
import by.epam.java.training.lifehacks.exception.DaoException;
import by.epam.java.training.lifehacks.weblayer.command.*;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.omg.CORBA.portable.ApplicationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main")
@MultipartConfig
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    public void init() throws ServletException {
        super.init();
        ConnectionPool.getInstance();
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().destroyConnections();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent(req);
        CommandFactory factory = CommandFactory.getInstance();
        if (factory.getCommand(requestContent) != null) {
            Command command = factory.getCommand(requestContent);
            CommandResult result = null;
            try {
                result = command.execute(requestContent);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, "Application error.", e);
                resp.sendError(500);
            } catch (DaoException e) {
                e.printStackTrace();
            }

            if (result != null) {
                if (ResponseType.FORWARD.equals(result.getResponseType())){
                    req.getRequestDispatcher(result.getPage()).forward(req, resp);
                } else {
                    resp.sendRedirect(result.getPage());
                }
            } else {
                logger.log(Level.ERROR, "Service return null.");
                resp.sendError(500);
            }
        } else {
            logger.log(Level.ERROR, "Request has no command.");
            resp.sendError(500);
        }
    }

}
