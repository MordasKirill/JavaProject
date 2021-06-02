package net.epam.study.dao;

public interface OrderCreateDAO {
    void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws DAOException;
}
