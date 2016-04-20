package presentation;

import dataaccess.LegalCustomer;
import html.FormElement;
import html.HtmlGenerator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;



public class RealCustomerServlet extends HttpServlet {

    private void newRealCustomer(HttpServletRequest request , HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("تعریف مشتری حقوقی");
        List<FormElement> formElements = new ArrayList<FormElement>();
        htmlGenerator.addToBody("<div class=\"header\">\n" +
                "\n" +
                "    </div>\n" +
                "    <div class=\"content center\">");
        formElements.add(new FormElement("text", "firstName", "نام", ""));
        formElements.add(new FormElement("text", "lastName", "نام خانوادگی", ""));
        formElements.add(new FormElement("text", "fatherName", "نام پدر", ""));
        formElements.add(new FormElement("text", "nationalCode", "کد ملی", ""));
        formElements.add(new FormElement("date", "birthDay", "تاریخ تولد", ""));

        String body = HtmlGenerator.generateForm(formElements, "/Customer");
        htmlGenerator.addToBody("</div>");
        htmlGenerator.addToBody(body);

        printWriter.println(htmlGenerator.generate());
    }

    private void showView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter  printWriter =response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("مشتری حقیقی");
        htmlGenerator.addToBody("show page");
        printWriter.println(htmlGenerator.generate());
    }

    private void editView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter  printWriter =response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("مشتری حقیقی");
        htmlGenerator.addToBody("edit page");
        printWriter.println(htmlGenerator.generate());
    }

    private void indexView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter  printWriter =response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("مشتری حقیقی");
        String body= "\n" +
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
                "                <th></th>\n" +
                "                <th></th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    ۱\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>edit</td>\n" +
                "                <td>delete</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    ۲\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>edit</td>\n" +
                "                <td>delete</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    ۳\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>edit</td>\n" +
                "                <td>delete</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    ۴\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>edit</td>\n" +
                "                <td>delete</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    ۵\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>\n" +
                "                    reza\n" +
                "                </td>\n" +
                "                <td>edit</td>\n" +
                "                <td>delete</td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>";
        htmlGenerator.addToBody(body);
        printWriter.println(htmlGenerator.generate());

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {

    }

    private void create(HttpServletRequest request , HttpServletResponse response){
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String fatherName = request.getParameter("fatherName");
        String nationalCode = request.getParameter("nationalCode");


    }
    private void search(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getServletConfig().getInitParameter("action");
        response.setContentType("text/html; charset=UTF-8");

        if("new".equalsIgnoreCase(action)){
            newRealCustomer(request,response);
        } else if ("edit".equalsIgnoreCase(action)) {
            editView(request, response);
        }
        else if ("showView".equalsIgnoreCase(action)) {
            showView(request, response);
        } else {
            indexView(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getServletConfig().getInitParameter("action");

        if("create".equalsIgnoreCase(action)){
            create(request ,  response);
        }else if("search".equalsIgnoreCase(action)){
            search(request , response);
        }
    }
}
