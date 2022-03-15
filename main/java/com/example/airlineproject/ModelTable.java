package com.example.airlineproject;

public class ModelTable {
    String clas, hour, price;
    int idflights;

    public ModelTable(int idflights, String clas, String hour, String price) {
        this.idflights = idflights;
        this.clas = clas;
        this.hour = hour;
        this.price = price;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIdflights() {
        return idflights;
    }

    public void setIdflights(int idflights) {
        this.idflights = idflights;
    }
}
