package com.example.hotelapp;

import android.app.DatePickerDialog;
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


public class NewReservation extends AppCompatActivity {
    Database db;
    Zimmer bloc1=new Zimmer();

    AutoCompleteTextView room_id, roomtype, customer_id;
    Map<String, Integer> Kunde;
    TextView checkIn, checkOut;
    Button btnadd;


    DatePickerDialog.OnDateSetListener setListener, setListener_out;
    AlertDialog.Builder b;

    public boolean checkDatum(int year, int month, int DayofMonth) {

        Date date = new Date(year - 1900, month - 1, DayofMonth, 23, 59);

        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().compareTo(date) < 0;

    }

    public void storeAllClients() {
        Cursor c = db.readAllData();
        if (c.getCount() == 0) {
            Toast.makeText(this, "Keine Kunden ", Toast.LENGTH_SHORT).show();
        } else {
            while (c.moveToNext()) {
                Kunde.put(c.getString(1) + " " + c.getString(2)
                        , Integer.parseInt(c.getString(0)));
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource
                (this, R.array.TypeZimmer, R.layout.drop_down_item);
        roomtype.setAdapter(adapter1);
        roomtype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<Integer> adapter = new ArrayAdapter<>(NewReservation.this, R.layout.drop_down_item,
                        (bloc1.getRoomsbyType(roomtype.getText().toString())));
                room_id.setAdapter(adapter);
            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(NewReservation.this, R.layout.drop_down_item
                , new ArrayList<>(Kunde.keySet()));
        customer_id.setAdapter(adapter2);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(NewReservation.this, R.layout.drop_down_item,
                (bloc1.getRoomsbyType(roomtype.getText().toString())));
        room_id.setAdapter(adapter);

    }

    public long price(@NonNull String checkin, @NonNull String checkout) {
        long days = getDays(checkin, checkout);
        return (roomtype.getText().toString().equals("Single")) ? days * 10 : days * 20;
    }

    public boolean checkroomavailibility(String id) {

        Cursor cursor = db.getdatesbyroom(id);
        if (cursor.getCount() == 0) return true;
        while (cursor.moveToNext()) {
            if (isBetween(cursor.getString(0), checkIn.getText().toString(), checkOut.getText().toString())
                    || isBetween(cursor.getString(1), checkIn.getText().toString(), checkOut.getText().toString())
                    || isBetween(checkIn.getText().toString(), cursor.getString(0), cursor.getString(1))) {
                Toast.makeText(this, "Room " + id + " is not available !", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
    private static long getDays(@NonNull String checkin, @NonNull String checkout) {
        String[] a = checkin.split("-");
        String[] b = checkout.split("-");
        return (new Date(Integer.parseInt(b[0]) - 1900, Integer.parseInt(b[1]) - 1, Integer.parseInt(b[2])).getTime()
                - new Date(Integer.parseInt(a[0]) - 1900, Integer.parseInt(a[1]) - 1, Integer.parseInt(a[2])).getTime()) / 86400000;
    }

    private static Boolean isBetween(String TestDate,@NonNull String checkin, @NonNull String checkout) {
        String[] a = checkin.split("-");
        String[] b = checkout.split("-");
        String[] c = TestDate.split("-");
        long a1 = new Date(Integer.parseInt(a[0]) - 1900, Integer.parseInt(a[1]) - 1, Integer.parseInt(a[2])).getTime();
        long b1 = new Date(Integer.parseInt(b[0]) - 1900, Integer.parseInt(b[1]) - 1, Integer.parseInt(b[2])).getTime();
        long c1 = new Date(Integer.parseInt(c[0]) - 1900, Integer.parseInt(c[1]) - 1, Integer.parseInt(c[2])).getTime();
        return c1 < b1 && c1 > a1;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_reservation);
        customer_id = findViewById(R.id.id_Kund_res);
        checkIn = findViewById(R.id.checkin);
        checkOut = findViewById(R.id.checkout);
        btnadd = findViewById(R.id.add_reservation);
        room_id = findViewById(R.id.id_room);
        roomtype = findViewById(R.id.id_typeroom);
        roomtype.setText("Single");
        db = new Database(this);
        Kunde = new HashMap<>();
        storeAllClients();
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long price = price(checkIn.getText().toString(), checkOut.getText().toString());
                if (checkroomavailibility(room_id.getText().toString())) {
                    db.addReservation(Kunde.get(customer_id.getText().toString()), Integer.parseInt(room_id.getText().toString()),
                            checkIn.getText().toString(), checkOut.getText().toString(), (int) price);
                    b = new AlertDialog.Builder(NewReservation.this);
                    b.setCancelable(true);
                    b.setTitle("Total ");
                    b.setMessage("Gesamte Price : " + price);
                    AlertDialog a = b.create();
                    b.show();
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

                    String date = year + "-" + month + "-" + dayOfMonth;
                    checkIn.setText(date);
                } else {
                    checkIn.setText(null);
                    checkOut.setText(null);
                    Toast.makeText(NewReservation.this, "Please Select a valid Checkin Date!", Toast.LENGTH_SHORT).show();
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

                month++;
                String date = year + "-" + month + "-" + dayOfMonth;
                long x = getDays(checkIn.getText().toString(), date);
                if (checkDatum(year, month, dayOfMonth) && x > 0) {
                    checkOut.setText(date);
                } else {
                    checkOut.setText(null);
                    Toast.makeText(NewReservation.this, "Please Select a valid Checkout Date!", Toast.LENGTH_SHORT).show();

                }
            }
        };
    }
}