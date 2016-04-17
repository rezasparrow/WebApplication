package presentation;

import common.HtmlGenerator;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class HelloWorld extends HttpServlet {


    public void init() throws ServletException
    {
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.addTitle("home");
        htmlGenerator.addToBody("<h1> HelloWorld </h2>");
        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println(htmlGenerator.generate());
    }

    public void destroy()
    {
        // do nothing.
    }
}