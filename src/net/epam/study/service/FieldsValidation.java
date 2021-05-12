package net.epam.study.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldsValidation {
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(ePattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidFullName(String fullName){
        String ePattern = "^([А-ЯA-Z]|[А-ЯA-Z][\\x27а-яa-z]{1,}|[А-ЯA-Z][\\x27а-яa-z]{1,}-([А-ЯA-Z][\\x27а-яa-z]{1,}|(оглы)|(кызы)))\\040[А-ЯA-Z][\\x27а-яa-z]{1,}(\\040[А-ЯA-Z][\\x27а-яa-z]{1,})?$";
        Pattern pattern = Pattern.compile(ePattern);
        Matcher matcher = pattern.matcher(fullName);
        return matcher.matches();
    }
    public static boolean isValidPhoneNumber(String phone){
        String ePattern = "^\\+375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$";
        Pattern pattern = Pattern.compile(ePattern);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    public static boolean isValidCity(String city){
        boolean result = false;
        if (city.equals("Minsk")
                ||city.equals("minsk")
                ||city.equals("минск")
                ||city.equals("Минск")){

            result = true;
        }
        return result;
    }
}
