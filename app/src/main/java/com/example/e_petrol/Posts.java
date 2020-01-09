package com.example.e_petrol;

public class Posts {
    String mark,liter,date,day,month,year,many;
    public Posts(){

    }

    public Posts(String mark,String liter,String date,String many){
        this.mark=mark;
        this.liter=liter;
        this.date=date;
        this.many=many;
    }
    public Posts(String mark,String liter,String date,String day,String month,String year,String many){
        this.mark=mark;
        this.liter=liter;
        this.date=date;
        this.day=day;
        this.month=month;
        this.year=year;
        this.many=many;
    }

    public String getMany() {
        return many;
    }

    public void setMany(String many) {
        this.many = many;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getLiter() {
        return liter;
    }

    public void setLiter(String liter) {
        this.liter = liter;
    }
}
