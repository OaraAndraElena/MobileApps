package com.OaraAndraElena.firstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EmailScreen extends Activity {

    EditText email_et, subject_et, text_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_screen);
        email_et = (EditText) findViewById(R.id.email_et);
        subject_et = (EditText) findViewById(R.id.subject_et);
        text_et = (EditText) findViewById(R.id.text_et);

        Button send_email_btn = (Button) findViewById(R.id.send_email_btn);
        send_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!email_et.getText().toString().isEmpty() && !subject_et.getText().toString().isEmpty() && !text_et.getText().toString().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setData(Uri.parse("mailto:"));
                    String[] mailto = {email_et.getText().toString()};
                    intent.putExtra(Intent.EXTRA_EMAIL, mailto);
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject_et.getText().toString());
                    intent.putExtra(Intent.EXTRA_TEXT, text_et.getText().toString());
                    intent.setType("message/rfc822");
                    intent = Intent.createChooser(intent, "Send Email");
                    startActivity(intent);
                }else{
                    new AlertDialog.Builder(EmailScreen.this)
                            .setTitle("Error")
                            .setMessage("Enter all fields")
                            .create().show();
                }
            }
        });

    }
}

