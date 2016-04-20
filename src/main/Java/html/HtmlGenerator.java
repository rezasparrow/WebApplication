package html;

import java.lang.String;
import java.util.List;


public class HtmlGenerator {
    private String css;
    private String html;
    private String title;
    private String body;

    public HtmlGenerator() {
        body = "";
        css = "";
        html = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" + "" +
                "<link href=\"/css/style.css\" rel=\"stylesheet\">" +
                "%s" +
                "</head>" +
                "<body  style=\"background:#cccccc;\">" +
                "<div class=\"container\" style=\"direction: rtl\">" +
                "%s" +
                "</div>" +
                "</body>" +
                "</html>";
        title = "";
        body = "";
    }

    public void addCss(String cssAddress) {
        css += "<link rel=\"stylesheet\" href=\"" + cssAddress + "\">";
    }

    public void addTitle(String title) {
        this.title = "<title>" + title + "</title>";
    }

    public void addToBody(String content) {
        body += content;
    }

    public String generate() {
        return java.lang.String.format(html, title + css, body);
    }

    public static String generateForm(List<FormElement> formElements, String action) {
        String formHtml = String.format("<form action=\"%s\" method=\"post\">", action);
        for (FormElement formElement : formElements) {
            if (formElement.errorText.isEmpty()) {
                formHtml += String.format("<div class =\"form-elm\" >\n" +
                        "            <label  class=\"label-control\" for=\"%s\"> %s</label>\n" +
                        "            <div  class=\"sml-col\"><input  class=\"input-control\" id=\"%s\" type=\"%s\" name=\"%s\" value=\"%s\"></div>\n" +
                        "        </div>", formElement.name, formElement.text, formElement.name, formElement.type, formElement.name , formElement.value);

            } else {
                formHtml += String.format("<div class =\"form-elm\" >\n" +
                        "            <label  class=\"label-control\" for=\"%s\"> %s</label>\n" +
                        "            <div  class=\"sml-col \"><input  class=\"input-control has-error\" id=\"%s\" type=\"%s\" name=\"%s\" value=\"%s\">" +
                        "<div style=\"color:red;\" > %s </div></div>\n" +
                        "        </div>", formElement.name, formElement.text, formElement.name, formElement.type, formElement.name,formElement.value , formElement.errorText);

            }
        }
        formHtml += "<div>\n" +
                "            <input type=\"submit\" value=\"ایجاد\">\n" +
                "        </div>\n" +
                "    </form>";
        return formHtml;
    }
}
