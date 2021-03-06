package dataaccess;


import sun.util.resources.cldr.so.CurrencyNames_so;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RealCustomer extends Customer {

    public String firstName;
    public String lastName;
    public String fatherName;
    public Date birthDay;
    public String nationalCode;
    public Integer id;
    private RealCustomerCRUD realCustomerCRUD;

    public RealCustomer(String firstName, String lastName, String fatherName, Date birthDay, String nationalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.birthDay = birthDay;
        this.nationalCode = nationalCode;
        realCustomerCRUD = new RealCustomerCRUD();
        this.id = 0;
    }

    public RealCustomer(int id, String customerNumber, String firstName, String lastName, String fatherName, Date birthDay, String nationalCode) {
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.birthDay = birthDay;
        this.nationalCode = nationalCode;
        this.id = id;
    }



    public String getCustomerNumber() {
        return customerNumber;
    }


    public RealCustomer save() throws SQLException, IOException {
        return realCustomerCRUD.create(this);
    }


    public void update() throws SQLException {
        realCustomerCRUD.update(id, this);
    }

    public List<RealCustomer> getAll() {
        return realCustomerCRUD.all();
    }

    public void delete() throws IOException, SQLException {
        realCustomerCRUD.delete(id);

    }

    public static List<RealCustomer> findByCustomerNumber(String nationalCode) throws SQLException {
        RealCustomerCRUD realCustomerCRUD = new RealCustomerCRUD();
        return realCustomerCRUD.all(new RealCustomer("" , "" , ""  , null , nationalCode));
    }
}
