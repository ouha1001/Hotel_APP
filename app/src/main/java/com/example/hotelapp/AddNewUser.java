package com.example.hotelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
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
        Tvorname=findViewById(R.id.vorname);
        Tnachname=findViewById(R.id.nachname);
        geburtstagK=findViewById(R.id.geburtsdatum);
        emailK=findViewById(R.id.email);
        telK=findViewById(R.id.phone);
        landK=findViewById(R.id.country);
        btnadduser=findViewById(R.id.speichern);
        btnadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database db=new Database(AddNewUser.this);
                if(validateEmailAddress(emailK)){
                db.addUser(Tvorname.getText().toString().trim(),Tnachname.getText().toString().trim()
                        ,geburtstagK.getText().toString().trim(),emailK.getText().toString().trim()
                        ,telK.getText().toString().trim(),landK.getText().toString().trim());
                setContentView(R.layout.activity_home_page2);

                }
            }
        });



    }

    private boolean validateEmailAddress (EditText  email) {
        String email_input = email.getText().toString();
        if(!email_input.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email_input).matches()){
            return true;
        } else {
            Toast.makeText(this,"Invalid Email Address!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }



}