package presentation;


import html.HtmlGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Home extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("home");
        String body = "<div>\n" +
                "    <a class=\"button\" href=\"/CustomerManager?type=legal\"> Legal Customer</a>\n" +
                "    <a class=\"button\" href=\"/CustomerManager?type=real\" > Real Customer</a>\n" +
                "</div>";
        htmlGenerator.addToBody(body);
        htmlGenerator.addCss("style.css");
        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println(body);
    }

}
