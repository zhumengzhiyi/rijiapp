package com.example.jump.enity;

public class Riji {
    public int id;
    public String guanjianzi;
    public String zongjie;
    public String tianshu;
    public String zuichang;
    public String riqi;

    public Riji(){

    }

    public Riji(String guanjizi, String zongjie, String tianshu, String zuichang, String riqi) {
        //this.id=id;
        this.guanjianzi = guanjizi;
        this.zongjie = zongjie;
        this.tianshu = tianshu;
        this.zuichang = zuichang;
        this.riqi = riqi;
    }

    @Override
    public String toString() {
        return "Riji{" +
                "id='" + id + '\'' +
                ", guanjizi='" + guanjianzi + '\'' +
                ", zongjie='" + zongjie + '\'' +
                ", tianshu='" + tianshu + '\'' +
                ", zuichang='" + zuichang + '\'' +
                ", riqi='" + riqi + '\'' +
                '}';
    }
}
