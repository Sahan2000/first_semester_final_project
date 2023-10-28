package lk.ijse.shopManagement.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO{
    boolean save(T entity) throws SQLException;

    boolean update(T entity) throws SQLException;

    ArrayList<T> getAll() throws SQLException;

    T search(String id) throws SQLException;

    boolean delete(String id) throws SQLException;

    String generateNextId() throws SQLException;
}
