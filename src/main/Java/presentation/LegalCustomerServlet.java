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


public class LegalCustomerServlet extends HttpServlet {

    private void newLegalCustomer(HttpServletRequest request , HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("تعریف مشتری حقوقی");
        List<FormElement> formElements = new ArrayList<FormElement>();
        formElements.add(new FormElement("text" , "companyName" , "نام" , request.getParameter("companyName"), ""));
        formElements.add(new FormElement("text" , "barCode" , "کد اقتصادی" ,request.getParameter("barCode"), ""));

        String body = HtmlGenerator.generateForm(formElements , "/CreateLegalCustomer");
        htmlGenerator.addToBody(body);

        printWriter.println(htmlGenerator.generate());
    }

    private void showView(HttpServletRequest request, HttpServletResponse response){

    }

    private void editView(HttpServletRequest request, HttpServletResponse response){

    }

    private void indexView(HttpServletRequest request, HttpServletResponse response){

    }
    private void delete(HttpServletRequest request, HttpServletResponse response){

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = getServletConfig().getInitParameter("action");
        response.setContentType("text/html; charset=UTF-8");

        if("new".equalsIgnoreCase(action)){
            newLegalCustomer(request,response);
        }
        else if ("showView".equalsIgnoreCase(action)) {
            showView(request, response);
        } else if (action.equalsIgnoreCase("editView")) {
            editView(request, response);
        } else {
            indexView(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
}
