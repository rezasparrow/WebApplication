package logic;

import dataaccess.LegalCustomer;
import dataaccess.LegalCustomerCRUD;
import javafx.util.Pair;
import logic.Bundle.LegalCustomerBundle;
import logic.Bundle.RealCustomerBundle;

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
                errors.add(new Pair<>("registrationDay", LegalCustomerBundle.REGISTRATION_DATE_INVALID_FORMAT));
            } catch (SQLException e) {
                e.printStackTrace();
                errors.add(new Pair<>("base", LegalCustomerBundle.DATA_BASE_ERROR_CONNECTION));

            }
        }
        return errors;
    }

    public static List<Pair<String, String>> validate(String companyName, String barCode, String registrationDay) {
        List<Pair<String, String>> errors = new ArrayList<>();
        if (companyName.isEmpty()) {
            errors.add(new Pair<>("companyName", LegalCustomerBundle.COMPANY_NAME_REQUIRED_REQUIRED));
        }
        if (barCode.isEmpty()) {
            errors.add(new Pair<>("barCode", LegalCustomerBundle.BAR_CODE_REQUIRED));
        }
        if (registrationDay.isEmpty()) {
            errors.add(new Pair<>("registrationDay", LegalCustomerBundle.REGISTRATION_DATE_REQUIRED));
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
            if (LegalCustomer.findByBarCode(barCode).size() > 0) {
                errors.add(new Pair<>("barCode", LegalCustomerBundle.BAR_CODE_IS_UNIQUE));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errors.add(new Pair<>("base", LegalCustomerBundle.DATA_BASE_ERROR_CONNECTION));
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
                errors.add(new Pair<>("base", LegalCustomerBundle.DATA_BASE_ERROR_CONNECTION));
            } catch (IOException e) {
                e.printStackTrace();
                errors.add(new Pair<>("base", LegalCustomerBundle.DATA_BASE_ERROR_CONNECTION));
            }
        } else {
            errors.add(new Pair<>("base", LegalCustomerBundle.CAN_NOT_FIND_LEGAL_CUSTOMER));
        }
        return errors;
    }

    public static List<LegalCustomer> find(LegalCustomer legalCustomer) {
        LegalCustomerCRUD legalCustomerCRUD = new LegalCustomerCRUD();
        return legalCustomerCRUD.all(legalCustomer);
    }
}
