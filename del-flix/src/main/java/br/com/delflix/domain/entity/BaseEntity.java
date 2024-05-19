package br.com.delflix.domain.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "create_at")
    private Date CreateAt;

    @Column(name = "update_at")
    private Date UpdateAt;

    @Column(name = "delete_at")
    private Date DeleteAt;

    @Column(name = "aviable")
    private boolean Aviable;

    @Column(name = "identifier")
    private String Identifier;

    public BaseEntity() {}

    public BaseEntity(boolean Aviable, Date DeleteAt, Date UpdateAt) 
    {
        this.Aviable = Aviable;
        this.CreateAt = new Date();
        this.DeleteAt = DeleteAt;
        this.Identifier = UUID.randomUUID().toString();
        this.UpdateAt = UpdateAt;
    }

    public Long getId() {
        return Id;
    }

    public Date getCreateAt() {
        return CreateAt;
    }

    public Date getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(Date UpdateAt) {
        this.UpdateAt = UpdateAt;
    }

    public Date getDeleteAt() {
        return DeleteAt;
    }

    public void setDeleteAt(Date DeleteAt) {
        this.DeleteAt = DeleteAt;
    }

    public boolean isAviable() {
        return Aviable;
    }

    public void setAviable(boolean Aviable) {
        this.Aviable = Aviable;
    }

    public String getIdentifier() {
        return Identifier;
    }
}
