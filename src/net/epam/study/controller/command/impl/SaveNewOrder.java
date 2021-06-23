package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.ChangeOrderService;
import net.epam.study.service.CreateTableInfoService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.impl.ChangeOrderImpl;
import net.epam.study.service.validation.ValidationService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveNewOrder implements Command {

    public static final String ATTR_EMAIL = "email";
    public static final String ATTR_FULL_NAME = "fullName";
    public static final String ATTR_ADDRESS = "address";
    public static final String ATTR_PHONE = "phone";
    public static final String ATTR_CITY = "city";
    public static final String ATTR_METHOD = "method";
    public static final String ATTR_METHOD_ONLINE = "online";
    public static final String ATTR_LOGIN = "login";

    public static final String ATTR_EMAIL_SESSION = "emailSession";
    public static final String ATTR_FULL_NAME_SESSION = "fullNameSession";
    public static final String ATTR_ADDRESS_SESSION = "addressSession";
    public static final String ATTR_PHONE_SESSION = "phoneSession";
    public static final String ATTR_CITY_SESSION = "citySession";
    public static final String ATTR_ERR_MSG_EMAIL = "errMsgEmail";
    public static final String ATTR_ERR_MSG_FULL_NAME = "errMsgFullName";
    public static final String ATTR_ERR_MSG_PHONE = "errMsgPhone";
    public static final String ATTR_ERR_MSG_CITY = "errMsgCity";

    public static final String ATTR_ORDER = "order";
    public static final String ATTR_TOTAL = "total";
    public static final String ATTR_SIZE = "size";

    public static final String STATUS_PROCESSING = "processing";
    public static final String STATUS_UPON_RECEIPT = "uponReceipt";

    public static final String ATTR_ERROR = "error";
    public static final String ATTR_ERROR_MSG = "local.error.orderEmpty";
    public static final String ATTR_ERROR_ORDER_MSG = "Save order fail!";

    private static final Logger log = Logger.getLogger(SaveNewOrder.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ValidationService validationService = serviceProvider.getValidationService();
        ChangeOrderService changeOrderService = serviceProvider.getChangeOrderService();
        CreateTableInfoService createTableInfoService = serviceProvider.getCreateTableInfoService();

        String email = request.getParameter(ATTR_EMAIL);
        String fullName = request.getParameter(ATTR_FULL_NAME);
        String address = request.getParameter(ATTR_ADDRESS);
        String phone = request.getParameter(ATTR_PHONE);
        String city = request.getParameter(ATTR_CITY);
        String paymentMethod = request.getParameter(ATTR_METHOD);

        HttpSession session = request.getSession(true);
        String login = (String) session.getAttribute(ATTR_LOGIN);


        try {

            if (validationService.emailErrorMsg(email)==null
                    && validationService.fullNameErrorMsg(fullName)==null
                    && validationService.phoneErrorMsg(phone)==null
                    && validationService.cityErrorMsg(city)==null){

                if (ChangeOrderImpl.ORDER.size() == 0) {

                    request.setAttribute(ATTR_ERROR, ATTR_ERROR_MSG);
                    request.setAttribute(ATTR_ORDER, ChangeOrderImpl.ORDER);
                    request.setAttribute(ATTR_TOTAL, changeOrderService.getTotal(login));
                    request.setAttribute(ATTR_SIZE, ChangeOrderImpl.ORDER.size());

                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BASKET);
                    requestDispatcher.forward(request, response);

                } else {

                    createTableInfoService.create(fullName, address, email, phone, changeOrderService.getOrder());


                    if (paymentMethod.equals(ATTR_METHOD_ONLINE)){
                        createTableInfoService.payment(login, changeOrderService.getTotal(login), STATUS_PROCESSING);

                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_PAYMENT_INDEX);
                        requestDispatcher.forward(request, response);
                    }else {
                        createTableInfoService.payment(login, changeOrderService.getTotal(login), STATUS_UPON_RECEIPT);

                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BILL_INDEX);
                        requestDispatcher.forward(request, response);
                    }
                }

            } else{

                session.setAttribute(ATTR_EMAIL_SESSION, email);
                session.setAttribute(ATTR_FULL_NAME_SESSION, fullName);
                session.setAttribute(ATTR_ADDRESS_SESSION, address);
                session.setAttribute(ATTR_PHONE_SESSION, phone);
                session.setAttribute(ATTR_CITY_SESSION, city);

                request.setAttribute(ATTR_ERR_MSG_EMAIL, validationService.emailErrorMsg(email));
                request.setAttribute(ATTR_ERR_MSG_FULL_NAME, validationService.fullNameErrorMsg(fullName));
                request.setAttribute(ATTR_ERR_MSG_PHONE, validationService.phoneErrorMsg(phone));
                request.setAttribute(ATTR_ERR_MSG_CITY, validationService.cityErrorMsg(city));
                request.setAttribute(ATTR_ORDER, ChangeOrderImpl.ORDER);
                request.setAttribute(ATTR_TOTAL, changeOrderService.getTotal(login));
                request.setAttribute(ATTR_SIZE, ChangeOrderImpl.ORDER.size());


                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_BASKET);
                requestDispatcher.forward(request, response);
            }

        } catch (ServiceException e){

            log.log(Level.ERROR,"SaveNewOrder error.", e);
            session.setAttribute(ATTR_ERROR, ATTR_ERROR_ORDER_MSG);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
            requestDispatcher.forward(request, response);
        }
    }
}
