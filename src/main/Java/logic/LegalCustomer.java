package logic;

import exception.FieldRequiredException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Dotin School1 on 4/17/2016.
 */
public class LegalCustomer extends Customer{
    private String companyName;
    private String barCode;
    public LegalCustomer(String customerNumber, String companyName, String barCode)
            throws FieldRequiredException {

        validateCustomerNumber();
        if("".equals(companyName.trim())){
            throw new FieldRequiredException("company name is required");
        }
        if("".equals(customerNumber.trim())){
            throw new FieldRequiredException("customer number is required");
        }
        if("".equals(barCode.trim())){
            throw new FieldRequiredException("last name is required");
        }
        validateBarCode();
        this.customerNumber = customerNumber;
        this.barCode = barCode;
        this.companyName = companyName;
    }
    //TODO: write uniqueness of BarCode
    private void validateBarCode() {
        throw new NotImplementedException();
    }



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
