package html;

/**
 * Created by Dotin School1 on 4/19/2016.
 */
public class FormElement {
    public String type;
    public String name;
    public String text;
    public String errorText;
    public String value;

    public FormElement(String type, String name, String text, String value, String error) {
        this.type = type;
        this.name = name;
        this.text = text;
        this.errorText = error;
        if(value != null)
        {
            this.value = value;
        }else{
            this.value = "";
        }
    }
}
