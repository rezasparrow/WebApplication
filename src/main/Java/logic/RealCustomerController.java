package logic;

import dataaccess.RealCustomer;
import dataaccess.RealCustomerCRUD;

import javafx.util.Pair;
import logic.Bundle.RealCustomerBundle;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RealCustomerController  {

    public static List<Pair<String, String>> save(String firstName, String lastName, String fatherName, String nationalCode, String birthday) {
        List<Pair<String, String>> errors = validate(firstName, lastName, fatherName, nationalCode, birthday);
        errors.addAll(validateNationalCode(nationalCode));
        if (errors.size() == 0) {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            Date date;
            try {
                date = df.parse(birthday);
                RealCustomer realCustomer = new RealCustomer(firstName, lastName, fatherName, date, nationalCode);

                realCustomer.save();

            } catch (ParseException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                errors.add(new Pair<String, String>("base", "unknown error"));
            }
        }
        return errors;
    }

    public static List<Pair<String, String>> validate(String firstName, String lastName, String fatherName, String nationalCode, String birthday) {
        List<Pair<String, String>> errors = new ArrayList<>();
        if (firstName.isEmpty()) {
            errors.add(new Pair<>("firstName", RealCustomerBundle.FIRST_NAME_REQUIRED));
        }
        if (lastName.isEmpty()) {
            errors.add(new Pair<>("lastName", RealCustomerBundle.LAST_NAME_REQUIRED));
        }
        if (fatherName.isEmpty()) {
            errors.add(new Pair<>("fatherName", RealCustomerBundle.FATHER_NAME_REQUIRED));
        }
        if (nationalCode.isEmpty()) {
            errors.add(new Pair<>("nationalCode", RealCustomerBundle.NATIONAL_CODE_REQUIRED));
        } else if (nationalCode.length() != 10) {
            errors.add(new Pair<>("nationalCode", RealCustomerBundle.NATIONAL_CODE_LENGTH));
        }

        if (birthday.isEmpty()) {
            errors.add(new Pair<>("birthday", RealCustomerBundle.BIRTHDAY_REQUIRED));
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            Date startDate;
            try {
                startDate = df.parse(birthday);

                df.format(startDate);
            } catch (ParseException e) {
                errors.add(new Pair<>("birthday", RealCustomerBundle.BIRTHDAY_INVALID_FORMAT));
            }
        }
        return errors;
    }

    public static List<RealCustomer> find(int id) {
        RealCustomerCRUD realCustomerCRUD = new RealCustomerCRUD();
        return realCustomerCRUD.findById(id);

    }

    public static List<Pair<String, String>> validateNationalCode(String nationalCode) {
        List<Pair<String, String>> errors = new ArrayList<>();
        try {
            if (RealCustomer.findByCustomerNumber(nationalCode).size() > 0) {
                errors.add(new Pair<>("nationalCode", RealCustomerBundle.NATIONAL_CODE_IS_UNIQUE));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            errors.add(new Pair<>("base",  RealCustomerBundle.DATA_BASE_ERROR_CONNECTION));
        }
        return errors;
    }

    public List<RealCustomer> all() {
        RealCustomerCRUD realCustomerCRUD = new RealCustomerCRUD();
        return realCustomerCRUD.all();
    }

    public static List<Pair<String, String>> update(int id, String firstName, String lastName, String fatherName, String nationalCode, String birthday) {
        List<Pair<String, String>> errors = validate(firstName, lastName, fatherName, nationalCode, birthday);
        List<RealCustomer> realCustomers = find(id);
        if (realCustomers.size() > 0) {
            RealCustomer realCustomerFounded = realCustomers.get(0);
            if(!realCustomerFounded.nationalCode.equals(nationalCode)){
                errors.addAll(validateNationalCode(nationalCode));
            }
            if (errors.size() == 0) {
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                Date date;
                try {
                    date = df.parse(birthday);
                    RealCustomer realCustomer = new RealCustomer(firstName, lastName, fatherName, date, nationalCode);
                    realCustomer.id = id;
                    realCustomer.update();

                } catch (ParseException e) {
                    e.printStackTrace();
                    errors.add(new Pair<>("birthday", RealCustomerBundle.BIRTHDAY_INVALID_FORMAT));
                } catch (SQLException e) {
                    e.printStackTrace();
                    errors.add(new Pair<>("base", RealCustomerBundle.DATA_BASE_ERROR_CONNECTION));
                }
            }
        }

        return errors;
    }

    public static List<Pair<String, String>> destroy(int id)  {
        List<Pair<String, String>>  errors = new ArrayList<>();
        List<RealCustomer> realCustomers = find(id);
        if(realCustomers.size() > 0){
            RealCustomerCRUD realCustomerCRUD = new RealCustomerCRUD();
            try {
                realCustomerCRUD.delete(id);
            } catch (SQLException e) {
                e.printStackTrace();
                errors.add(new Pair<>("base",RealCustomerBundle.DATA_BASE_ERROR_CONNECTION));
            }
        }
        else{
            errors.add(new Pair<>("base",RealCustomerBundle.CAN_NOT_FIND_REAL_CUSTOMER));
        }
        return errors;
    }

    public static List<RealCustomer> find(RealCustomer realCustomer){
        RealCustomerCRUD realCustomerCRUD= new RealCustomerCRUD();
        return realCustomerCRUD.all(realCustomer);
    }
}
