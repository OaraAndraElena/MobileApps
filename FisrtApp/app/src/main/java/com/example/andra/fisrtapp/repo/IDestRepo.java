package com.example.andra.fisrtapp.repo;

/**
 * Created by Andra on 17.01.2017.
 */

import com.example.andra.fisrtapp.model.Destination;

import java.util.List;


public interface IDestRepo {
    Destination addDestination(Destination d);
    void removeDestination(int id);
    Destination updateDestination(Destination  dest);
    Destination getDestination(int id);
    List<Destination> getAllDest();

}