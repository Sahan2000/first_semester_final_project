package lk.ijse.shopManagement.bo.custom.Impl;

import lk.ijse.shopManagement.bo.custom.CustomerBO;
import lk.ijse.shopManagement.dao.DAOFactory;
import lk.ijse.shopManagement.dao.custom.CustomerDAO;
import lk.ijse.shopManagement.dto.CustomerDTO;
import lk.ijse.shopManagement.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.save(new Customer(customerDTO.getId(), customerDTO.getName(),customerDTO.getAddress(),customerDTO.getContact(), customerDTO.getNic()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getContact(), customerDTO.getAddress(), customerDTO.getNic()));
    }

    @Override
    public List<CustomerDTO> getAllCustomer() throws SQLException {
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        ArrayList<Customer> customers = customerDAO.getAll();
        for (Customer cust:customers) {
            customerDTOS.add(new CustomerDTO(cust.getId(), cust.getName(), cust.getAddress(), cust.getContact(), cust.getNIC()));
        }
        return customerDTOS;
    }

    @Override
    public CustomerDTO searchCustomerId(String id) throws SQLException {
        Customer customer = customerDAO.search(id);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getContact(), customer.getAddress(), customer.getNIC());
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateNextCustomerId() throws SQLException {
        return customerDAO.generateNextId();
    }

    @Override
    public CustomerDTO searchCustomerNIC(String nic) throws SQLException {
        Customer customer = customerDAO.searchCustomerNIC(nic);
        return new CustomerDTO(customer.getId(),customer.getName(),customer.getContact(),customer.getAddress(),customer.getNIC());
    }
}
