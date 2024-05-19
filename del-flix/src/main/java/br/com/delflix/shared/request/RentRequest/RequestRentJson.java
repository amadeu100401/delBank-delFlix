package br.com.delflix.shared.request.RentRequest;

public class RequestRentJson {
    private String dvdIdentifier;
    private int days;

    public RequestRentJson() {
    }

    public RequestRentJson(String dvdIdentifier, int days) {
        this.dvdIdentifier = dvdIdentifier;
        this.days = days;
    }

    public String getDvdIdentifier() {
        return dvdIdentifier;
    }

    public void setDvdIdentifier(String dvdIdentifier) {
        this.dvdIdentifier = dvdIdentifier;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
