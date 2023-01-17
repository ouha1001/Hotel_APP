package com.example.hotelapp;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyRow extends AppCompatActivity {
    RecyclerView recyclerView;
    Database db;
    Map<Integer, String> Kunde;
    String sessionId;




    ArrayList<Integer> id_reservation,id_kunde,id_zimmer,gesamt;
    ArrayList<String> datein,dateout,vorname,nachname;
    CustomAdapter customAdapter;
    public void storeAllDataReservation(String id) {
        Cursor c = db.readAllDataReservationbyId(id);
        if (c.getCount() == 0) {
            Toast.makeText(this, "Keine Reservation ", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()) {
                id_reservation.add(Integer.valueOf(c.getString(0)));
                id_kunde.add(Integer.valueOf(c.getString(1)));
                id_zimmer.add(Integer.valueOf(c.getString(2)));
                gesamt.add(Integer.valueOf(c.getString(5)));
                datein.add(c.getString(3));
                dateout.add(c.getString(4));
                vorname.add(c.getString(6));
                nachname.add(c.getString(7));
            }
        }

    }
    public void storeAllClients() {
        Cursor c = db.readAllData();
        if (c.getCount() == 0) {
            Toast.makeText(this, "Keinen Kunden", Toast.LENGTH_SHORT).show();        } else {
            while (c.moveToNext()) {
                Kunde.put(Integer.parseInt(c.getString(0)), c.getString(1) + " " + c.getString(2));
            }
        }

    }
    public  void storeAllDataReservation(){
        Cursor c = db.readAllDataReservation();
        if (c.getCount()==0){
            Toast.makeText(this, "Keine Reservation ", Toast.LENGTH_SHORT).show();
        }
        else {
            while (c.moveToNext()){
                id_reservation.add(Integer.valueOf(c.getString(0)));
                id_kunde.add(Integer.valueOf(c.getString(1)));
                id_zimmer.add(Integer.valueOf(c.getString(2)));
                gesamt.add(Integer.valueOf(c.getString(5)));
                datein.add(c.getString(3));
                dateout.add(c.getString(4));
                vorname.add(c.getString(6));
                nachname.add(c.getString(7));


            }
        }

    }

   // @Override
   // protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
   //     super.onActivityResult(requestCode, resultCode, data);
   //     if (requestCode == 1) {
   //         recreate();
   //     }
   // }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_row);
        recyclerView = findViewById(R.id.recyclerView);
        db = new Database(MyRow.this);

        Kunde = new HashMap<>();
        storeAllClients();
        id_reservation=new ArrayList<>();
        id_kunde= new ArrayList<>();
        sessionId = getIntent().getStringExtra("EXTRA1_SESSION_ID");
        id_zimmer = new ArrayList<>();
        datein = new ArrayList<>();
        dateout = new ArrayList<>();
        gesamt=new ArrayList<>();
        vorname=new ArrayList<>();
        nachname=new ArrayList<>();

        if (!(sessionId == null)) {
            storeAllDataReservation(sessionId);
        } else {
            storeAllDataReservation();
        }


        customAdapter = new CustomAdapter(MyRow.this,this, id_reservation,id_kunde,id_zimmer,datein,dateout,gesamt,vorname,nachname);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyRow.this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }


}