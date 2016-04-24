package logic;

import dataaccess.RealCustomer;
import dataaccess.RealCustomerCRUD;
import javafx.util.Pair;
import presentation.RealCustomerServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RealCustomerController implements Validatable {

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
            errors.add(new Pair<>("firstName", "first name required"));
        }
        if (lastName.isEmpty()) {
            errors.add(new Pair<>("lastName", "last name required"));
        }
        if (fatherName.isEmpty()) {
            errors.add(new Pair<>("fatherName", "father name required"));
        }
        if (nationalCode.isEmpty()) {
            errors.add(new Pair<>("nationalCode", "national code required"));
        } else if (nationalCode.length() != 10) {
            errors.add(new Pair<>("nationalCode", "size of national code must be 10"));
        }

        if (birthday.isEmpty()) {
            errors.add(new Pair<>("birthday", "birthday required"));
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            Date startDate;
            try {
                startDate = df.parse(birthday);

                df.format(startDate);
            } catch (ParseException e) {
                errors.add(new Pair<>("birthday", "invalid birthday format"));
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
            if (RealCustomer.validateNationalCode(nationalCode)) {
                errors.add(new Pair<>("nationalCode", "national code must be unique"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            errors.add(new Pair<>("base", "Unknown error"));
        } catch (IOException e) {
            e.printStackTrace();
            errors.add(new Pair<>("base", "Unknown error"));
        }
        return errors;
    }

    public boolean isValid() {
        return false;
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
                    errors.add(new Pair<>("base", "unknown error"));
                } catch (SQLException e) {
                    e.printStackTrace();
                    errors.add(new Pair<>("base", "unknown error"));
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
                errors.add(new Pair<String, String>("base" , "unknown error"));
            } catch (IOException e) {
                e.printStackTrace();
                errors.add(new Pair<String, String>("base" , "unknown error"));
            }
        }
        else{
            errors.add(new Pair<>("base", "real customer can not find"));
        }
        return errors;
    }
}
