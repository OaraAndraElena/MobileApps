package com.example.padurean.exam.Domain;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;



public class Item extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private int quantity;
    private String type;
    private String status;

    public Item() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType(){
        return type;
    }

    public void setType(String t){
        type=t;
    }

    public Item(int id, String name, int quantity,String t,String status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
        this.type=t;
    }
}
