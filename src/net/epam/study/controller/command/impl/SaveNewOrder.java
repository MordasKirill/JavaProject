package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.service.ChangeOrderService;
import net.epam.study.service.OrderCreateService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.impl.ChangeOrderImpl;
import net.epam.study.service.validation.ValidationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveNewOrder implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ValidationService validationService = serviceProvider.getValidationService();
        ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();
        OrderCreateService orderCreateService = serviceProvider.getOrderCreateService();

        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String city = request.getParameter("city");
        String paymentMethod = request.getParameter("method");

        HttpSession session = request.getSession(true);
        String login = (String) session.getAttribute("login");


        try {

            if (validationService.emailErrorMsg(email)==null
                    && validationService.fullNameErrorMsg(fullName)==null
                    && validationService.phoneErrorMsg(phone)==null
                    && validationService.cityErrorMsg(city)==null){

                if (ChangeOrderImpl.ORDER.size() == 0) {

                    request.setAttribute("error", "local.error.orderEmpty");
                    request.setAttribute("order", ChangeOrderImpl.ORDER);
                    request.setAttribute("total", changeOrderService.getTotal(login));
                    request.setAttribute("size", ChangeOrderImpl.ORDER.size());

                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
                    requestDispatcher.forward(request, response);

                } else {

                    orderCreateService.create(fullName, address, email, phone, changeOrderService.getOrder(), login, changeOrderService.getTotal(login));


                    if (paymentMethod.equals("online")){
                        String status = "processing";
                        orderCreateService.payment(login, changeOrderService.getTotal(login), status);

                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/payment-indexPage.jsp");
                        requestDispatcher.forward(request, response);
                    }else {

                        String status = "uponReceipt";
                        orderCreateService.payment(login, changeOrderService.getTotal(login), status);

                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/bill-indexPage.jsp");
                        requestDispatcher.forward(request, response);
                    }
                }

            } else{

                session.setAttribute("emailSession", email);
                session.setAttribute("fullNameSession", fullName);
                session.setAttribute("addressSession", address);
                session.setAttribute("phoneSession", phone);
                session.setAttribute("citySession", city);

                request.setAttribute("errMsgEmail", validationService.emailErrorMsg(email));
                request.setAttribute("errMsgFullName", validationService.fullNameErrorMsg(fullName));
                request.setAttribute("errMsgPhone", validationService.phoneErrorMsg(phone));
                request.setAttribute("errMsgCity", validationService.cityErrorMsg(city));
                request.setAttribute("order", ChangeOrderImpl.ORDER);
                request.setAttribute("total", changeOrderService.getTotal(login));
                request.setAttribute("size", ChangeOrderImpl.ORDER.size());


                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/basketPage.jsp");
                requestDispatcher.forward(request, response);
            }

        } catch (ServiceException e){

            session.setAttribute("error", "Save order fail!");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
