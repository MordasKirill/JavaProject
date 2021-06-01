package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.dao.impl.OrderCreateImpl;
import net.epam.study.service.ChangeOrderService;
import net.epam.study.service.validation.FieldsValidationService;
import net.epam.study.service.OrderCreateService;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.impl.ChangeOrderImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveNewOrder implements Command {
    ServiceProvider serviceProvider = ServiceProvider.getInstance();
    FieldsValidationService fieldsValidationService = serviceProvider.getFieldsValidationService();
    ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();
    OrderCreateService orderCreateService = serviceProvider.getOrderCreateService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String city = request.getParameter("city");
        HttpSession session = request.getSession(true);
        if (fieldsValidationService.emailErrorMsg(email)==null
                ||fieldsValidationService.fullNameErrorMsg(fullName)==null
                ||fieldsValidationService.phoneErrorMsg(phone)==null
                ||fieldsValidationService.cityErrorMsg(city)==null){
            if (ChangeOrderImpl.order.size() == 0) {
                request.setAttribute("error", "You cant checkout with empty cart!");
                request.setAttribute("order", ChangeOrderImpl.order);
                request.setAttribute("total", changeOrderService.getTotal());
                request.setAttribute("size", ChangeOrderImpl.order.size());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
                requestDispatcher.forward(request, response);
            } else {
                if (OrderCreateImpl.error != null) {
                    session.setAttribute("error", OrderCreateImpl.error);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
                    requestDispatcher.forward(request, response);
                    return;
                }
                orderCreateService.create(fullName, address, email, phone, changeOrderService.getOrder());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bill-indexPage.jsp");
                requestDispatcher.forward(request, response);
            }
        } else{
            request.setAttribute("errMsgEmail", fieldsValidationService.emailErrorMsg(email));
            request.setAttribute("errMsgFullName", fieldsValidationService.fullNameErrorMsg(fullName));
            request.setAttribute("errMsgPhone", fieldsValidationService.phoneErrorMsg(phone));
            request.setAttribute("errMsgCity", fieldsValidationService.cityErrorMsg(city));
            request.setAttribute("order", ChangeOrderImpl.order);
            request.setAttribute("total", changeOrderService.getTotal());
            request.setAttribute("size", ChangeOrderImpl.order.size());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
