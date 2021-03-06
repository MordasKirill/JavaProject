package net.epam.study.controller;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.CommandProvider;
import net.epam.study.controller.command.PagePath;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private final CommandProvider provider = new CommandProvider();
    private final String COMMAND_PARAM = "command";

    public Controller() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(response, request);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(response, request);
    }

    private void process(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        String name;
        Command command;
        name = request.getParameter(COMMAND_PARAM);
        if (provider.isContains(name)) {
            command = provider.takeCommand(name);
            command.execute(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_LOGIN);
            requestDispatcher.forward(request, response);
        }
    }
}
