package logic;

import dataaccess.LegalCustomer;
import dataaccess.LegalCustomerCRUD;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LegalCustomerController {


    public static List<Pair<String, String>> save(String companyName, String barCode, String registrationDay) {
        List<Pair<String, String>> errors = validate(companyName, barCode, registrationDay);
        errors.addAll(validateBarCode(barCode));
        if (errors.size() == 0) {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            Date date;
            try {
                date = df.parse(registrationDay);
                LegalCustomer legalCustomer = new LegalCustomer(companyName, barCode, date);

                    legalCustomer.save();


            } catch (ParseException e) {
                e.printStackTrace();
                errors.add(new Pair<>("registrationDay", "registration day invalid format"));
            }catch (SQLException e) {
                e.printStackTrace();
                errors.add(new Pair<>("base", "error connect to database"));

            }
        }
        return errors;
    }

    public static List<Pair<String, String>> validate(String companyName, String barCode, String registrationDay) {
        List<Pair<String, String>> errors = new ArrayList<>();
        if (companyName.isEmpty()) {
            errors.add(new Pair<>("companyName", "company name required"));
        }
        if (barCode.isEmpty()) {
            errors.add(new Pair<>("barCode", "barcode required"));
        }
        if (registrationDay.isEmpty()) {
            errors.add(new Pair<>("registrationDay", "registration day required"));
        }


        return errors;
    }

    public static List<LegalCustomer> find(int id) {
        LegalCustomerCRUD legalCustomerCRUD = new LegalCustomerCRUD();
        return legalCustomerCRUD.findById(id);

    }

    public static List<Pair<String, String>> validateBarCode(String barCode) {
        List<Pair<String, String>> errors = new ArrayList<>();
        try {
            if (LegalCustomer.validateBarCodeUnique(barCode)) {
                errors.add(new Pair<>("barCode", "bar code must be unique"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            errors.add(new Pair<>("base", "Unknown error"));
        }
        return errors;
    }

    public static List<LegalCustomer> all() {
        LegalCustomerCRUD legalCustomerCRUD = new LegalCustomerCRUD();
        return legalCustomerCRUD.all();
    }

    public static List<Pair<String, String>> update(int id, String companyName, String barCode, String registrationDay) {
        List<Pair<String, String>> errors = validate(companyName, barCode, registrationDay);
        List<LegalCustomer> legalCustomers = find(id);
        if (legalCustomers.size() > 0) {
            LegalCustomer legalCustomerFounded = legalCustomers.get(0);
            if (!legalCustomerFounded.barCode.equals(barCode)) {
                errors.addAll(validateBarCode(barCode));
            }
            if (errors.size() == 0) {
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                Date date;
                try {
                    date = df.parse(registrationDay);
                    LegalCustomer legalCustomer = new LegalCustomer(companyName, barCode, date);
                    legalCustomer.id = id;
                    legalCustomer.update();

                } catch (ParseException e) {
                    e.printStackTrace();
                    errors.add(new Pair<>("base", "unknown error"));
                } catch (SQLException e) {
                    e.printStackTrace();
                    errors.add(new Pair<>("base", "unknown error"));
                }
            }
        }

        return errors;
    }

    public static List<Pair<String, String>> destroy(int id) {
        List<Pair<String, String>> errors = new ArrayList<>();
        List<LegalCustomer> legalCustomers = find(id);
        if (legalCustomers.size() > 0) {
            LegalCustomer legalCustomer = legalCustomers.get(0);

            try {
                legalCustomer.delete();
            } catch (SQLException e) {
                e.printStackTrace();
                errors.add(new Pair<>("base", "unknown error"));
            } catch (IOException e) {
                e.printStackTrace();
                errors.add(new Pair<>("base", "unknown error"));
            }
        } else {
            errors.add(new Pair<>("base", "legal customer can not find"));
        }
        return errors;
    }

    public static List<LegalCustomer> find(LegalCustomer legalCustomer){
        LegalCustomerCRUD legalCustomerCRUD = new LegalCustomerCRUD();
        return legalCustomerCRUD.all(legalCustomer);
    }
}
