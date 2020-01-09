package com.example.e_petrol;



public class jsonPost {
    String sehir,semt,marka;
    String katkili,benzin;
    public jsonPost(){

    }
    public jsonPost(String sehir,String semt,String marka,String katkili,String benzin){
        this.sehir=sehir;
        this.semt=semt;
        this.marka=marka;
        this.katkili=katkili;
        this.benzin=benzin;

    }

    public String getSehir() {
        return sehir;
    }

    public void setSehir(String sehir) {
        this.sehir = sehir;
    }

    public String getSemt() {
        return semt;
    }

    public void setSemt(String semt) {
        this.semt = semt;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getKatkili() {
        return katkili;
    }

    public void setKatkili(String katkili) {
        this.katkili = katkili;
    }

    public String getBenzin() {
        return benzin;
    }

    public void setBenzin(String benzin) {
        this.benzin = benzin;
    }
}
