package net.epam.study.controller;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CommandProvider provider = new CommandProvider();

    public Controller() {
        super();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestDispatcher(response, request);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        request.getSession(true).setAttribute("local", request.getParameter("local"));
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
