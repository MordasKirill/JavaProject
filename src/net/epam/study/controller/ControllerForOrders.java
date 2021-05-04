package net.epam.study.controller;

import net.epam.study.controller.commands.Command;
import net.epam.study.controller.commands.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerForOrders extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CommandProvider provider = new CommandProvider();
    
    public ControllerForOrders() {
        super();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestDispatcher(response, request);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        requestDispatcher(response, request);
    }
    private void requestDispatcher(HttpServletResponse response, HttpServletRequest request) throws  IOException, ServletException{
        String name;
        Command command;
        name = request.getParameter("command");
        command = provider.takeCommand(name);
        command.execute(request, response);
    }
}
