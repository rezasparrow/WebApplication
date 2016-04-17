package logic;

import exception.FieldRequiredException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.util.Date;

/**
 * Created by Dotin School1 on 4/17/2016.
 */
public class RealCustomer extends Customer {
    private String firstName;
    private String lastName;
    private String fatherName;
    private Date birthDay;
    private String NationalCode;

    public RealCustomer(String customerNumber, String firstName, String lastName, String fatherName, Date birthDay, String nationalCode)
            throws FieldRequiredException {

        validateCustomerNumber();
        if("".equals(firstName.trim())){
            throw new FieldRequiredException("first name is required");
        }
        if("".equals(customerNumber.trim())){
            throw new FieldRequiredException("customer number is required");
        }
        if("".equals(lastName.trim())){
            throw new FieldRequiredException("last name is required");
        }
        if("".equals(fatherName.trim())){
            throw new FieldRequiredException("father name is required");
        }
        if("".equals(nationalCode.trim())){
            throw new FieldRequiredException("national code is required");
        }
        validateNationalCode();
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.birthDay = birthDay;
        NationalCode = nationalCode;
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
    public void save(){
        throw new NotImplementedException();
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
