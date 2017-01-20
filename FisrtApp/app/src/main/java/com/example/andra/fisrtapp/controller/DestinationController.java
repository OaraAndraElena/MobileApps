package com.example.andra.fisrtapp.controller;

/**
 * Created by Andra on 17.01.2016.
 */

import com.example.andra.fisrtapp.model.Destination;
import com.example.andra.fisrtapp.repo.IDestRepo;

import java.util.List;

public class DestinationController {
    private IDestRepo repo;

    public DestinationController(IDestRepo repo) {
        this.repo = repo;
    }

    public Destination addDestination(String place, int price, String date){
        return repo.addDestination(new Destination(place,price,date));
    }

    public void removeDestination(int id){
        repo.removeDestination(id);
    }

    public Destination updateDestination(int id,String place, int price, String date){
        Destination dest = new Destination(place,price,date);
        dest.setId(id);
        return repo.updateDestination(dest);
    }

    public Destination getDestination(int id){
        return repo.getDestination(id);
    }

    public List<Destination> getAllDestinations(){
        return repo.getAllDest();
    }

}
