package com.example.hotelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class NewReservation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Database db;
    String itemselected;
    Spinner spinner, spinner1, spinner2;
    Map<Integer, String> rooms = new Zimmer().getRooms();
    Map<String, Integer> Kunde;
    TextView checkIn, checkOut;
    long i, i1;
    Button btnadd;


    DatePickerDialog.OnDateSetListener setListener, setListener_out;
    AlertDialog.Builder b;

    public boolean checkDatum(int year, int month, int DayofMonth) {

        Date date = new Date(year, month, DayofMonth);

        Calendar calendar = Calendar.getInstance();

        return calendar.getTime().compareTo(date) < 0;

    }

    public boolean checkPeriod(long in, long out) {
        boolean r;
        long temp = (out - in) / 86400000;
        if (temp < 0) {
            b = new AlertDialog.Builder(this);
            b.setCancelable(true);
            b.setTitle("Falsche Information");
            b.setMessage("Check Out und Check In sind nicht Richtig !!");
            AlertDialog a = b.create();
            b.show();

            r = true;
        } else {
            r = false;
        }
        return r;
    }

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

    public List<Integer> getRoom(@NonNull Map<Integer, String> rooms) {
        List<Integer> spinnerArray = new ArrayList<>(rooms.size());

        if (itemselected == null) {
            spinnerArray.addAll(rooms.keySet());
        } else {
            for (Integer list : rooms.keySet()) {

                if (Objects.equals(rooms.get(list), itemselected)) {
                    spinnerArray.add(list);
                }
            }
        }
        return spinnerArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);




        db = new Database(this);
        Kunde = new HashMap<>();
        storeAllClients();
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        checkIn = findViewById(R.id.checkin);
        checkOut = findViewById(R.id.checkout);
        btnadd = findViewById(R.id.add_reservation);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long Result = (i1 - i) / 86400000;
                long price = (itemselected.equals("Single")) ? 10 * Result : 20 * Result;
                if (price > 0) {
                    db.addReservation(Kunde.get(spinner2.getSelectedItem().toString()), Integer.parseInt(spinner.getSelectedItem().toString()), checkIn.toString(), checkOut.toString(), (int) price);
                    b = new AlertDialog.Builder(NewReservation.this);
                    b.setCancelable(true);
                    b.setTitle("Total ");
                    b.setMessage("Gesamte Price : " + price);
                    AlertDialog a = b.create();
                    b.show();
                } else {
                    b = new AlertDialog.Builder(NewReservation.this);
                    b.setCancelable(true);
                    b.setTitle("Falsche Information");
                    b.setMessage("Check Out und Check In sind nicht Richtig !!");
                    AlertDialog a = b.create();
                    b.show();
                    checkIn.setText(null);
                    checkOut.setText(null);
                }


            }
        });
        //Check IN
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewReservation.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                if (checkDatum(year, month, dayOfMonth)) {
                    Date tag = new Date(year, month, dayOfMonth);
                    String date = dayOfMonth + "/" + month + "/" + year;
                    i = tag.getTime();
                    checkIn.setText(date);
                } else {
                    checkIn.setText(null);
                    checkOut.setText(null);
                }


            }
        };
        //Check OUt
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewReservation.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener_out, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener_out = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                if (checkDatum(year, month, dayOfMonth)) {

                    Date tag = new Date(year, month, dayOfMonth);
                    String date = dayOfMonth + "/" + month + "/" + year;
                    i1 = tag.getTime();
                    //String days = tag.getDay()+"/"+tag.getMonth()+"/"+ tag.getYear();
                    checkOut.setText(date);
                } else {
                    checkIn.setText(" ");
                    checkOut.setText(" ");
                }


            }
        };
        //Type Zimmer
        spinner1 = findViewById(R.id.id_typeroom);
        ArrayAdapter<CharSequence> adapter_ = ArrayAdapter.createFromResource(this, R.array.TypeZimmer, android.R.layout.simple_spinner_item);
        adapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter_);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemselected = spinner1.getSelectedItem().toString();
                spinner = findViewById(R.id.id_room);
                ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(NewReservation.this, android.R.layout.simple_spinner_item, getRoom(rooms));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(NewReservation.this);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner Kunde
        spinner2 = findViewById(R.id.id_Kund);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(Kunde.keySet()));
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}