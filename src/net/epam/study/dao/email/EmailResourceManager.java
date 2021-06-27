package net.epam.study.dao.email;

import java.util.ResourceBundle;

public class EmailResourceManager {
    private final static EmailResourceManager instance = new EmailResourceManager();

    private ResourceBundle bundle = ResourceBundle.getBundle("net.epam.study.dao.email.mail");

    public static EmailResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
