package com.example.padurean.exam;

import com.example.padurean.exam.Domain.Item;



public interface MyCallback {

    void add(Item item);

    void showError(String s);

    void clear();

    void buyOffline(Integer id);
}
