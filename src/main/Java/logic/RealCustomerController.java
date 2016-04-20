package logic;

import dataaccess.RealCustomer;
import javafx.util.Pair;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Dotin School1 on 4/19/2016.
 */
public class RealCustomerController implements Validatable {

    public void save(String firstName, String lastName, String fatherName, String nationalCode, String birthday) {
        List<Pair<String, String>> errors = validate(firstName, lastName, fatherName, nationalCode, birthday);
        if(errors.size() == 0 ){
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date date;
            try {
                date = df.parse(birthday);
                RealCustomer realCustomer = new RealCustomer(firstName, lastName, fatherName, date, nationalCode);
                realCustomer.save();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Pair<String, String>> validate(String firstName, String lastName, String fatherName, String nationalCode, String birthday) {
        List<Pair<String, String>> errors = new ArrayList<>();
        if (firstName.isEmpty()) {
            errors.add(new Pair<String, String>("firstName", "first name required"));
        }
        if (lastName.isEmpty()) {
            errors.add(new Pair<String, String>("lastName", "last name required"));
        }
        if (fatherName.isEmpty()) {
            errors.add(new Pair<String, String>("fatherName", "father name required"));
        }
        if (nationalCode.isEmpty()) {
            errors.add(new Pair<String, String>("nationalCode", "national code required"));
        }
//        TODO check uniqueness of national code

        if(birthday.isEmpty()){
            errors.add(new Pair<String, String>("birthday" , "birthday required"));
        }
        else{
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date startDate;
            try {
                startDate = df.parse(birthday);

                String newDateString = df.format(startDate);
            } catch (ParseException e) {
                errors.add(new Pair<String, String>("birthday" , "invalid birthday format"));
            }
        }
        return errors;
    }

    public boolean isValid() {
        return false;
    }

    //ToDo add validation for all data
}
