package br.com.delflix.shared.request.AuthorRequest;

public class RequestAuthorRegisterJson 
{
    private final String Name;
    private final String Surname;
    
    public RequestAuthorRegisterJson(String name, String surname) {
        Name = name;
        Surname = surname;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }
}
