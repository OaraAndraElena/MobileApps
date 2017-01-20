package com.example.andra.fisrtapp.repo;
import android.widget.ArrayAdapter;
import com.example.andra.fisrtapp.model.Destination;
import com.example.andra.fisrtapp.utils.Observer;
import com.example.andra.fisrtapp.utils.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealMDestRepo implements IDestRepo, Subject {

    private Realm realm;
    private DatabaseReference fireBaseDb;
    private List<Destination> dests = new ArrayList<>();
    private List<Observer> observers =  new ArrayList<>();

    private Destination fromMap(Map<String,Object> map){
        Destination dest= new Destination();
        dest.setId(Integer.parseInt(map.get("id").toString()));
        dest.setPlace(map.get("place").toString());
        dest.setPrice(Integer.parseInt(map.get("price").toString()));
        dest.setDate(map.get("date").toString());
        return dest;
    }
    public void loadDestinations() {
        fireBaseDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " possible destinations");
                dests.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    for (DataSnapshot postSnapshot2: postSnapshot.getChildren()) {
                        Destination pet = fromMap((HashMap<String,Object> )postSnapshot2.getValue());
                        dests.add(pet);
                    }
                }
                System.out.println(dests);
                notifyObservers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public RealMDestRepo(Realm realm, DatabaseReference fireBaseDb) {
        this.realm = realm;
        this.fireBaseDb = fireBaseDb;
    }

    @Override
    public Destination addDestination(Destination dest) {
        List<Destination> dests = getAllDest();
        dest.setId(dests.size() > 0 ? dests.get(dests.size() - 1).getId() + 1 : 0);
        realm.beginTransaction();
        realm.insert(dest);
        realm.commitTransaction();
        Map<String,Object> added = new HashMap<>();
        added.put("/destinations/" + dest.getId(),dest.toMap());
        fireBaseDb.updateChildren(added);
        return dest;
    }

    @Override
    public void removeDestination(int id) {
        realm.beginTransaction();
        realm.where(Destination.class).equalTo("id",id).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        fireBaseDb.child("/destinations/"+String.valueOf(id)).removeValue();
    }

    @Override
    public Destination updateDestination(Destination dest) {
        realm.beginTransaction();
        RealmResults<Destination> rows = realm.where(Destination.class).equalTo("id",dest.getId()).findAll();
        for (Destination a: rows){
            a.setPlace(dest.getPlace());
            a.setPrice(dest.getPrice());
            a.setDate(dest.getDate());
        }
        realm.commitTransaction();
        Map<String,Object> added = new HashMap<>();
        added.put("/destinations/" + dest.getId(),dest.toMap());
        fireBaseDb.updateChildren(added);
        return dest;
    }

    @Override
    public Destination getDestination(int id) {
        return realm.where(Destination.class).equalTo("id",id).findFirst();
    }

    @Override
    public List<Destination> getAllDest() {
        return dests;
    }


    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update("Update destination");
        }
    }

    @Override
    public void notifyObservers(String designName, String typeOfChange) {

    }
}