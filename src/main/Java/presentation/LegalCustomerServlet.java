package presentation;

import dataaccess.LegalCustomer;
import html.FormElement;
import html.HtmlGenerator;
import javafx.util.Pair;
import logic.LegalCustomerController;

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


    private void redirectToLegalCustomer(HttpServletResponse response) {
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
        htmlGenerator.addTitle("تعریف مشتری حقوقی");
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

        htmlGenerator.addToBody(partialForm(errors, "/LegalCustomer/create", customer));
        htmlGenerator.addToBody("<div > <a class=\"btn btn-sml\" href=\"/LegalCustomer\">Back</a> </div>");

        printWriter.println(htmlGenerator.generate());
    }

    private void showView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<LegalCustomer> legalCustomers = LegalCustomerController.find(id);
        if (legalCustomers.size() == 0) {
            redirectToLegalCustomer(response);
        } else {
            LegalCustomer legalCustomer= legalCustomers.get(0);
            PrintWriter printWriter = response.getWriter();
            HtmlGenerator htmlGenerator = new HtmlGenerator();
            htmlGenerator.addTitle("مشتری حقیقی");
            List<FormElement> formElements = new ArrayList<>();
            List<Pair<String, String>> errors = new ArrayList<>();
            formElements.add(new FormElement("text", "customerNumber", "نام شرکت", legalCustomer.getCustomerNumber(), getError("customerNumber", errors)));
            formElements.add(new FormElement("text", "companyName", "نام شرکت", legalCustomer.companyName, getError("companyName", errors)));
            formElements.add(new FormElement("text", "barCode", "کد اقتصادی", legalCustomer.barCode, getError("barCode", errors)));
            formElements.add(new FormElement("text", "registrationDay", "کد اقتصادی", legalCustomer.registrationDay.toString(), getError("registrationDay", errors)));

            htmlGenerator.addToBody(HtmlGenerator.showData(formElements));
            htmlGenerator.addToBody("<div > <a class=\"btn btn-sml\" href=\"/LegalCustomer\">Back</a> </div>");
            printWriter.println(htmlGenerator.generate());
        }
    }

    private void editView(HttpServletRequest request, HttpServletResponse response, List<Pair<String, String>> errors) throws IOException {
        String idString = request.getParameter("id");
        if (idString == null) {
            redirectToLegalCustomer(response);
        } else {
            PrintWriter printWriter = response.getWriter();
            HtmlGenerator htmlGenerator = new HtmlGenerator();
            int id = Integer.parseInt(idString);
            htmlGenerator.addTitle("ویرایش مشتری حقیقی");
            List<LegalCustomer> legalCustomers = LegalCustomerController.find(id);
            if (legalCustomers.size() == 0) {
                redirectToLegalCustomer(response);
            } else {

                htmlGenerator.addToBody(partialForm(errors, "/LegalCustomer/update?id=" + id, legalCustomers.get(0)));
                htmlGenerator.addToBody("<div > <a class=\"btn btn-sml\" href=\"/LegalCustomer\">Back</a> </div>");

            }

            printWriter.println(htmlGenerator.generate());
        }
    }

    private void indexView(HttpServletResponse response, List<LegalCustomer> legalCustomers) throws IOException {
        PrintWriter printWriter = response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("مشتری حقوقی");
        String tableRows = "";
        for (int i = 0; i < legalCustomers.size(); ++i) {
            tableRows += String.format("<tr>" +
                            "<td>%s</td>" +
                            "<td>%s</td>" +
                            "<td>%s</td>" +
                            "<td>%s</td>" +
                            "<td><a href=\"/LegalCustomer/edit?id=%s\">update</a></td>\n" +
                            "<td><a href=\"/LegalCustomer/delete?id=%s\">delete</a></td>\n" +
                            "</tr>"
                    , i + 1, legalCustomers.get(i).getCustomerNumber(), legalCustomers.get(i).companyName,
                    legalCustomers.get(i).barCode ,
                    legalCustomers.get(i).id, legalCustomers.get(i).id , 3 ,4,5,1);
        }
        String body = "\n" +
                "    <div class=\"content\">\n" +
                "\n" +
                "        <a class=\"btn btn-sml\" href=\"/LegalCustomer/new\"> تعریف مشتری حقیقی جدید</a>\n" +
                "<form action=\"/LegalCustomer/search\" method=\"post\">" +
                "        <table class=\"table\">\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th>\n" +
                "                    شماره مشتری\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                    نام شرکت\n" +
                "                </th>\n" +
                "                <th>\n" +
                "                   شماره اقتصادی\n" +
                "                </th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "            <tr style=\"padding: 0px;\">\n" +
                "                <td>\n" +
                "                    <input name=\"customerNumber\" class=\"input-control\">\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    <input name=\"companyName\" class=\"input-control\">\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    <input name=\"barCode\" class=\"input-control\">\n" +
                "                </td>\n" +
                "                <td><input value=\"search\" class=\"btn btn-sml\" type=\"submit\" /></td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "\n" +
                "        </table></form>\n" +
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
                "                    نام شرکت\n" +
                "                </th>\n" +
                "                <th>\n" +
                " کد اقتصادی\n" +
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
        List<Pair<String, String>> errors = LegalCustomerController.destroy(id);
        if (errors.size() == 0) {
            redirectToLegalCustomer(response);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String companyName = request.getParameter("companyName");
        String barCode = request.getParameter("barCode");
        String registrationDay = request.getParameter("registrationDay");
        List<Pair<String, String>> errors = LegalCustomerController.save(companyName, barCode ,registrationDay);
        if (errors.size() == 0) {
            redirectToLegalCustomer(response);
        } else {
            newLegalCustomer(request, response, errors);

        }

    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<LegalCustomer> legalCustomers = new ArrayList<>();
        String barCode = request.getParameter("barCode");
        String companyName = request.getParameter("companyName");
        String customerNumber = request.getParameter("customerNumber");
        LegalCustomer legalCustomer = new LegalCustomer(0 ,customerNumber , companyName , barCode , null);
        legalCustomers = LegalCustomerController.find(legalCustomer);
        indexView(response  , legalCustomers);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getServletConfig().getInitParameter("action");
        response.setContentType("text/html; charset=UTF-8");

        if ("new".equalsIgnoreCase(action)) {
            newLegalCustomer(request, response, new ArrayList<Pair<String, String>>());
        }  else if ("show".equalsIgnoreCase(action)) {
            showView(request, response);
        } else if ("edit".equalsIgnoreCase(action)) {
            List<Pair<String, String>> errors = new ArrayList<>();
            editView(request, response, errors);
        }else if ("delete".equalsIgnoreCase(action)) {
            delete(request, response);
        } else {
            List<LegalCustomer> legalCustomers = LegalCustomerController.all();
            indexView(response, legalCustomers);
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
            redirectToLegalCustomer(response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String companyName = request.getParameter("companyName");
        String barCode = request.getParameter("barCode");
        String registrationDay = request.getParameter("registrationDay");
        int id = Integer.parseInt(request.getParameter("id"));
        List<Pair<String, String>> errors = LegalCustomerController.update(id, companyName, barCode, registrationDay);

        if (errors.size() == 0) {
            response.setStatus(response.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", "/LegalCustomer/show?id=" + id);
        } else {
            editView(request, response, errors);

        }
    }
}
