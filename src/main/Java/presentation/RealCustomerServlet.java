package presentation;

import html.FormElement;
import html.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dotin School1 on 4/19/2016.
 */
public class RealCustomerServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("تعریف مشتری حقوقی");
        List<FormElement> formElements = new ArrayList<FormElement>();
        formElements.add(new FormElement("text" , "firstName" , "نام" , ""));
        formElements.add(new FormElement("text" , "lastName" , "نام خانوادگی" , ""));
        formElements.add(new FormElement("text" , "fatherName" , "نام پدر" , ""));
        formElements.add(new FormElement("text" , "nationalCode" , "کد ملی" , ""));
        formElements.add(new FormElement("date" , "birthDay" , "تاریخ تولد" , ""));

        String body = HtmlGenerator.generateForm(formElements , "/CreateRealCustomer");
        htmlGenerator.addToBody(body);

        printWriter.println(htmlGenerator.generate());
    }
}
