package dataaccess;

import exception.FieldRequiredException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


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

    public static boolean validateNationalCode(String nationalCode) throws SQLException, IOException {

        try (
                DataBaseManager dataBaseManager = new DataBaseManager()
        ) {
            String sql = "select count(*) from real_customer where national_code='" + nationalCode.trim() + "'";
            ResultSet resultSet = dataBaseManager.statement.executeQuery(sql);
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

    public String getCustomerNumber(){
        return customerNumber;
    }

    public void validate() {

    }


    //ToDo : validate uniqueness of customer number
    private boolean validateCustomerNumber() {
        throw new NotImplementedException();
    }

    //    // TODO: 4/17/2016 Save Customer
    public RealCustomer save() throws SQLException, IOException {
        return realCustomerCRUD.create(this);
    }


    //    // TODO: 4/17/2016 update Customer
    public void update() throws SQLException {
        realCustomerCRUD.update(id , this);
    }

    //    // TODO: 4/17/2016 getAll Customer
    public void getAll() {
        throw new NotImplementedException();

    }

    //    // TODO: 4/17/2016 delete Customer
    public void delete() {
        throw new NotImplementedException();

    }

}
