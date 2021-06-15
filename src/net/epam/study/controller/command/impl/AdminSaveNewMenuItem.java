package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.CreateTableInfoService;
import net.epam.study.service.DeleteTableInfoService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.ValidationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminSaveNewMenuItem implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ValidationService validationService = serviceProvider.getValidationService();
        CreateTableInfoService createTableInfoService = serviceProvider.getCreateTableInfoService();
        DeleteTableInfoService deleteTableInfoService = serviceProvider.getDeleteTableInfoService();

        String itemName = request.getParameter("itemName");
        String price = request.getParameter("itemPrice");
        String category = request.getParameter("category");
        String waitTime = request.getParameter("itemWaitTime");
        String success = "local.error.sucess";
        String itemExist = "local.error.adminErrorExist";

        HttpSession session = request.getSession(true);

        try {

            if (validationService.priceErrorMsg(price) == null
                    && validationService.timeErrorMsg(waitTime) == null
                    && !deleteTableInfoService.isMenuItemExists(itemName, category)) {

                createTableInfoService.createMenuItem(itemName, price, waitTime, category);


                session.setAttribute("success", success);

                session.removeAttribute("errMsgPrice");
                session.removeAttribute("errMsgWaitTime");
                session.removeAttribute("errMsgItemExist");

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin-indexPage.jsp");
                requestDispatcher.forward(request, response);

            }else {
                session.removeAttribute("success");

                session.setAttribute("errMsgPrice", validationService.priceErrorMsg(price));
                session.setAttribute("errMsgWaitTime", validationService.timeErrorMsg(waitTime));
                session.setAttribute("errMsgItemExist", itemExist);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin-indexPage.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (ServiceException e){

            session.setAttribute("error", "Add menu item fail!");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            requestDispatcher.forward(request, response);
        }


    }
}
