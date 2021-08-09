package net.epam.study.service.validation.impl;

import net.epam.study.bean.MenuItem;
import net.epam.study.controller.command.Role;
import net.epam.study.service.validation.ValidationService;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationImpl implements ValidationService {
    public static Pattern pattern;
    public static Matcher matcher;
    public static String userLocale;

    public boolean isValidEmailAddress(String email) {
        if (email == null) {
            return false;
        }
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        pattern = Pattern.compile(ePattern);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidFullName(String fullName) {
        if (fullName == null) {
            return false;
        }
        String ePattern = "^([А-ЯA-Z]|[А-ЯA-Z][\\x27а-яa-z]{1,}|[А-ЯA-Z][\\x27а-яa-z]{1,}-([А-ЯA-Z][\\x27а-яa-z]{1,}|(оглы)|(кызы)))\\040[А-ЯA-Z][\\x27а-яa-z]{1,}(\\040[А-ЯA-Z][\\x27а-яa-z]{1,})?$";
        pattern = Pattern.compile(ePattern);
        matcher = pattern.matcher(fullName);
        return matcher.matches();
    }

    public boolean isValidPhoneNumber(String phone) {
        if (phone == null) {
            return false;
        }
        String ePattern = "^\\+375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$";
        pattern = Pattern.compile(ePattern);
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }


    public boolean isValidPrice(String price) {
        if (price == null) {
            return false;
        }
        String ePattern = "\\d{1,4}\\.\\d{1,3}";
        pattern = Pattern.compile(ePattern);
        matcher = pattern.matcher(price);
        return matcher.matches();
    }

    public boolean isValidTime(String time) {
        if (time == null) {
            return false;
        }
        String ePattern = "\\d{1,4}";
        pattern = Pattern.compile(ePattern);
        matcher = pattern.matcher(time);
        return matcher.matches();
    }

    public boolean isValidCity(String city) {
        if (city == null) {
            return false;
        }

        boolean result = false;
        if (city.equalsIgnoreCase("Minsk") ||
                city.equalsIgnoreCase("Минск")) {
            result = true;
        }
        return result;
    }

    public String emailErrorMsg(String email) {

        if (!isValidEmailAddress(email)) {
            return "local.error.orderErrorEmail";
        }
        return null;
    }

    public String fullNameErrorMsg(String fullName) {
        if (!isValidFullName(fullName)) {
            return "local.error.orderErrorFullName";
        }
        return null;
    }

    public String phoneErrorMsg(String phone) {
        if (!isValidPhoneNumber(phone)) {
            return "local.error.orderErrorPhone";
        }
        return null;
    }

    public String cityErrorMsg(String city) {
        if (!isValidCity(city)) {
            return "local.error.orderErrorCity";
        }
        return null;
    }

    public String priceErrorMsg(String price) {
        if (!isValidPrice(price)) {
            return "local.error.adminErrorPrice";
        }
        return null;
    }

    public String timeErrorMsg(String time) {
        if (!isValidTime(time)) {
            return "local.error.adminErrorTime";
        }
        return null;
    }

    public boolean isAuthenticated(Boolean auth, String role) {
        return auth != null && auth && role != null;
    }

    public boolean isAdmin(String role) {
        return !Role.ADMIN.toString().equalsIgnoreCase(role) && !Role.OWNER.toString().equalsIgnoreCase(role);
    }

    public boolean isUser(String role) {
        return !Role.USER.toString().equalsIgnoreCase(role);
    }

    public boolean isParamNotNull(String param) {
        return !(param == null);
    }

    public boolean isParamNotNull(int param) {
        return !(param == 0);
    }

    public boolean isParamNotNull(Map<Integer, LinkedList<MenuItem>> param) {
        return !(param.size() == 0);
    }


}
