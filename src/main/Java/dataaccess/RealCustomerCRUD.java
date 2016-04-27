package dataaccess;

import org.w3c.dom.css.CSSUnknownRule;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RealCustomerCRUD implements CRUD<RealCustomer> {

    @Override
    public RealCustomer create(RealCustomer customer) throws SQLException, IOException {
        Connection dataBaseConnection = DataBaseManager.getConnection();
        dataBaseConnection.setAutoCommit(false);
        String insertCustomerSqlCommand = "insert into customer() values()";
        PreparedStatement statement = dataBaseConnection.prepareStatement(insertCustomerSqlCommand,
                Statement.RETURN_GENERATED_KEYS);

        int effectedRow = statement.executeUpdate();
        if (effectedRow == 0) {
            dataBaseConnection.rollback();
            throw new SQLException("create customer failed; no row effected");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                String customer_number = generatedKeys.getString(1);
                String insertRealCustomerSql = "insert into real_customer" +
                        "(first_name , last_name , father_name , birthday , national_code ,customer_number)" +
                        " values(? ,? ,? ,? ,?,? )";

                statement = dataBaseConnection.prepareStatement(insertRealCustomerSql,
                        Statement.RETURN_GENERATED_KEYS);

                statement.setString(1, customer.firstName);
                statement.setString(2, customer.lastName);
                statement.setString(3, customer.fatherName);
                statement.setDate(4, new java.sql.Date(customer.birthDay.getTime()));
                statement.setString(5, customer.nationalCode);
                statement.setString(6, customer_number);

                statement.executeUpdate();
                dataBaseConnection.commit();
                return findByCustomerNumber(customer_number);
            } else {
                dataBaseConnection.rollback();
                throw new SQLException("Creating customer failed, no customer number obtained.");
            }
        }


    }

    private RealCustomer findByCustomerNumber(String customer_number) throws SQLException {
        Connection dataBaseConnection = DataBaseManager.getConnection();
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement("select * from legal_customer where customer_number=?");
        preparedStatement.setString(1, customer_number);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String fatherName = resultSet.getString("father_name");
            String nationalCode = resultSet.getString("national_code");
            Date birthday = resultSet.getDate("birthday");
            String customerNumber = resultSet.getString("customer_number");

            return new RealCustomer(id, customerNumber, firstName, lastName, fatherName, birthday, nationalCode);
        }
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from customer where customer_number=?";

        Connection dataBaseConnection = DataBaseManager.getConnection();
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(sql);
        List<RealCustomer > realCustomers = findById(id);
        if(realCustomers.size() > 0){
            RealCustomer realCustomer = realCustomers.get(0);
            preparedStatement.setString(1, realCustomer.customerNumber);
            preparedStatement.execute();
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
            Connection dataBaseConnection = DataBaseManager.getConnection();
            PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(
                    "SELECT * FROM  real_customer where first_name like ? and last_name like ? and national_code like ? and customer_number like ?");
            preparedStatement.setString(1 ,"%" + customer.firstName + "%");
            preparedStatement.setString(2 , "%" + customer.lastName + "%");
            preparedStatement.setString(3 , "%" + customer.nationalCode + "%");
            preparedStatement.setString(4 , "%" + customer.customerNumber + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                realCustomers.add(getRealCustomer(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realCustomers;
    }

    @Override
    public List<RealCustomer> update(int id, RealCustomer customer) throws SQLException {
        Connection dataBaseConnection = DataBaseManager.getConnection();
        String sqlCommand = "update real_customer set first_name = ? , last_name=? , national_code=? , father_name=? , birthday =? where id=?";

        PreparedStatement statement = dataBaseConnection.prepareStatement(sqlCommand);
        statement.setString(1, customer.firstName);
        statement.setString(2, customer.lastName);
        statement.setString(3, customer.nationalCode);
        statement.setString(4, customer.fatherName);
        statement.setDate(5, new java.sql.Date(customer.birthDay.getTime()));
        statement.setInt(6 , id);
        statement.executeUpdate();

        return findById(id);
    }

    @Override
    public List<RealCustomer> all() {
        List<RealCustomer> realCustomers = new ArrayList<>();
        try {
            Connection dataBaseConnection = DataBaseManager.getConnection();
            String sql = "select * from real_customer";
            ResultSet resultSet = dataBaseConnection.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {

                realCustomers.add(getRealCustomer(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realCustomers;
    }

    @Override
    public List<RealCustomer> findById(int id) {
        List<RealCustomer> realCustomers = new ArrayList<>();
        try {
            Connection dataBaseConnection = DataBaseManager.getConnection();
            String sql = "select * from real_customer where id = ?";
            PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                realCustomers.add(getRealCustomer(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realCustomers;
    }
}
