package dataaccess;

import exception.FieldRequiredException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    public RealCustomer(int id , String customerNumber, String firstName, String lastName, String fatherName, Date birthDay, String nationalCode)
             {

//        validateCustomerNumber();
//        if("".equals(firstName.trim())){
//            throw new FieldRequiredException("first name is required");
//        }
//        if("".equals(customerNumber.trim())){
//            throw new FieldRequiredException("customer number is required");
//        }
//        if("".equals(lastName.trim())){
//            throw new FieldRequiredException("last name is required");
//        }
//        if("".equals(fatherName.trim())){
//            throw new FieldRequiredException("father name is required");
//        }
//        if("".equals(nationalCode.trim())){
//            throw new FieldRequiredException("national code is required");
//        }
        validateNationalCode();
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.birthDay = birthDay;
        this.nationalCode = nationalCode;
    }
//TODO: write uniqueness of national code
    private void validateNationalCode() {
        throw new NotImplementedException();
    }


    public void validate(){

    };
//ToDo : validate uniqueness of customer number
    private boolean validateCustomerNumber(){
        throw new NotImplementedException();
    }
//    // TODO: 4/17/2016 Save Customer
    public RealCustomer save() throws SQLException {
        return realCustomerCRUD.create(this);
    }


//    // TODO: 4/17/2016 edit Customer
    public void edit(){
        throw new NotImplementedException();

    }

//    // TODO: 4/17/2016 getAll Customer
    public void getAll(){
        throw new NotImplementedException();

    }

//    // TODO: 4/17/2016 delete Customer
    public void delete(){
        throw new NotImplementedException();

    }

}
