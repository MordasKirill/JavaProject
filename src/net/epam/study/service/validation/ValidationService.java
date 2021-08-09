package net.epam.study.service.validation;

import net.epam.study.bean.MenuItem;

import java.util.LinkedList;
import java.util.Map;

public interface ValidationService {
    String emailErrorMsg(String email);

    String fullNameErrorMsg(String fullName);

    String phoneErrorMsg(String phone);

    String cityErrorMsg(String city);

    String priceErrorMsg(String price);

    String timeErrorMsg(String time);

    boolean isParamNotNull(String param);

    boolean isParamNotNull(int param);

    boolean isParamNotNull(Map<Integer, LinkedList<MenuItem>> param);

    boolean isAuthenticated(Boolean auth, String role);

    boolean isAdmin(String role);

    boolean isUser(String role);
}