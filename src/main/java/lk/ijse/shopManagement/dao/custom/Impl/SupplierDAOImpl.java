package lk.ijse.shopManagement.dao.custom.Impl;

import lk.ijse.shopManagement.dao.custom.SupplierDAO;
import lk.ijse.shopManagement.entity.Supplier;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public boolean save(Supplier entity) throws SQLException {
        String sql = "INSERT INTO supplier(supplier_id,supplier_name,supplier_company,supplier_contact) VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(sql,entity.getId(),entity.getSupplierName(),entity.getSupplierCompany(),entity.getContact());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        String sql = "UPDATE supplier SET supplier_name = ? ,supplier_company = ?, supplier_contact = ? WHERE supplier_id = ? ";
        return CrudUtil.execute(sql,entity.getSupplierName(),entity.getSupplierCompany(),entity.getContact(),entity.getId());
    }

    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<Supplier> data = new ArrayList<>();
        while (resultSet.next()){
            data.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }

    @Override
    public Supplier search(String id) throws SQLException {
        String sql ="SELECT * FROM supplier WHERE supplier_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, id);
        if (resultSet.next()){
            String supplier_id = resultSet.getString(1);
            String supplier_name = resultSet.getString(2);
            String supplier_company = resultSet.getString(3);
            String supplier_contact = resultSet.getString(4);
            return new Supplier(supplier_id,supplier_name,supplier_company,supplier_contact);
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supplier_id = ?";
        return CrudUtil.execute(sql,id);
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("supplier_id");
            int newCustomerId = Integer.parseInt(id.replace("S00-", "")) + 1;
            return String.format("S00-%03d", newCustomerId);
        } else {
            return "S00-001";
        }
    }

    @Override
    public List<String> getSupplierName() throws SQLException {
        String sql = "SELECT * FROM supplier";
        List<String> data = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()){
            data.add(resultSet.getString(2));
        }
        return data;
    }

    @Override
    public String getSupplierId(String supplier) throws SQLException {
        String sql = "SELECT supplier_id FROM supplier WHERE supplier_name=?";
        ResultSet resultSet = CrudUtil.execute(sql, supplier);
        if (resultSet.next()){
            String id = resultSet.getString(1);
            return id;
        }
        return null;
    }
}
