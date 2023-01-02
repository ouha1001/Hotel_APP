package com.example.hotelapp;

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
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class NewReservation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Database db;
    String itemselected;
    volatile Spinner spinner,spinner1,spinner2;
    Map<Integer, String> rooms =new Zimmer().getRooms();
    Map<String, Integer> Kunde;
    EditText checkIn,checkOut;
    long i,i1;
    Button btnadd;


    DatePickerDialog.OnDateSetListener setListener,setListener_out;
    AlertDialog.Builder b;

    public  boolean checkDatum(int year){
        boolean res;
        Calendar calendar = Calendar.getInstance();
        if(year< calendar.get(Calendar.YEAR)){

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


    public  void storeAllClients(){
        Cursor c = db.readAllData();
        if (c.getCount()==0){
            Toast.makeText(this, "Keine Kunden ", Toast.LENGTH_SHORT).show();
        }
        else {
            while (c.moveToNext()){
                Kunde.put(c.getString(1)+" "+c.getString(2), Integer.parseInt(c.getString(0)));

            }
        }

    }
    public List<Integer> getRoom(Map<Integer,String> rooms){
        List<Integer> spinnerArray = new ArrayList<>(rooms.size());

        if (itemselected==null){
            spinnerArray.addAll(rooms.keySet());
        } else{
        for (Integer list : rooms.keySet()  ) {

            if (Objects.equals(rooms.get(list), itemselected)){
                spinnerArray.add(list);}
            }}
        return spinnerArray;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        db = new Database(this);
        Kunde = new HashMap<>();
        storeAllClients();
        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);
        checkIn=findViewById(R.id.checkin);
        checkOut=findViewById(R.id.checkout);
        btnadd=findViewById(R.id.add_reservation);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long Result =(i1-i)/86400000;
                long price = (itemselected.equals("Single")) ? 10 * Result : 20 * Result;
                db.addReservation(Kunde.get(spinner2.getSelectedItem().toString()),Integer.parseInt(spinner.getSelectedItem().toString()),checkIn.toString(),checkOut.toString(),(int)price);

            }
        });
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewReservation.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
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
                Date tag = new Date(year,month,dayOfMonth);
                String date = dayOfMonth+"/"+month+"/"+year;
                i= tag.getTime();
                Toast.makeText(NewReservation.this, "psss "+i, Toast.LENGTH_SHORT).show();
                //String days = tag.getDay()+"/"+tag.getMonth()+"/"+ tag.getYear();
                checkIn.setText(date);



            }
        };
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NewReservation.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener_out,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener_out = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                checkDatum(year);
                month= month+1;
                Date tag = new Date(year,month,dayOfMonth);
                String date = dayOfMonth+"/"+month+"/"+year;
                i1= tag.getTime();
                //String days = tag.getDay()+"/"+tag.getMonth()+"/"+ tag.getYear();
                checkOut.setText(date);

            }
        };
        //Type Zimmer
        spinner1 =findViewById(R.id.id_typeroom);
        ArrayAdapter<CharSequence> adapter_= ArrayAdapter.createFromResource(this,R.array.TypeZimmer , android.R.layout.simple_spinner_item);
        adapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter_);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemselected=spinner1.getSelectedItem().toString();
                spinner =findViewById(R.id.id_room);
                ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(NewReservation.this , android.R.layout.simple_spinner_item,getRoom(rooms));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(NewReservation.this);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2 =findViewById(R.id.id_Kund);
        ArrayAdapter<String> adapter2= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(Kunde.keySet()));
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