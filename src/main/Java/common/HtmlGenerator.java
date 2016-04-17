package common;

import java.lang.String;



public class HtmlGenerator {
    private String css;
    private String html;
    private String title;
    private String body;

    public HtmlGenerator(){
        body ="";
        css ="";
        html = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "%s" +
                "</head>" +
                "<body>" +
                "%s" +
                "</body>" +
                "</html>";
        title = "";
        body = "";
     }
    public void addCss(String cssAddress){
        css += "<link rel=\"stylesheet\" href=\"" + cssAddress + "\">";
    }

    public void addTitle(String title){
        this.title = "<title>" + title +"</title>";
    }

    public void addToBody(String content){
        body += content;
    }

    public String  generate(){
        return java.lang.String.format(html , title + css , body);
    }
}
