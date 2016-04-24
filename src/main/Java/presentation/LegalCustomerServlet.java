package presentation;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LegalCustomerServlet extends HttpServlet {


    private void redirectToRealCustomer(HttpServletResponse response) {
        response.setStatus(response.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", "/LegalCustomer");
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

    private String partialForm(List<Pair<String, String>> errors, String action, LegalCustomer customer) {
        List<FormElement> formElements = new ArrayList<FormElement>();
        String registrationDay = "";
        if (customer.registrationDay == null) {
            registrationDay = "";
        } else {
            registrationDay = customer.registrationDay.toString();
        }
        formElements.add(new FormElement("text", "companyName", "نام شرکت", customer.companyName, getError("companyName", errors)));
        formElements.add(new FormElement("text", "barCode", "کد اقتصادی", customer.barCode, getError("barCode", errors)));
        formElements.add(new FormElement("date", "registrationDay", "تاریخ ثبت", registrationDay, getError("registrationDay", errors)));

        return HtmlGenerator.generateForm(formElements, action);
    }

    private void newLegalCustomer(HttpServletRequest request, HttpServletResponse response, List<Pair<String, String>> errors) throws IOException {
        PrintWriter printWriter = response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("تعریف مشتری حقیقی");
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");

        Date registrationDay = null;
        try {
            String date = request.getParameter("registrationDay");
            if (date != null && !date.isEmpty()) {
                registrationDay = df.parse(date);
            }

        } catch (ParseException e) {
            registrationDay = null;
        }
        LegalCustomer customer = new LegalCustomer(request.getParameter("companyName"), request.getParameter("barCode"),
                registrationDay);

        htmlGenerator.addToBody(partialForm(errors, "/RealCustomer/create", customer));
        htmlGenerator.addToBody("<div > <a class=\"btn btn-sml\" href=\"/RealCustomer\">Back</a> </div>");

        printWriter.println(htmlGenerator.generate());
    }

    private void showView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<RealCustomer> realCustomers = RealCustomerController.find(id);
        if (realCustomers.size() == 0) {
            redirectToRealCustomer(response);
        } else {
            RealCustomer realCustomer = realCustomers.get(0);
            PrintWriter printWriter = response.getWriter();
            HtmlGenerator htmlGenerator = new HtmlGenerator();
            htmlGenerator.addTitle("مشتری حقیقی");
            List<FormElement> formElements = new ArrayList<>();
            List<Pair<String, String>> errors = new ArrayList<>();
            formElements.add(new FormElement("text", "firstName", "نام", realCustomer.firstName, getError("firstName", errors)));
            formElements.add(new FormElement("text", "lastName", "نام خانوادگی", realCustomer.lastName, getError("lastName", errors)));
            formElements.add(new FormElement("text", "fatherName", "نام پدر", realCustomer.fatherName, getError("fatherName", errors)));
            formElements.add(new FormElement("text", "nationalCode", "کد ملی", realCustomer.nationalCode, getError("nationalCode", errors)));
            formElements.add(new FormElement("date", "birthday", "تاریخ تولد", realCustomer.birthDay.toString(), getError("birthday", errors)));

            htmlGenerator.addToBody(HtmlGenerator.showData(formElements));
            htmlGenerator.addToBody("<div > <a class=\"btn btn-sml\" href=\"/RealCustomer\">Back</a> </div>");
            printWriter.println(htmlGenerator.generate());
        }
    }

    private void editView(HttpServletRequest request, HttpServletResponse response, List<Pair<String, String>> errors) throws IOException {
        String idString = request.getParameter("id");
        if (idString == null) {
            redirectToRealCustomer(response);
        } else {
            PrintWriter printWriter = response.getWriter();
            HtmlGenerator htmlGenerator = new HtmlGenerator();
            int id = Integer.parseInt(idString);
            htmlGenerator.addTitle("ویرایش مشتری حقیقی");
            List<LegalCustomer> legalCustomers = LegalCustomerController.find(id);
            if (legalCustomers.size() == 0) {
                redirectToRealCustomer(response);
            } else {

                htmlGenerator.addToBody(partialForm(errors, "/RealCustomer/update?id=" + id, legalCustomers.get(0)));
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
                    , i + 1, realCustomers.get(i).getCustomerNumber(), realCustomers.get(i).firstName,
                    realCustomers.get(i).lastName, realCustomers.get(i).fatherName
                    , realCustomers.get(i).nationalCode,
                    realCustomers.get(i).id, realCustomers.get(i).id);
        }
        String body = "\n" +
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
                tableRows +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>";
        htmlGenerator.addToBody(body);
        printWriter.println(htmlGenerator.generate());

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Pair<String, String>> errors = RealCustomerController.destroy(id);
        if (errors.size() == 0) {
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
        if (errors.size() == 0) {
            redirectToRealCustomer(response);
        } else {
            newLegalCustomer(request, response, errors);

        }

    }

    private void search(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getServletConfig().getInitParameter("action");
        response.setContentType("text/html; charset=UTF-8");
        RealCustomerController realCustomerController = new RealCustomerController();

        if ("new".equalsIgnoreCase(action)) {
            newLegalCustomer(request, response, new ArrayList<Pair<String, String>>());
        } else if ("edit".equalsIgnoreCase(action)) {
            List<Pair<String, String>> errors = new ArrayList<>();
            editView(request, response, errors);
        } else if ("show".equalsIgnoreCase(action)) {
            showView(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            delete(request, response);
        } else {
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
        } else if ("update".equalsIgnoreCase(action)) {
            update(request, response);
        } else {
            redirectToRealCustomer(response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String fatherName = request.getParameter("fatherName");
        String nationalCode = request.getParameter("nationalCode");
        String birthday = request.getParameter("birthday");
        int id = Integer.parseInt(request.getParameter("id"));
        List<Pair<String, String>> errors = RealCustomerController.update(id, firstName, lastName, fatherName, nationalCode, birthday);

        if (errors.size() == 0) {
            response.setStatus(response.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", "/RealCustomer/show?id=" + id);
        } else {
            editView(request, response, errors);

        }
    }
}
