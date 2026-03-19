package com.life.tracker.model;


import jakarta.persistence.*;

@Entity
@Table(name = "ziele")
public class ZielEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titel;
    private String description;
    private String email;

    @ManyToOne
    @JoinColumn(name = "users")
    private UserEntity users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return description;
    }

    public void setBeschreibung(String beschreibung) {
        this.description = beschreibung;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserEntity getUserEntity() {
        return users;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.users = userEntity;
    }

}

