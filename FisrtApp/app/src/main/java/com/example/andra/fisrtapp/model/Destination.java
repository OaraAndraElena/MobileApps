package com.example.andra.fisrtapp.model;


import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.realm.RealmObject;

public class Destination extends RealmObject {
    private int id;
    private String place;
    private int price;
    private String date;

    public Destination(String place,int price,String date){
        this.place = place;
        this.price = price;
        this.date = date;
    }
    public Destination(){}
    public int getId(){return id;}
    public void setId(int id){ this.id = id;}
    public String getPlace(){ return place;}
    public void setPlace(String place) {this.place = place;}
    public int getPrice(){return price;}
    public void setPrice(int price) {this.price = price;}
    public String getDate(){return date;}
    public void setDate(String date) { this.date =date; }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id",id);
        result.put("place", place);
        result.put("price", price);
        result.put("date", date);
        return result;
    }

}