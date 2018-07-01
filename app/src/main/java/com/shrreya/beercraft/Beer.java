package com.shrreya.beercraft;

public class Beer {

    private String abv, ibu, name, style;
    private int id;
    private double ounces;

    public Beer() {
    }

    public Beer(String abv, String ibu, int id, String name, String style, double ounces) {
        this.abv = abv;
        this.ibu = ibu;
        this.id = id;
        this.name = name;
        this.style = style;
        this.ounces = ounces;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getIbu() {
        return ibu;
    }

    public void setIbu(String ibu) {
        this.ibu = ibu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public double getOunces() {
        return ounces;
    }

    public void setOunces(double ounces) {
        this.ounces = ounces;
    }
}