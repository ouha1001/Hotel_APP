package com.example.hotelapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class activity_UpdateReservation extends AppCompatActivity {

    Database db;
    String itemselected;
    AutoCompleteTextView room_type, room_id;

    Map<Integer, String> rooms = new Zimmer().getRooms();
    Map<Integer, String> Kunde;
    TextView Reservation_id, Kunde_Id, checkIn, checkOut;
    long i, i1;
    Button btnupdate,btndelete;
    DatePickerDialog.OnDateSetListener setListener, setListener_out;
    AlertDialog.Builder b;
    String R_id, K_Id, Z_Id, C_IN, C_OUT, price;

    public void storeAllClients() {
        Cursor c = db.readAllData();
        if (c.getCount() == 0) {
            Toast.makeText(this, "Keinen Kunden", Toast.LENGTH_SHORT).show();        } else {
            while (c.moveToNext()) {
                Kunde.put(Integer.parseInt(c.getString(0)), c.getString(1) + " " + c.getString(2));
            }
        }

    }

    public void getAndSetIntentData() {
        if (getIntent().hasExtra("Id_Reservation") && getIntent().hasExtra("Id_Kunde")
                && getIntent().hasExtra("Id_Zimmer") && getIntent().hasExtra("check_In")
                && getIntent().hasExtra("check_Out") && getIntent().hasExtra("gesamt")) {
            //getting data from Intent.
            R_id = getIntent().getStringExtra("Id_Reservation");
            K_Id = getIntent().getStringExtra("Id_Kunde");
            Z_Id = getIntent().getStringExtra("Id_Zimmer");
            C_IN = getIntent().getStringExtra("check_In");
            C_OUT = getIntent().getStringExtra("check_Out");
            price = getIntent().getStringExtra("gesamt");
            //SettingData
            Reservation_id.setText(R_id);
            int k = Integer.parseInt(K_Id);
            Kunde_Id.setText(Kunde.get(k));
            checkIn.setText(C_IN);
            checkOut.setText(C_OUT);
            room_id.setText(Z_Id);
            room_type.setText(rooms.get(Integer.valueOf(Z_Id)));
            Toast.makeText(this, "" + Z_Id, Toast.LENGTH_SHORT).show();


        } else {
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
        Date date = new Date(year - 1900, month - 1, DayofMonth, 23, 59);
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().compareTo(date) < 0;
    }



    @Override
    protected void onResume() {
        super.onResume();
        ArrayAdapter<CharSequence> adapter_ = ArrayAdapter.createFromResource
                (this, R.array.TypeZimmer, R.layout.drop_down_item);
        room_type.setAdapter(adapter_);
        room_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(activity_UpdateReservation.this, R.layout.drop_down_item,
                        (rooms.keySet().stream().filter(c -> Objects.equals(rooms.get(c), room_type.getText().toString())).collect(Collectors.toList())));
                room_id.setAdapter(adapter);
                Toast.makeText(activity_UpdateReservation.this, "test :" + room_id.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ArrayAdapter<Integer> adapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            adapter = new ArrayAdapter<Integer>(activity_UpdateReservation.this, R.layout.drop_down_item,
                    (rooms.keySet().stream().filter(c -> Objects.equals(rooms.get(c), room_type.getText().toString())).collect(Collectors.toList())));
        }
        room_id.setAdapter(adapter);
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
        Reservation_id = findViewById(R.id.id_reservation);
        Kunde_Id = findViewById(R.id.id_Kund);
        checkIn = findViewById(R.id.checkin);
        checkOut = findViewById(R.id.checkout);
        btnupdate = findViewById(R.id.Update_reservation);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c=db.storeAllDataReservation(room_id.getText().toString());

                if(c.getCount()>0){
                    Toast.makeText(activity_UpdateReservation.this, "is reserved ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(activity_UpdateReservation.this, "Free Room", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btndelete = findViewById(R.id.Delete_reservation);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.DeleteReservation(Reservation_id.getText().toString());
                Intent intent = new Intent(activity_UpdateReservation.this,MyRow.class);
                startActivity(intent);
                finish();
            }
        });


        room_type = findViewById(R.id.id_typeroom);
        room_id = findViewById(R.id.id_room);

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

                month++;
                Date tag = new Date(year, month, dayOfMonth);
                i1 = tag.getTime();
                if (checkDatum(year, month, dayOfMonth) && i1 > i) {

                    String date = dayOfMonth + "/" + month + "/" + year;
                    checkOut.setText(date);
                } else {
                    checkOut.setText(null);
                    Toast.makeText(activity_UpdateReservation.this, "Please Select a valid Checkout Date!", Toast.LENGTH_SHORT).show();
                }
            }
        };


        getAndSetIntentData();

    }
}