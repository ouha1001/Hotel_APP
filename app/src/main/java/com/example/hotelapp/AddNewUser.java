package com.example.hotelapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

public class AddNewUser extends AppCompatActivity {
    Database db;
    EditText Tvorname,Tnachname,geburtstagK,telK,landK,emailK;
    Button btnadduser;
    DatePickerDialog.OnDateSetListener setListener;
    AlertDialog.Builder b;

    public  boolean checkDatum(int year){
        boolean res;
        Calendar calendar = Calendar.getInstance();
        if(year> calendar.get(Calendar.YEAR)){

            b = new AlertDialog.Builder(this);
            b.setCancelable(true);
            b.setTitle("Kunde Informationen");
            b.setMessage("Date unvalid");
            AlertDialog a=b.create();
            b.show();


            res = false;
        }
        else {
            res =true;
        }
        return  res;
    }
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
        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        geburtstagK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                     AddNewUser.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    checkDatum(year);
                    month= month+1;
                    String date = day+"/"+month+"/"+year;
                    geburtstagK.setText(date);



            }
        };

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