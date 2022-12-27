package com.example.hotelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;

public class AddNewUser extends AppCompatActivity {
    Database db;
    EditText Tvorname,Tnachname,geburtstagK,telK,landK,emailK;


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


    }

    public void add_User(View view) {

        String txtV,txtN,txtG,txtE,txtP,txtL;
        txtV= Tvorname.toString();
        txtN=Tnachname.toString();
        txtG=geburtstagK.toString();
        txtE=emailK.toString();
        txtP=telK.toString();
        txtL=landK.toString();

        boolean res=db.addUser(txtV,txtN, Date.valueOf(txtG),txtE,Integer.valueOf(txtP),txtL);

        if (res== true){
            Toast.makeText(this,"Add Done",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Add Failed",Toast.LENGTH_SHORT).show();
        }


    }
}