package dataaccess;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class RealCustomerCRUD implements CRUD {

    @Override
    public Customer create(Customer customer) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    private String generateLikeQuery(RealCustomer customer) {
        String sql = "Select * from real_customer ";
        String likeQuerySection = "";
        if (!"".equals(customer.firstName.trim())) {
            if (likeQuerySection.length() > 0) {
                likeQuerySection += " and";
            }
            likeQuerySection += String.format(" first_name like '%%s%'", customer.firstName);
        }
        if (!"".equals(customer.lastName.trim())) {
            if (likeQuerySection.length() > 0) {
                likeQuerySection += "and";
            }
            likeQuerySection += String.format(" last_name like '%%s%'", customer.lastName);
        }
        if (!"".equals(customer.nationalCode.trim())) {
            if (likeQuerySection.length() > 0) {
                likeQuerySection += " and";
            }
            likeQuerySection += String.format(" national_code like '%%s%'", customer.nationalCode);
        }
        if (!"".equals(customer.customerNumber.trim())) {
            if (likeQuerySection.length() > 0) {
                likeQuerySection += "and";
            }
            likeQuerySection += String.format(" customer_number like '%%s%'", customer.customerNumber);
        }
        return sql + likeQuerySection;
    }

    @Override
    public List<Customer> read(Customer customer) {
        List<Customer> realCustomers = new ArrayList<Customer>();
        try {
            try (DataBaseManager dataBaseManager = new DataBaseManager();) {
                String sql = generateLikeQuery((RealCustomer) customer);
                ResultSet resultSet = dataBaseManager.statement.executeQuery(sql);
                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String fatherName = resultSet.getString("father_name");
                    Date birthDay = resultSet.getDate("birthday");
                    String nationalCode = resultSet.getString("national_code");
                    String customerNumber = resultSet.getString("customer_number");
                    int id = resultSet.getInt("id");

                    RealCustomer realCustomer = new RealCustomer(id, customerNumber, firstName, lastName, fatherName, birthDay, nationalCode);
                    realCustomers.add(realCustomer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return realCustomers;
    }

    @Override
    public Customer update(int id, Customer customer) {
        return null;
    }
}
