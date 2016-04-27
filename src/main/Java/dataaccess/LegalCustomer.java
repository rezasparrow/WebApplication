package dataaccess;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Dotin School1 on 4/17/2016.
 */
public class LegalCustomer extends Customer {
    public int id;
    public String companyName;
    public String barCode;
    public Date registrationDay;
    private LegalCustomerCRUD legalCustomerCRUD;

    public LegalCustomer(int id, String customerNumber, String companyName, String barCode, Date registrationDay) {
        this.id = id;
        this.customerNumber = customerNumber;
        this.companyName = companyName;
        this.barCode = barCode;
        this.registrationDay = registrationDay;
        legalCustomerCRUD = new LegalCustomerCRUD();
    }


    public LegalCustomer(String companyName, String barCode, Date registrationDay) {
        this.companyName = companyName;
        this.barCode = barCode;
        this.registrationDay = registrationDay;
        this.legalCustomerCRUD = new LegalCustomerCRUD();
    }

    public void update() throws SQLException {
        legalCustomerCRUD.update(id, this);
    }

    public void save() throws SQLException {
        legalCustomerCRUD.create(this);
    }

    public void getAll() throws SQLException {
        legalCustomerCRUD.all();

    }

    public void delete() throws IOException, SQLException {
        legalCustomerCRUD.delete(id);
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public static List<LegalCustomer> findByBarCode(String barCode) throws SQLException {
        LegalCustomerCRUD legalCustomerCRUD = new LegalCustomerCRUD();
        return legalCustomerCRUD.all(new LegalCustomer("" , barCode , null));
    }
}
