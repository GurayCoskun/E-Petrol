package com.example.e_petrol;

public class Users {
    String namesurname,phone,mail,fueltype,favmark,cartype;

    public Users(){

    }
    public Users(String namesurname, String phone, String mail, String fueltype,String fav, String cartype){
        this.namesurname=namesurname;
        this.phone=phone;
        this.mail=mail;
        this.fueltype=fueltype;
        this.cartype=cartype;
        this.favmark=fav;
    }

    public String getFavmark() {
        return favmark;
    }

    public void setFavmark(String favmark) {
        this.favmark = favmark;
    }

    public String getNamesurname() {
        return namesurname;
    }

    public void setNamesurname(String namesurname) {
        this.namesurname = namesurname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFueltype() {
        return fueltype;
    }

    public void setFueltype(String fueltype) {
        this.fueltype = fueltype;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }


}
