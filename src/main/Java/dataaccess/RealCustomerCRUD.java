package dataaccess;

import org.w3c.dom.css.CSSUnknownRule;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RealCustomerCRUD implements CRUD<RealCustomer> {

    @Override
    public RealCustomer create(RealCustomer customer) throws SQLException, IOException {
        try (DataBaseManager dataBaseManager = new DataBaseManager()) {

            String insertCustomerSqlCommand = "insert into customer() values()";
            PreparedStatement statement = dataBaseManager.connection.prepareStatement(insertCustomerSqlCommand,
                    Statement.RETURN_GENERATED_KEYS);

            int effectedRow = statement.executeUpdate();
            if (effectedRow == 0) {
                throw new SQLException("create customer failed; no row effected");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Integer customer_number = generatedKeys.getInt(1);
                    String insertRealCustomerSql = "insert into real_customer" +
                            "(first_name , last_name , father_name , birthday , national_code ,customer_number)" +
                            " values(? ,? ,? ,? ,?,? )";

                    statement = dataBaseManager.connection.prepareStatement(insertRealCustomerSql,
                            Statement.RETURN_GENERATED_KEYS);

                    statement.setString(1, customer.firstName);
                    statement.setString(2, customer.lastName);
                    statement.setString(3, customer.fatherName);
                    statement.setDate(4, new java.sql.Date(customer.birthDay.getTime()));
                    statement.setString(5, customer.nationalCode);
                    statement.setInt(6, customer_number);

                    statement.executeUpdate();
                    dataBaseManager.connection.commit();
                    return findByCustomerNumber(customer_number);
                } else {
                    throw new SQLException("Creating customer failed, no customer number obtained.");
                }
            }

        }
    }

    private RealCustomer findByCustomerNumber(Integer customer_number) throws SQLException {
        DataBaseManager dataBaseManager = new DataBaseManager();
        ResultSet resultSet = dataBaseManager.statement.executeQuery("select * from legal_customer where customer_number=" + customer_number);
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String fatherName = resultSet.getString("father_name");
            String nationalCode = resultSet.getString("national_code");
            Date birthday = resultSet.getDate("birthday");
            Integer customerNumber = resultSet.getInt("customer_number");

            return new RealCustomer(id, customerNumber.toString(), firstName, lastName, fatherName, birthday, nationalCode);
        }
        return null;
    }

    @Override
    public void delete(int id) throws SQLException, IOException {
        String sql = "delete from real_customer where id=" + id;

        try (DataBaseManager dataBaseManager = new DataBaseManager();) {
            dataBaseManager.statement.executeQuery(sql);
        }

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
            likeQuerySection = likeQuerySection + " last_name like '%" + customer.lastName + "%'";
        }
        if (!"".equals(customer.nationalCode.trim())) {
            if (likeQuerySection.length() > 0) {
                likeQuerySection += " and";
            }
            likeQuerySection += " national_code like '%" + customer.nationalCode + "%'";
        }
        if (!"".equals(customer.customerNumber.trim())) {
            if (likeQuerySection.length() > 0) {
                likeQuerySection += "and";
            }
            likeQuerySection += " customer_number like '%" + customer.customerNumber + "%'";
        }
        return sql + likeQuerySection;
    }

    private RealCustomer getRealCustomer(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String fatherName = resultSet.getString("father_name");
        Date birthDay = resultSet.getDate("birthday");
        String nationalCode = resultSet.getString("national_code");
        String customerNumber = resultSet.getString("customer_number");
        int id = resultSet.getInt("id");

        return new RealCustomer(id, customerNumber, firstName, lastName, fatherName, birthDay, nationalCode);
    }
    @Override
    public List<RealCustomer> all(RealCustomer customer) {
        List<RealCustomer> realCustomers = new ArrayList<>();
        try {
            try (DataBaseManager dataBaseManager = new DataBaseManager()) {
                String sql = generateLikeQuery(customer);
                ResultSet resultSet = dataBaseManager.statement.executeQuery(sql);
                while (resultSet.next()) {

                    realCustomers.add(getRealCustomer(resultSet));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realCustomers;
    }

    @Override
    public RealCustomer update(int id, RealCustomer customer) throws SQLException {
        DataBaseManager dataBaseManager = new DataBaseManager();
        String sqlCommand = "update first_name set last_name=? , national_code=? , father_name=? , birthday =? where id=?";

        PreparedStatement statement = dataBaseManager.connection.prepareStatement(sqlCommand);
        statement.setString(1, customer.firstName);
        statement.setString(2, customer.lastName);
        statement.setString(3, customer.nationalCode);
        statement.setString(4, customer.fatherName);
        statement.setDate(5, new java.sql.Date(customer.birthDay.getTime()));
        statement.executeUpdate();

        dataBaseManager.connection.commit();

        return findByCustomerNumber(id);
    }

    @Override
    public  List<RealCustomer> all(){
        List<RealCustomer> realCustomers = new ArrayList<>();
        try {
            try (DataBaseManager dataBaseManager = new DataBaseManager()) {
                String sql = "select * from real_customer";
                ResultSet resultSet = dataBaseManager.statement.executeQuery(sql);
                while (resultSet.next()) {

                    realCustomers.add(getRealCustomer(resultSet));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realCustomers;
    }
}
