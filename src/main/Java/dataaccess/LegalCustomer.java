package dataaccess;

import exception.FieldRequiredException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Dotin School1 on 4/17/2016.
 */
public class LegalCustomer extends Customer{
    public int id;
    public String companyName;
    public String barCode;
    public Date registrationDay;

    public LegalCustomer(int id, String customerNumber, String companyName, String barCode, Date registrationDay) {
        this.id = id;
        this.customerNumber = customerNumber;
        this.companyName = companyName;
        this.barCode = barCode;
        this.registrationDay = registrationDay;
    }

    //TODO: write uniqueness of BarCode
    private boolean validateBarCodeUnique(String barCode) throws SQLException, IOException {
        try(
                DataBaseManager dataBaseManager = new DataBaseManager()
        ) {
            ResultSet resultSet =  dataBaseManager.statement.executeQuery("select count(*) from legal_customer where bar_code='"+barCode.trim()+"'");
            if( resultSet.next()){
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
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
