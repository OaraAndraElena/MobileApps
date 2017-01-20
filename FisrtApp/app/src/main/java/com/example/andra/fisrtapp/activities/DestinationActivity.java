package com.example.andra.fisrtapp.activities;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.andra.fisrtapp.*;
import com.example.andra.fisrtapp.R;
import com.example.andra.fisrtapp.adapter.Adapter;
import com.example.andra.fisrtapp.controller.DestinationController;
import com.example.andra.fisrtapp.fragments.InputDialog;
import com.example.andra.fisrtapp.model.Destination;
import com.example.andra.fisrtapp.repo.RealMDestRepo;
import com.example.andra.fisrtapp.utils.Observer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.realm.Realm;


public class DestinationActivity extends AppCompatActivity implements Observer {
    ListView destList;
    ArrayAdapter adapter;
    RealMDestRepo repo;
    DestinationController controller;
    private DatabaseReference mDatabase;


    public void updateDestination(int id, String place,int price, String date){
        controller.updateDestination(id,place,price,date);
        adapter.notifyDataSetChanged();
    }

    public void addDestination(String place,int price, String date){
        System.out.println(controller.addDestination(place,price,date).getPlace());
        adapter.notifyDataSetChanged();
    }

    public void removeDestination(int id){
        controller.removeDestination(id);
        adapter.notifyDataSetChanged();
    }

    public void setupAdapter(){
        Realm realm = Realm.getDefaultInstance();
        repo = new RealMDestRepo(realm,mDatabase);
        repo.registerObserver(this);
        repo.loadDestinations();
        controller =  new DestinationController(repo);

        setContentView(R.layout.activity_destination);
        adapter = new Adapter(this, controller.getAllDestinations());
    }

    public void addButton(){
        Button addButton = (Button) findViewById(R.id.addDestination);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle state = new Bundle();
                state.putBoolean("addState", true);
                InputDialog addDialog = new InputDialog();
                addDialog.setArguments(state);
                addDialog.show(getFragmentManager(), "addDialog");
            }
        });
    }

    public void addListView(){
        destList = (ListView) findViewById(R.id.DestinationList);
        destList.setAdapter(adapter);
        destList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Destination currentDestination = (Destination) adapter.getItem(i);
                Bundle state = new Bundle();

                state.putBoolean("addState", false);
                state.putString("place", currentDestination.getPlace());
                state.putInt("price", currentDestination.getPrice());
                state.putString("date",currentDestination.getDate());
                state.putInt("id", currentDestination.getId());

                InputDialog editDialog = new InputDialog();
                editDialog.setArguments(state);
                editDialog.show(getFragmentManager(), "editDialog");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getApplicationContext());
        mDatabase=FirebaseDatabase.getInstance().getReference();
        setupAdapter();
        addButton();
        addListView();
    }

    @Override
    public void update(String message) {
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }
}

