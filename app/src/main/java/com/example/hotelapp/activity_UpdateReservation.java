package com.example.hotelapp;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class activity_UpdateReservation extends AppCompatActivity {

    Database db;
    String itemselected;
    volatile Spinner  room_type, room_id;
    Map<Integer, String> rooms = new Zimmer().getRooms();
    Map<Integer,String> Kunde;
    TextView Reservation_id,Kunde_Id,checkIn,checkOut;
    long i, i1;
    Button btnupdate;
    DatePickerDialog.OnDateSetListener setListener, setListener_out;
    AlertDialog.Builder b;
    String R_id,K_Id,Z_Id,C_IN,C_OUT,price;

    public  void getAndSetIntentData(){
        if (getIntent().hasExtra("Id_Reservation") && getIntent().hasExtra("Id_Kunde")
            && getIntent().hasExtra("Id_Zimmer") && getIntent().hasExtra("check_In")
            && getIntent().hasExtra("check_Out") && getIntent().hasExtra("gesamt")) {
                R_id = getIntent().getStringExtra("Id_Reservation");
                K_Id = getIntent().getStringExtra("Id_Kunde");
                Z_Id = getIntent().getStringExtra("Id_Zimmer");
                C_IN = getIntent().getStringExtra("check_In");
                C_OUT = getIntent().getStringExtra("check_Out");
                price= getIntent().getStringExtra("gesamt");

                Reservation_id.setText(R_id);
                int k = Integer.parseInt(K_Id);
                Kunde_Id.setText(Kunde.get(k));
                checkIn.setText(C_IN);
                checkOut.setText(C_OUT);
                int zimmer= Integer.parseInt(Z_Id);
                room_type.setSelection(getRoom(rooms).get(zimmer-101));
                room_id.setSelection(zimmer);

        }
        else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
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

    public boolean checkDatum(int year, int month, int DayofMonth) {

        Date date = new Date(year, month, DayofMonth);

        Calendar calendar = Calendar.getInstance();

        return calendar.getTime().compareTo(date) < 0;

    }
    public void storeAllClients() {
        Cursor c = db.readAllData();
        if (c.getCount() == 0) {
            Toast.makeText(this, "Keine Kunden ", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()) {
                Kunde.put( Integer.parseInt(c.getString(0)), c.getString(1) + " " + c.getString(2));

            }
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);

        db = new Database(this);
        Kunde = new HashMap<>();
        storeAllClients();
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        Reservation_id=findViewById(R.id.id_reservation);
        Kunde_Id=findViewById(R.id.id_Kund);
        checkIn = findViewById(R.id.checkin);
        checkOut = findViewById(R.id.checkout);
        btnupdate=findViewById(R.id.Update_reservation);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        room_type=findViewById(R.id.id_typeroom);
        room_id=findViewById(R.id.id_room);


        //Check IN
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity_UpdateReservation.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity_UpdateReservation.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener_out, year, month, day);
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


        getAndSetIntentData();









    }
}