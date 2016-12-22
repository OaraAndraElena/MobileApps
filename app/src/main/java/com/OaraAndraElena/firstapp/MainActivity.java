package com.OaraAndraElena.firstapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button email_btn = (Button) findViewById(R.id.email_btn);
        Button list_btn = (Button) findViewById(R.id.list_btn);

        email_btn.setOnClickListener(this);
        list_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.email_btn:
                Intent emailScreen = new Intent(this, EmailScreen.class);
                startActivity(emailScreen);
                break;
            case R.id.list_btn:
                Intent listScreen = new Intent(this, ListScreen.class);
                startActivity(listScreen);
                break;
        }
    }
}
