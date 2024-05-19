package br.com.delflix.shared.request.DvdRequest;

import java.util.Date;

import br.com.delflix.shared.Enum.GenderEnum;
import br.com.delflix.shared.request.AuthorRequest.RequestAuthorRegisterJson;

public class RequestDvdJson 
{
    private final String Title;

    private final RequestAuthorRegisterJson Author;

    private final GenderEnum Gender;    

    private final Date Published;

    private final int CopiesQuantity;

    private final boolean Aviable;

    public RequestDvdJson(String title, RequestAuthorRegisterJson author, GenderEnum gender, Date published, int copiesQuantity,
            boolean aviable) {
        Title = title;
        Author = author;
        Gender = gender;
        Published = published;
        CopiesQuantity = copiesQuantity;
        Aviable = aviable;
    }

    public String getTitle() {
        return Title;
    }

    public GenderEnum getGender() {
        return Gender;
    }

    public Date getPublished() {
        return Published;
    }

    public int getCopiesQuantity() {
        return CopiesQuantity;
    }
    
    public boolean isAviable() {
        return Aviable;
    }

    public RequestAuthorRegisterJson getAuthor() {
        return Author;
    }
}
