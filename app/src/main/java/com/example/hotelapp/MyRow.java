package com.example.hotelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MyRow extends AppCompatActivity {
    RecyclerView recyclerView;
    Database db;


    ArrayList<Integer> id_reservation,id_kunde,id_zimmer,gesamt;
    ArrayList<String> datein,dateout;
    CustomAdapter customAdapter;

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

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_row);
        recyclerView=findViewById(R.id.recyclerView);
        db = new Database(MyRow.this);
        id_reservation=new ArrayList<>();
        id_kunde= new ArrayList<>();
        id_zimmer = new ArrayList<>();
        datein =new ArrayList<>();
        dateout=new ArrayList<>();
        gesamt=new ArrayList<>();




        storeAllDataReservation();

        customAdapter = new CustomAdapter(MyRow.this, id_reservation,id_kunde,id_zimmer,datein,dateout,gesamt);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyRow.this));

    }
}