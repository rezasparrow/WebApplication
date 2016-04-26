package presentation;

import dataaccess.Customer;
import dataaccess.LegalCustomer;
import dataaccess.RealCustomer;
import html.FormElement;
import html.HtmlGenerator;
import javafx.util.Pair;
import logic.LegalCustomerController;
import logic.RealCustomerController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class RealCustomerServlet extends HttpServlet {


    private void redirectToRealCustomer(HttpServletResponse response){
        response.setStatus(response.SC_MOVED_PERMANENTLY);
        response.setHeader("Location" , "/RealCustomer");
    }

    private String getError(String key, List<Pair<String, String>> errors) {
        String error = "";
        for (Pair<String, String> err : errors) {
            if (err.getKey().equalsIgnoreCase(key)) {
                if (error.length() > 0) {
                    error += err.getValue() + "<br>";
                } else {
                    error += err.getValue();
                }
            }
        }
        return error;
    }

    private String partialForm(List<Pair<String, String>> errors, String action, RealCustomer customer) {
        List<FormElement> formElements = new ArrayList<FormElement>();
        String birthday = "" ;
        if(customer.birthDay == null){
            birthday = "";
        }else{
            birthday = customer.birthDay.toString();
        }
        formElements.add(new FormElement("text", "firstName", "نام", customer.firstName, getError("firstName", errors)));
        formElements.add(new FormElement("text", "lastName", "نام خانوادگی", customer.lastName, getError("lastName", errors)));
        formElements.add(new FormElement("text", "fatherName", "نام پدر", customer.fatherName, getError("fatherName", errors)));
        formElements.add(new FormElement("text", "nationalCode", "کد ملی", customer.nationalCode, getError("nationalCode", errors)));
        formElements.add(new FormElement("date", "birthday", "تاریخ تولد",birthday, getError("birthday", errors)));

        return HtmlGenerator.generateForm(formElements, action);
    }

    private void newRealCustomer(HttpServletRequest request, HttpServletResponse response, List<Pair<String, String>> errors) throws IOException {
        PrintWriter printWriter = response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("تعریف مشتری حقوقی");
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");

        Date birthday = null;
        try {
            String date = request.getParameter("birthday") ;
            if(date != null && !date.isEmpty()) {
                birthday = df.parse(date);
            }

        } catch (ParseException e) {
            birthday = null;
        }
        RealCustomer customer = new RealCustomer(request.getParameter("firstName"), request.getParameter("lastName"), request.getParameter("fatherName"),
                birthday, request.getParameter("nationalCode"));

        htmlGenerator.addToBody(partialForm(errors, "/RealCustomer/create", customer));
        htmlGenerator.addToBody("<div > <a class=\"btn btn-sml\" href=\"/RealCustomer\">Back</a> </div>");

        printWriter.println(htmlGenerator.generate());
    }

    private void showView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<RealCustomer> realCustomers = RealCustomerController.find(id);
        if(realCustomers.size() == 0){
            redirectToRealCustomer(response);
        }
        else{
            RealCustomer realCustomer = realCustomers.get(0);
            PrintWriter printWriter = response.getWriter();
            HtmlGenerator htmlGenerator = new HtmlGenerator();
            htmlGenerator.addTitle("مشتری حقیقی");
            List<FormElement> formElements = new ArrayList<>();
            List<Pair<String , String>>errors = new ArrayList<>();
            formElements.add(new FormElement("text", "firstName", "نام", realCustomer.firstName, getError("firstName", errors)));
            formElements.add(new FormElement("text", "lastName", "نام خانوادگی", realCustomer.lastName, getError("lastName", errors)));
            formElements.add(new FormElement("text", "fatherName", "نام پدر", realCustomer.fatherName, getError("fatherName", errors)));
            formElements.add(new FormElement("text", "nationalCode", "کد ملی", realCustomer.nationalCode, getError("nationalCode", errors)));
            formElements.add(new FormElement("date", "birthday", "تاریخ تولد",realCustomer.birthDay.toString(), getError("birthday", errors)));

            htmlGenerator.addToBody(HtmlGenerator.showData(formElements));
            htmlGenerator.addToBody("<div > <a class=\"btn btn-sml\" href=\"/RealCustomer\">Back</a> </div>");
            printWriter.println(htmlGenerator.generate());
        }
    }

    private void editView(HttpServletRequest request, HttpServletResponse response ,  List<Pair<String, String>> errors ) throws IOException {
        String idString =  request.getParameter("id");
        if (idString == null){
            redirectToRealCustomer(response);
        }
        else{
            PrintWriter printWriter = response.getWriter();
            HtmlGenerator htmlGenerator = new HtmlGenerator();
            int id =  Integer.parseInt(idString);
            htmlGenerator.addTitle("ویرایش مشتری حقیقی");
            List<RealCustomer> realCustomers = RealCustomerController.find(id);
            if(realCustomers.size() == 0){
                redirectToRealCustomer(response);
            }
            else {

                htmlGenerator.addToBody(partialForm(errors ,"/RealCustomer/update?id=" + id,realCustomers.get(0)));
                htmlGenerator.addToBody("<div > <a class=\"btn btn-sml\" href=\"/RealCustomer\">Back</a> </div>");

            }

            printWriter.println(htmlGenerator.generate());
        }
    }

    private void indexView(HttpServletResponse response, List<RealCustomer> realCustomers) throws IOException {
        PrintWriter printWriter = response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("مشتری حقیقی");
        String tableRows = "";
        for (int i = 0; i < realCustomers.size(); ++i) {
            tableRows += String.format("<tr>" +
                            "<td>%s</td>" +
                            "<td>%s</td>" +
                            "<td>%s</td>" +
                            "<td>%s</td>" +
                            "<td>%s</td>" +
                            "<td>%s</td>" +
                            "<td><a href=\"/RealCustomer/edit?id=%s\">update</a></td>\n" +
                            "<td><a href=\"/RealCustomer/delete?id=%s\">delete</a></td>\n" +
                            "</tr>"
                    , i+1, realCustomers.get(i).getCustomerNumber(), realCustomers.get(i).firstName,
                    realCustomers.get(i).lastName, realCustomers.get(i).fatherName
                    , realCustomers.get(i).nationalCode,
                    realCustomers.get(i).id, realCustomers.get(i).id);
        }
        String body = "\n" +
                "    <div class=\"content\">\n" +
                "\n" +
                "        <a class=\"btn btn-sml\" href=\"/RealCustomer/new\"> تعریف مشتری حقیقی جدید</a>\n" +
                "<form action=\"/RealCustomer/search\" method=\"post\">"+
                "        <table class=\"table\">\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th>\n" +
                "                    نام\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    نام خانوادگی\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    کد ملی\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    شماره مشتری\n" +
                "                </th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "            <tr style=\"padding: 0px;\">\n" +
                "                <td>\n" +
                "                    <input name=\"firstName\" class=\"input-control\">\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    <input name=\"lastName\" class=\"input-control\">\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    <input name=\"nationalCode\" class=\"input-control\">\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    <input name=\"customerNumber\" class=\"input-control\">\n" +
                "                </td>\n" +
                "                <td><input value=\"search\" class=\"btn btn-sml\" type=\"submit\" /></td>\n" +
                "            </tr>\n" +
                "            </tbody>" +
                "</form>\n" +
                "\n" +
                "        </table>\n" +
                "        <table class=\"table\">\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th>\n" +
                "                    ردیف\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    شماره مشتری\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    نام\n" +
                "                </th>\n" +
                "                <th>\n" +
                " نام خانوادگی\n" +
                "                </th>\n" +
                "                <th>\n" +
                " نام پدر\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    کد ملی\n" +
                "                </th>\n" +
                "                <th></th>\n" +
                "                <th></th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                tableRows +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>";
        htmlGenerator.addToBody(body);
        printWriter.println(htmlGenerator.generate());

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Pair<String , String>> errors = RealCustomerController.destroy(id);
        if(errors.size() ==0){
            redirectToRealCustomer(response);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String fatherName = request.getParameter("fatherName");
        String nationalCode = request.getParameter("nationalCode");
        String birthday = request.getParameter("birthday");
        List<Pair<String, String>> errors = RealCustomerController.save(firstName, lastName, fatherName, nationalCode, birthday);
        if(errors.size() == 0){
            redirectToRealCustomer(response);
        }else{
            newRealCustomer(request, response, errors);

        }

    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<RealCustomer> realCustomers = new ArrayList<>();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String fatherName = request.getParameter("fatherName");
        String nationalCode = request.getParameter("nationalCode");
        String customerNumber = request.getParameter("customerNumber");
        RealCustomer realCustomer = new RealCustomer(0 , customerNumber , firstName ,lastName , fatherName , null , nationalCode);
        realCustomers = RealCustomerController.find(realCustomer);
        indexView(response  , realCustomers);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getServletConfig().getInitParameter("action");
        response.setContentType("text/html; charset=UTF-8");
        RealCustomerController realCustomerController = new RealCustomerController();

        if ("new".equalsIgnoreCase(action)) {
            newRealCustomer(request, response, new ArrayList<Pair<String, String>>());
        } else if ("edit".equalsIgnoreCase(action)) {
            List<Pair<String  , String>> errors =  new ArrayList<>();
            editView(request, response , errors);
        } else if ("show".equalsIgnoreCase(action)) {
            showView(request, response);
        } else if ("delete".equalsIgnoreCase(action)){
            delete(request , response);
        }else {
            List<RealCustomer> realCustomers = realCustomerController.all();
            indexView(response, realCustomers);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String action = getServletConfig().getInitParameter("action");

        if ("create".equalsIgnoreCase(action)) {
            create(request, response);
        } else if ("search".equalsIgnoreCase(action)) {
            search(request, response);
        }
        else if ("update".equalsIgnoreCase(action)){
            update(request , response);
        }
        else{
            redirectToRealCustomer(response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String fatherName = request.getParameter("fatherName");
        String nationalCode = request.getParameter("nationalCode");
        String birthday = request.getParameter("birthday");
        int id =  Integer.parseInt(request.getParameter("id"));
        List<Pair<String, String>> errors =  RealCustomerController.update(id , firstName , lastName , fatherName , nationalCode , birthday);

        if(errors.size() == 0){
            response.setStatus(response.SC_MOVED_PERMANENTLY);
            response.setHeader("Location" , "/RealCustomer/show?id=" + id);
        }else{
            editView(request, response, errors);

        }
    }
}
