package com.example.hotelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReservierungenList extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_row);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //textView = (TextView) findViewById(R.id.order_1);
        //textView.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
              //  Intent intent = new Intent(ReservierungenList.this, OrderDetails.class);
                //startActivity(intent);
            //}
        //});
    }



}