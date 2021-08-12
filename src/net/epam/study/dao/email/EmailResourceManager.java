package net.epam.study.dao.email;

import java.util.ResourceBundle;

public class EmailResourceManager {
    private final static EmailResourceManager instance = new EmailResourceManager();

    private final ResourceBundle bundle = ResourceBundle.getBundle("resources.mail");

    public static EmailResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
