package lk.ijse.shopManagement.dao.custom.Impl;

import lk.ijse.shopManagement.dao.custom.EmployeeDAO;
import lk.ijse.shopManagement.entity.Employee;
import lk.ijse.shopManagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean save(Employee entity) throws SQLException {
        String sql = "INSERT INTO employee(employee_id,employee_name,employee_address,employee_jobRoll,employee_contact,employee_salary) VALUES (?,?,?,?,?,?)";
        return CrudUtil.execute(sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getJobRoll(),entity.getContact(),entity.getSalary());
    }

    @Override
    public boolean update(Employee entity) throws SQLException {
        String sql = "UPDATE employee SET employee_name = ?, address = ?, contact_number = ?, job_roll = ?, salary = ? WHERE employee_id = ? ";
        return CrudUtil.execute(sql,entity.getName(),entity.getAddress(),entity.getContact(),entity.getJobRoll(),entity.getSalary(),entity.getId());
    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";
        ArrayList<Employee> data = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()){
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
            ));
        }
        return data;
    }

    @Override
    public Employee search(String id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);
        while (resultSet.next()){
            String employee_id = resultSet.getString(1);
            String employee_name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact_number = resultSet.getString(4);
            String job_roll = resultSet.getString(5);
            double salary = resultSet.getDouble(6);
            return new Employee(employee_id,employee_name,address,contact_number,job_roll,salary);
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM employee WHERE employee_id = ?";
        return CrudUtil.execute(sql,id);
    }

    @Override
    public String generateNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("employee_id");
            int newCustomerId = Integer.parseInt(id.replace("E00-", "")) + 1;
            return String.format("E00-%03d", newCustomerId);
        } else {
            return "E00-001";
        }
    }
}
