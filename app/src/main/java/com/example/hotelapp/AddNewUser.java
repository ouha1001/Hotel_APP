package com.example.hotelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;

public class AddNewUser extends AppCompatActivity {
    Database db;
    EditText Tvorname,Tnachname,geburtstagK,telK,landK,emailK;
    Button btnadduser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db=new Database(this);
        Tvorname=(EditText)findViewById(R.id.vorname);
        Tnachname=(EditText)findViewById(R.id.nachname);
        geburtstagK=(EditText)findViewById(R.id.geburtsdatum);
        emailK=(EditText)findViewById(R.id.email);
        telK=(EditText)findViewById(R.id.phone);
        landK=(EditText)findViewById(R.id.country);
        btnadduser=findViewById(R.id.speichern);
        btnadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db=new Database(AddNewUser.this);
                db.addUser(Tvorname.getText().toString().trim(),Tnachname.getText().toString().trim()
                        ,geburtstagK.getText().toString().trim(),emailK.getText().toString().trim()
                        ,telK.getText().toString().trim(),landK.getText().toString().trim());
            }
        });


    }



}