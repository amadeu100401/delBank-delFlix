package br.com.delflix.domain.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Dvd")
public class Dvd extends BaseEntity {
    @Column(name="title", nullable=false, length=100)
    private String title;

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="author_id", nullable=false, referencedColumnName="id")
    private Author author;

    @Column(name="gender", nullable=false, length=100)
    private String gender;    

    @Column(name="published", nullable=false)
    private Date published;

    @Column(name="copies_quantity", nullable=false)
    private int copiesQuantity;

    public Dvd() {
        super(true, null, new Date());
    }

    public Dvd(String title, Author author, String gender, Date published, int copiesQuantity) {
        super(true, null, new Date());
        this.title = title;
        this.author = author;
        this.gender = gender;
        this.published = published;
        this.copiesQuantity = copiesQuantity;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getPublished() {
        return this.published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public int getCopiesQuantity() {
        return this.copiesQuantity;
    }

    public void setCopiesQuantity(int copiesQuantity) {
        this.copiesQuantity = copiesQuantity;
    }
}
