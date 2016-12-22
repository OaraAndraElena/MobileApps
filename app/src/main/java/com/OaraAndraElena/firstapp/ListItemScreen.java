package com.OaraAndraElena.firstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class ListItemScreen extends Activity {
    EditText item_text;
    Intent myIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_screen);
        item_text = (EditText) findViewById(R.id.item_text);
        Button save_button = (Button) findViewById(R.id.save_button);
        myIn = getIntent();
        String item = myIn.getStringExtra("item");
        item_text.setText(item);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ListItemScreen.this, ListScreen.class);
                in.putExtra("item", item_text.getText().toString());
                in.putExtra("position", myIn.getIntExtra("position", -1));
                startActivity(in);
            }
        });

    }
}
