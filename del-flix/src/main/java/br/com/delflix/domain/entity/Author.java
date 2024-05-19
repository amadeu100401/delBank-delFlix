package br.com.delflix.domain.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Authors")
public class Author extends BaseEntity 
{
    @Column(name="name", nullable=false, length=100)
    private String name;

    @Column(name="surname", nullable=false, length=100)
    private String surname;

    @OneToOne(mappedBy="author")
    private Dvd dvd;
    
    public Author() {
        super(true, null, new Date());
    }

    public Author(String name, String surname) {
        super(true, null, new Date());
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
