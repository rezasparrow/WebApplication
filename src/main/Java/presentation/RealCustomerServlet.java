package presentation;

import dataaccess.LegalCustomer;
import dataaccess.RealCustomer;
import dataaccess.RealCustomerCRUD;
import html.FormElement;
import html.HtmlGenerator;
import javafx.util.Pair;
import logic.RealCustomerController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class RealCustomerServlet extends HttpServlet {

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
    private void partialForm(HttpServletRequest request, HttpServletResponse response, List<Pair<String, String>> errors , String action){

    }
    private void newRealCustomer(HttpServletRequest request, HttpServletResponse response, List<Pair<String, String>> errors) throws IOException {
        PrintWriter printWriter = response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("تعریف مشتری حقوقی");
        List<FormElement> formElements = new ArrayList<FormElement>();
        htmlGenerator.addToBody("<div class=\"header\">\n" +
                "\n" +
                "    </div>\n" +
                "    <div class=\"content center\">");
        formElements.add(new FormElement("text", "firstName", "نام", request.getParameter("firstName") ,  getError("firstName" , errors)));
        formElements.add(new FormElement("text", "lastName", "نام خانوادگی",request.getParameter("lastName") ,  getError("lastName" , errors)));
        formElements.add(new FormElement("text", "fatherName", "نام پدر",request.getParameter("fatherName") ,  getError("fatherName" , errors)));
        formElements.add(new FormElement("text", "nationalCode", "کد ملی",request.getParameter("nationalCode") ,  getError("nationalCode" , errors)));
        formElements.add(new FormElement("date", "birthday", "تاریخ تولد", request.getParameter("birthday") , getError("birthday" , errors)));

        String body = HtmlGenerator.generateForm(formElements, "/RealCustomer/create");
        htmlGenerator.addToBody("</div>");
        htmlGenerator.addToBody(body);

        printWriter.println(htmlGenerator.generate());
    }

    private void showView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("مشتری حقیقی");
        htmlGenerator.addToBody("show page");
        printWriter.println(htmlGenerator.generate());
    }

    private void editView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("مشتری حقیقی");
        htmlGenerator.addToBody("edit page");
        printWriter.println(htmlGenerator.generate());
    }

    private void indexView(HttpServletRequest request, HttpServletResponse response , List<RealCustomer> realCustomers) throws IOException {
        PrintWriter printWriter = response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("مشتری حقیقی");
        String tableRows = "";
        for (int i = 0 ; i < realCustomers.size() ; ++i){
            tableRows+= String.format("<tr>" +
                    "<td>%s</td>" +
                    "<td>%s</td>" +
                    "<td>%s</td>" +
                    "<td>%s</td>" +
                    "<td>%s</td>" +
                    "<td>%s</td>" +
                    "<td><a href=\"/RealCustomer/edit?id=%s\">edit</a></td>\n" +
                    "<td><a href=\"/RealCustomer/delete?id=%s\">delete</a></td>\n" +
                    "</tr>"
                    , i ,realCustomers.get(i).getCustomerNumber() ,realCustomers.get(i).firstName ,
                    realCustomers.get(i).lastName,realCustomers.get(i).fatherName
                    ,realCustomers.get(i).nationalCode ,
                    realCustomers.get(i).id , realCustomers.get(i).id);
        }
        String body = "\n" +
                "    <div class=\"header\">\n" +
                "\n" +
                "    </div>\n" +
                "    <div class=\"content\">\n" +
                "\n" +
                "        <a class=\"btn btn-sml\" href=\"/RealCustomer/new\"> تعریف مشتری حقیقی جدید</a>\n" +
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
                "                    شناسه\n" +
                "                </th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "            <tr style=\"padding: 0px;\">\n" +
                "                <td>\n" +
                "                    <input class=\"input-control\">\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    <input class=\"input-control\">\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    <input class=\"input-control\">\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    <input class=\"input-control\">\n" +
                "                </td>\n" +
                "                <td><input value=\"search\" class=\"btn btn-sml\" type=\"submit\" /></td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
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
                tableRows+
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>";
        htmlGenerator.addToBody(body);
        printWriter.println(htmlGenerator.generate());

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {

    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String fatherName = request.getParameter("fatherName");
        String nationalCode = request.getParameter("nationalCode");
        String birthday = request.getParameter("birthday");
        RealCustomerController realCustomerController = new RealCustomerController();
        List<Pair<String, String>> errors = realCustomerController.save(firstName, lastName, fatherName, nationalCode, birthday);
        newRealCustomer(request , response , errors);

    }

    private void search(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getServletConfig().getInitParameter("action");
        response.setContentType("text/html; charset=UTF-8");
        RealCustomerController realCustomerController = new RealCustomerController();

        if ("new".equalsIgnoreCase(action)) {
            newRealCustomer(request, response, new ArrayList<Pair<String, String>>());
        } else if ("edit".equalsIgnoreCase(action)) {
            editView(request, response);
        } else if ("showView".equalsIgnoreCase(action)) {
            showView(request, response);
        } else {
            List<RealCustomer> realCustomers = realCustomerController.all();
            indexView(request, response , realCustomers);
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
    }
}
