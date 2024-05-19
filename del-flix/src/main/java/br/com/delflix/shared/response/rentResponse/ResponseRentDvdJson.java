package br.com.delflix.shared.response.rentResponse;

public class ResponseRentDvdJson 
{
    private String Title;
    private String Identifier;
    private int Days;

    public ResponseRentDvdJson() {
    }
    
    public ResponseRentDvdJson(String title, String identifier, int days) {
        Title = title;
        Identifier = identifier;
        Days = days;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
    } 

    
}
