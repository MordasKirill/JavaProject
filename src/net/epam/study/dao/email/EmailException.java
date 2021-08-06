package net.epam.study.dao.email;

public class EmailException extends Exception {
    private static final long serialVersionUID = 1L;

    public EmailException(String message, Exception e) {
        super(message, e);
    }
}
