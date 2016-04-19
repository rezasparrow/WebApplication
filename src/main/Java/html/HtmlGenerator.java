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
                "<link href=\"css/style.css\" rel=\"stylesheet\">" +
                "%s" +
                "</head>" +
                "<body>" +
                "%s" +
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
        String formHtml = String.format("<form action=\"$s\" method=\"post\">", action);
        for (FormElement formElement : formElements) {
            formHtml += String.format("<div class =\"%s\" >\n" +
                    "            <label for=\"%s\"> %s</label>\n" +
                    "            <div><input id=\"%s\" type=\"%s\" name=\"%s\"></div>\n" +
                    "        </div>", formElement.htmlClass, formElement.name, formElement.text, formElement.name, formElement.type, formElement.name);
        }
        formHtml += "<div>\n" +
                "            <input type=\"submit\" value=\"ایجاد\">\n" +
                "        </div>\n" +
                "    </form>";
        return formHtml;
    }
}
