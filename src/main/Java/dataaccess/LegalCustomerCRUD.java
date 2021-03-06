package dataaccess;


import javafx.util.Pair;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LegalCustomerCRUD implements CRUD<LegalCustomer> {

    private LegalCustomer getLegalCustomer(ResultSet resultSet) throws SQLException {
        String companyName = resultSet.getString("company_name");
        String barCode = resultSet.getString("bar_code");
        Date registrationDay = resultSet.getDate("registration_day");
        String customerNumber = resultSet.getString("customer_number");
        int id = resultSet.getInt("id");

        return new LegalCustomer(id, customerNumber, companyName, barCode, registrationDay);
    }

    @Override
    public LegalCustomer create(LegalCustomer customer) throws SQLException {
        LegalCustomer legalCustomer = customer;
        String insertCustomerSql = "insert into customer() values()";
        Connection dataBaseConnection = DataBaseManager.getConnection();
        dataBaseConnection.setAutoCommit(false);
        PreparedStatement statement = dataBaseConnection.prepareStatement(insertCustomerSql,
                Statement.RETURN_GENERATED_KEYS);
        int effectedRow = statement.executeUpdate();
        if (effectedRow == 0) {
            dataBaseConnection.rollback();
            throw new SQLException("create customer failed; no row effected");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                String customer_number = generatedKeys.getString(1);
                String insertLegalCustomerSql = "insert into legal_customer " +
                        "(bar_code , company_name , registration_day , customer_number)" +
                        " values(? ,? ,? ,? )";

                statement = dataBaseConnection.prepareStatement(insertLegalCustomerSql,
                        Statement.RETURN_GENERATED_KEYS);

                statement.setString(1, legalCustomer.barCode);
                statement.setString(2, legalCustomer.companyName);
                statement.setDate(3, new java.sql.Date(legalCustomer.registrationDay.getTime()));
                statement.setString(4, customer_number);
                statement.executeUpdate();
                dataBaseConnection.commit();
                return findByCustomerNumber(customer_number);
            } else {
                dataBaseConnection.rollback();
                throw new SQLException("Creating customer failed, no customer number obtained.");
            }
        }
    }

    private LegalCustomer findByCustomerNumber(String customer_number) throws SQLException {
        Connection dataBaseConnection = DataBaseManager.getConnection();
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement("select * from legal_customer where customer_number=?");
        preparedStatement.setString(1, customer_number);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String barCode = resultSet.getString("bar_code");
            String companyName = resultSet.getString("company_name");
            Date registrationDay = resultSet.getDate("registration_day");
            String customerNumber = resultSet.getString("customer_number");

            return new LegalCustomer(id, customerNumber, companyName, barCode, registrationDay);
        }
        return null;
    }

    @Override
    public void delete(int id) throws SQLException, IOException {

        Connection dataBaseConnection = DataBaseManager.getConnection();
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement("delete from legal_customer where customer_number=?");
        List<LegalCustomer> legalCustomers = findById(id);
        if (legalCustomers.size() > 0) {
            LegalCustomer legalCustomer = legalCustomers.get(0);
            preparedStatement.setString(1, legalCustomer.customerNumber);
            preparedStatement.execute();
        }

    }

    @Override
    public List<LegalCustomer> all() {
        List<LegalCustomer> legalCustomers = new ArrayList<>();
        try {
            Connection dataBaseConnection = DataBaseManager.getConnection();

            String sql = "select * from legal_customer";
            ResultSet resultSet = dataBaseConnection.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {

                legalCustomers.add(getLegalCustomer(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return legalCustomers;
    }

    @Override
    public List<LegalCustomer> findById(int id) {
        List<LegalCustomer> legalCustomers = new ArrayList<>();
        try {
            Connection dataBaseConnection = DataBaseManager.getConnection();
            String sql = "select * from legal_customer where id = ?";
            PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                legalCustomers.add(getLegalCustomer(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return legalCustomers;
    }

    @Override
    public List<LegalCustomer> all(LegalCustomer customer) {
        List<LegalCustomer> legalCustomers = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = generateFindQuery(customer);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String barCode = resultSet.getString("bar_code");
                String companyName = resultSet.getString("company_name");
                Date registrationDay = resultSet.getDate("registration_day");
                String customerNumber = resultSet.getString("customer_number");
                int id = resultSet.getInt("id");

                LegalCustomer legalCustomer = new LegalCustomer(id, customerNumber, companyName, barCode, registrationDay);
                legalCustomers.add(legalCustomer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return legalCustomers;
    }

    private PreparedStatement generateFindQuery(LegalCustomer customer) throws SQLException {
        String sql = "Select * from legal_customer ";
        List<Pair<String , String>> attributes = new ArrayList<>();
        if (!"".equals(customer.companyName.trim())) {
            attributes.add(new Pair<>("company_name", customer.companyName));
        }
        if (!"".equals(customer.barCode.trim())) {
            attributes.add(new Pair<>("bar_code", customer.barCode));
        }
        if (!"".equals(customer.customerNumber.trim())) {

            attributes.add(new Pair<>("customer_number", customer.customerNumber));
        }
        if(attributes.size() > 0){
            sql += "where ";
        }
        for (int i = 0 ; i < attributes.size() ; ++i){
            if(i != 0){
                sql += "and";
            }
            sql += attributes.get(i).getKey() + " = ? ";
        }
        PreparedStatement preparedStatement = DataBaseManager.getConnection().prepareStatement(sql);
        for (int i = 0 ; i < attributes.size() ; ++i){
            preparedStatement.setString(i+1 , attributes.get(i).getValue());
        }
        return preparedStatement;
    }


    @Override
    public List<LegalCustomer> update(int id, LegalCustomer customer) throws SQLException {
        Connection dataBaseConnection = DataBaseManager.getConnection();
        String sqlCommand = "update legal_customer set company_name='رضا' , bar_code=? where id=?";

        PreparedStatement statement = dataBaseConnection.prepareStatement(sqlCommand);
        statement.setInt(2, id);
        statement.setString(1, customer.barCode);
        statement.executeUpdate();


        return findById(id);
    }
}
