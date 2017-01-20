package com.example.andra.fisrtapp.repo;

/**
 * Created by Andra on 17.01.2017.
 */

import com.example.andra.fisrtapp.model.Destination;

import java.util.ArrayList;
import java.util.List;



public class DestinationRepo implements IDestRepo{
    private List<Destination> dest;
    private String filename;

    public DestinationRepo() {
        this.dest =  new ArrayList<>();
    }

    public DestinationRepo(String filename){
        this.filename = filename;
    }

    @Override
    public Destination addDestination(Destination destination) {
        destination.setId(dest.size());
        if (dest.add(destination)){
            return dest.get(dest.size() - 1);
        }
        return null;
    }

    @Override
    public void removeDestination(int id) {
        dest.remove(id);
    }

    @Override
    public Destination updateDestination(Destination destination) {
        dest.set(destination.getId(), destination);
        return dest.get(destination.getId());
    }

    @Override
    public Destination getDestination(int id) {
        return dest.get(id);
    }

    @Override
    public List<Destination> getAllDest() {
        return dest;
    }


}