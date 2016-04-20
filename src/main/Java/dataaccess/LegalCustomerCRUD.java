package dataaccess;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LegalCustomerCRUD implements CRUD <LegalCustomer> {
    @Override
    public LegalCustomer create(LegalCustomer customer) throws SQLException, ClassNotFoundException {
        LegalCustomer legalCustomer = (LegalCustomer) customer;
        DataBaseManager dataBaseManager = new DataBaseManager();
        String insertCustomerSql = "insert into customer() values()";
        PreparedStatement statement = dataBaseManager.connection.prepareStatement(insertCustomerSql,
                Statement.RETURN_GENERATED_KEYS);

        int effectedRow = statement.executeUpdate();
        if (effectedRow == 0) {
            throw new SQLException("create customer failed; no row effected");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                Integer customer_number = generatedKeys.getInt(1);
                String insertRealCustomerSql = "insert into real_customer" +
                        "(bar_code , company_name , registeration_day , customer_number)" +
                        " values(? ,? ,? ,? )";

                statement = dataBaseManager.connection.prepareStatement(insertRealCustomerSql,
                        Statement.RETURN_GENERATED_KEYS);

                statement.setString(1, legalCustomer.barCode);
                statement.setString(2, legalCustomer.companyName);
                statement.setDate(3, new java.sql.Date(legalCustomer.registrationDay.getTime()));
                statement.setInt(4, customer_number);

                dataBaseManager.connection.commit();
                return findByCustomerNumber(customer_number);
            } else {
                throw new SQLException("Creating customer failed, no customer number obtained.");
            }
        }
    }

    private LegalCustomer findByCustomerNumber(Integer customer_number) throws SQLException, ClassNotFoundException {
        DataBaseManager dataBaseManager = new DataBaseManager();
        ResultSet resultSet = dataBaseManager.statement.executeQuery("select * from legal_customer where customer_number=" + customer_number);
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
    public void delete(int id) {
        String sql = "delete from legal_customer where id=" + id;
        try {
            try (DataBaseManager dataBaseManager = new DataBaseManager();) {
                dataBaseManager.statement.executeQuery(sql);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String generateLikeQuery(LegalCustomer customer) {
        String sql = "Select * from real_customer ";
        String likeQuerySection = "";
        if (!"".equals(customer.barCode.trim())) {
            if (likeQuerySection.length() > 0) {
                likeQuerySection += " and";
            }
            likeQuerySection += String.format(" bar_code like '%%s%'", customer.barCode);
        }
        if (!"".equals(customer.companyName.trim())) {
            if (likeQuerySection.length() > 0) {
                likeQuerySection += "and";
            }
            likeQuerySection += String.format(" company_name like '%%s%'", customer.companyName);
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
    public List<LegalCustomer> read(LegalCustomer customer) {
        List<LegalCustomer> realCustomers = new ArrayList<>();
        try {
            try (DataBaseManager dataBaseManager = new DataBaseManager();) {
                String sql = generateLikeQuery( customer);
                ResultSet resultSet = dataBaseManager.statement.executeQuery(sql);
                while (resultSet.next()) {
                    String barCode = resultSet.getString("bar_code");
                    String companyName = resultSet.getString("company_name");
                    Date registrationDay = resultSet.getDate("registration_day");
                    String customerNumber = resultSet.getString("customer_number");
                    int id = resultSet.getInt("id");

                    LegalCustomer realCustomer = new LegalCustomer(id, customerNumber , companyName, barCode, registrationDay);
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
    public LegalCustomer update(int id, LegalCustomer customer) throws SQLException, ClassNotFoundException {
        DataBaseManager dataBaseManager = new DataBaseManager();
        String sqlCommand = "update legal_customer set company_name=? , bar_code=? where id=?";

        PreparedStatement statement = dataBaseManager.connection.prepareStatement(sqlCommand);
        statement.setInt(3 , id);
        statement.setString(2, customer.companyName);
        statement.setString(1, customer.barCode);
        statement.executeUpdate();

        dataBaseManager.connection.commit();

        return findByCustomerNumber(id);
    }
}
