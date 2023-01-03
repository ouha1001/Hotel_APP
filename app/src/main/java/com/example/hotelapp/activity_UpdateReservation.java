package com.example.hotelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
public class activity_UpdateReservation extends AppCompatActivity {

    Database db;
    String itemselected;
    volatile Spinner  room_type, Kunde_name,room_id;
    Map<Integer, String> rooms = new Zimmer().getRooms();
    Map<String, Integer> Kunde;
    EditText checkIn, checkOut;
    TextView Reservation_id;
    long i, i1;
    Button btnupdate;

    public void storeAllClients() {
        Cursor c = db.readAllData();
        if (c.getCount() == 0) {
            Toast.makeText(this, "Keine Kunden ", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()) {
                Kunde.put(c.getString(1) + " " + c.getString(2), Integer.parseInt(c.getString(0)));

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);
        db = new Database(this);
        Kunde = new HashMap<>();
        NewReservation nr =new NewReservation();
        nr.storeAllClients();






    }
}