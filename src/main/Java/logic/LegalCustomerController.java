package logic;

import dataaccess.LegalCustomer;
import dataaccess.LegalCustomerCRUD;
import dataaccess.RealCustomer;
import dataaccess.RealCustomerCRUD;

import java.util.List;

/**
 * Created by Dotin School1 on 4/19/2016.
 */
public class LegalCustomerController  {


    public static List<LegalCustomer> find(int id) {
        LegalCustomerCRUD legalCustomerCRUD = new LegalCustomerCRUD();
        return legalCustomerCRUD.findById(id);

    }
}
