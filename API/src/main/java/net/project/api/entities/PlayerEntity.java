package net.project.api.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.Locale;

@Entity(name = "Player")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NaturalId
    @Column(unique = true)
    private String uuid;

    private Locale locale;

    private double skyCoins;

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public double getSkyCoins() {
        return skyCoins;
    }

    public void setSkyCoins(double skyCoins) {
        this.skyCoins = skyCoins;
    }
}
