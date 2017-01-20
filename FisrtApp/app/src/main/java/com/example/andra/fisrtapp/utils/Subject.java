package com.example.andra.fisrtapp.utils;

/**
 * Created by Andra on 17.01.2017.
 */

public interface Subject {
    public void registerObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyObservers();

    public void notifyObservers(String designName, String typeOfChange);
}