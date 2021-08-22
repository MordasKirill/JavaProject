package net.epam.study;

public class Constants {
    public static final String ATTR_AUTH = "auth";
    public static final String ATTR_ROLE = "role";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_PRICE = "price";
    public static final String PARAM_USER = "user";
    public static final String PARAM_CATEGORY = "category";
    public static final String ITEM_NAME_DELETE = "itemNameDelete";
    public static final String CATEGORY_DELETE = "categoryDelete";
    public static final String PARAM_ID = "id";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_LOCALE = "locale";
    public static final String ATTR_LOCAL = "local";
    public static final String PARAM_LOGIN = "login";
    public static final String PARAM_STATUS = "status";
    public static final String PARAM_EMAIL = "email";
    public static final String ID_ORDER = "idOrder";
    public static final String ID_USER = "idUser";
    public static final int DEFAULT_LIMIT = 8;
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    public static final String FULL_NAME_PATTERN = "^([А-ЯA-Z]|[А-ЯA-Z][\\x27а-яa-z]{1,}|[А-ЯA-Z][\\x27а-яa-z]{1,}-([А-ЯA-Z][\\x27а-яa-z]{1,}|(оглы)|(кызы)))\\040[А-ЯA-Z][\\x27а-яa-z]{1,}(\\040[А-ЯA-Z][\\x27а-яa-z]{1,})?$";
    public static final String PHONE_PATTERN = "^\\+375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$";
    public static final String PRICE_PATTERN = "\\d{1,4}\\.\\d{1,3}";
    public static final String TIME_PATTERN = "\\d{1,4}";
    public static final String EMAIL_ERROR = "local.error.orderErrorEmail";
    public static final String FULL_NAME_ERROR = "local.error.orderErrorFullName";
    public static final String PRICE_ERROR = "local.error.errMsgNullPrice";
    public static final String PHONE_ERROR = "local.error.orderErrorPhone";
    public static final String CITY_ERROR = "local.error.orderErrorCity";
    public static final String ADMIN_PRICE_ERROR = "local.error.adminErrorPrice";
    public static final String TIME_ERROR = "local.error.adminErrorTime";
}
