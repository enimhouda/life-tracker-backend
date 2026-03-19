package com.life.tracker.model;


import jakarta.persistence.*;


import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String vorname;
    private String nachname;
    private String email;
    private String geburtsdatum;
    private String land;

    @OneToMany(mappedBy = "users")
    private List<ZielEntity> zielEntityList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public List<ZielEntity> getZielEntityList() {
        return zielEntityList;
    }

    public void setZielEntityList(List<ZielEntity> zielEntityList) {
        this.zielEntityList = zielEntityList;
    }
}

