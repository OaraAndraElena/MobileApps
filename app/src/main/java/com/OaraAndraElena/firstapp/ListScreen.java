package com.OaraAndraElena.firstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListScreen extends Activity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_screen);
        list  = (ListView) findViewById(R.id.list);
        final String[] items = {"Item1", "Item2", "Item3"};
        list.setAdapter(new ArrayAdapter<String>(ListScreen.this,
                android.R.layout.simple_list_item_1, items));

        int position = getIntent().getIntExtra("position", -1);
        if(position != -1){
            items[position] = getIntent().getStringExtra("item");
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = items[position];
                Intent in = new Intent(ListScreen.this, ListItemScreen.class);
                in.putExtra("item", item);
                in.putExtra("position", position);
                startActivity(in);
            }
        });

    }
}
