package lk.ijse.shopManagement.bo.custom.Impl;

import lk.ijse.shopManagement.bo.custom.EmployeeBO;
import lk.ijse.shopManagement.dao.DAOFactory;
import lk.ijse.shopManagement.dao.custom.EmployeeDAO;
import lk.ijse.shopManagement.dto.EmployeeDTO;
import lk.ijse.shopManagement.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.save(new Employee(employeeDTO.getId(), employeeDTO.getName(), employeeDTO.getAddress(), employeeDTO.getJobRoll(), employeeDTO.getContact(), employeeDTO.getSalary()));
    }

    @Override
    public List<EmployeeDTO> getAllEployee() throws SQLException {
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        ArrayList<Employee> employees = employeeDAO.getAll();
        for (Employee emp:employees) {
            employeeDTOS.add(new EmployeeDTO(emp.getId(), emp.getName(), emp.getAddress(), emp.getContact(), emp.getJobRoll(), emp.getSalary()));
        }
        return employeeDTOS;
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.update(new Employee(employeeDTO.getId(), employeeDTO.getName(), employeeDTO.getAddress(), employeeDTO.getJobRoll(), employeeDTO.getContact(), employeeDTO.getSalary()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException {
        return employeeDAO.delete(id);
    }

    @Override
    public EmployeeDTO searchEmployee(String id) throws SQLException {
        Employee employee = employeeDAO.search(id);
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getAddress(), employee.getContact(), employee.getJobRoll(), employee.getSalary());
    }

    @Override
    public String generateNextEmployeeId() throws SQLException {
        return employeeDAO.generateNextId();
    }
}
