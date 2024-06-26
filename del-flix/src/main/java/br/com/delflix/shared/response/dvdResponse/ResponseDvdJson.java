package br.com.delflix.shared.response.dvdResponse;

public class ResponseDvdJson 
{
    private String Title;
    private int CopiesQuantity;
    private String Identifier;
    private boolean Aviable;

    public ResponseDvdJson() {
    }

    public ResponseDvdJson(String title, int copiesQuantity, String identifier, boolean isActive)
    {
        this.Title = title;
        this.CopiesQuantity = copiesQuantity;
        this.Identifier = identifier;
        this.Aviable = isActive;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public int getCopiesQuantity() {
        return CopiesQuantity;
    }

    public void setCopiesQuantity(int copiesQuantity) {
        this.CopiesQuantity = copiesQuantity;
    }

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String Identifier) {
        this.Identifier = Identifier;
    }

    public boolean isAviable() { return Aviable; }

    public void setAviable(boolean aviable) { Aviable = aviable; }
}
