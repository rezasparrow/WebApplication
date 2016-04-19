package presentation;

import common.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * Created by Dotin School1 on 4/19/2016.
 */
public class CustomerManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HtmlGenerator htmlGenerator ;
        htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("Customer Manager");
        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter printWriter = resp.getWriter();
        String type = req.getParameter("type");
        String customerType = "";
        if(type.equalsIgnoreCase("realCustomer")){
            customerType = "حقیقی";
        }
        else {
            customerType = "حقوقی";
        }

        String body = String.format("<div> \n" +
                "<a href=\"/newCustomer/%s\" class=\"button\">تعریف مشتری %s جدید</a>\n" +
                "<a href=\"/searchCustomer/%s\" class=\"button\">جستجوی مشتری %s</a>\n" +
                "</div>" , type ,customerType ,type , customerType);
        htmlGenerator.addToBody(body);

        printWriter.println(htmlGenerator.generate());
//        super.doGet(req, resp);
    }
}
