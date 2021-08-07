package net.epam.study.controller.command.impl;

import net.epam.study.controller.command.Command;
import net.epam.study.controller.command.PagePath;
import net.epam.study.service.MenuService;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.ValidationService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminSaveNewMenuItem implements Command {

    private static final String PARAM_ITEM_NAME = "itemName";
    private static final String PARAM_PRICE = "itemPrice";
    private static final String PARAM_CATEGORY = "category";
    private static final String PARAM_WAIT_TIME = "itemWaitTime";

    private static final String SUCCESS_MSG = "local.error.sucess";
    private static final String SUCCESS_ATTR = "success";
    private static final String ITEM_ERROR = "local.error.adminErrorExist";
    private static final String PRICE_ERROR = "errMsgPrice";
    private static final String WAIT_TIME_ERROR = "errMsgWaitTime";
    private static final String ITEM_ERROR_ERR_MSG_ITEM_EXIST = "errMsgItemExist";
    private static final String ERROR_MSG = "Add menu item fail!";
    private static final String ERROR_ATTR = "error";

    private static final Logger log = Logger.getLogger(AdminSaveNewMenuItem.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ValidationService validationService = serviceProvider.getValidationService();
        MenuService menuService = serviceProvider.getMenuService();


        String itemName = request.getParameter(PARAM_ITEM_NAME);
        String price = request.getParameter(PARAM_PRICE);
        String category = request.getParameter(PARAM_CATEGORY);
        String waitTime = request.getParameter(PARAM_WAIT_TIME);

        HttpSession session = request.getSession(true);

        try {

            if (validationService.priceErrorMsg(price) == null
                    && validationService.timeErrorMsg(waitTime) == null
                    && !menuService.isMenuItemExists(itemName, category)) {


                menuService.createMenuItem(itemName, price, waitTime, category);


                session.setAttribute(SUCCESS_ATTR, SUCCESS_MSG);

                session.removeAttribute(PRICE_ERROR);
                session.removeAttribute(WAIT_TIME_ERROR);
                session.removeAttribute(ITEM_ERROR_ERR_MSG_ITEM_EXIST);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
                requestDispatcher.forward(request, response);

            } else {

                if (menuService.isMenuItemExists(itemName, category)) {

                    session.setAttribute(ITEM_ERROR_ERR_MSG_ITEM_EXIST, ITEM_ERROR);
                }

                session.removeAttribute(SUCCESS_ATTR);

                session.setAttribute(PRICE_ERROR, validationService.priceErrorMsg(price));
                session.setAttribute(WAIT_TIME_ERROR, validationService.timeErrorMsg(waitTime));


                RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.FORWARD_ADMIN_INDEX);
                requestDispatcher.forward(request, response);
            }
        } catch (ServiceException e) {

            log.log(Level.ERROR, "AdminSaveNewMenuItem error.", e);
            session.setAttribute(ERROR_ATTR, ERROR_MSG);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.ERROR);
            requestDispatcher.forward(request, response);
        }


    }
}
