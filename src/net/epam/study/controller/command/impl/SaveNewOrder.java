package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.OrderValidateDAO;
import net.epam.study.service.FieldsValidationService;
import net.epam.study.service.ChangeOrderService;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.impl.ChangeOrderImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveNewOrder implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String city = request.getParameter("city");
        DAOProvider provider = DAOProvider.getInstance();
        OrderValidateDAO orderValidateDAO = provider.getOrderValidateDAO();
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        FieldsValidationService fieldsValidationService = serviceProvider.getFieldsValidationService();
        ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i< ChangeOrderImpl.order.size(); i++){
            stringBuilder.append(ChangeOrderImpl.order.get(i).toString()).append(" ");
        }
        String sql = "INSERT INTO orders (fullName,address,email,phone,details)" +
                "VALUES ('" + fullName + "','" + address + "','" + email + "','" + phone + "','" + stringBuilder + "')";
        if(!fieldsValidationService.isValidEmailAddress(email)) {
            request.setAttribute("errMsgEmail", "Email is incorrect");
            request.setAttribute("order", ChangeOrderImpl.order);
            request.setAttribute("total", changeOrderService.getTotal());
            request.setAttribute("size", ChangeOrderImpl.order.size());
        }
        if(!fieldsValidationService.isValidFullName(fullName)) {
            request.setAttribute("errMsgFullName", "Full name is incorrect!");
            request.setAttribute("order", ChangeOrderImpl.order);
            request.setAttribute("total", changeOrderService.getTotal());
            request.setAttribute("size", ChangeOrderImpl.order.size());
        }
        if(!fieldsValidationService.isValidPhoneNumber(phone)) {
            request.setAttribute("errMsgPhone", "Phone is incorrect!");
            request.setAttribute("order", ChangeOrderImpl.order);
            request.setAttribute("total", changeOrderService.getTotal());
            request.setAttribute("size", ChangeOrderImpl.order.size());
        }
        if(!fieldsValidationService.isValidCity(city)) {
            request.setAttribute("errMsgCity", "Only Minsk required!");
            request.setAttribute("order", ChangeOrderImpl.order);
            request.setAttribute("total", changeOrderService.getTotal());
            request.setAttribute("size", ChangeOrderImpl.order.size());
        }
        if (fieldsValidationService.isValidEmailAddress(email)
                &&fieldsValidationService.isValidFullName(fullName)
                &&fieldsValidationService.isValidPhoneNumber(phone)
                &&fieldsValidationService.isValidCity(city)){
            if (ChangeOrderImpl.order.size() == 0) {
                request.setAttribute("error", "You cant checkout with empty cart!");
                request.setAttribute("order", ChangeOrderImpl.order);
                request.setAttribute("total", changeOrderService.getTotal());
                request.setAttribute("size", ChangeOrderImpl.order.size());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
                requestDispatcher.forward(request, response);
            } else {
                orderValidateDAO.validate(sql);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bill-indexPage.jsp");
                requestDispatcher.forward(request, response);
            }
        } else{
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
