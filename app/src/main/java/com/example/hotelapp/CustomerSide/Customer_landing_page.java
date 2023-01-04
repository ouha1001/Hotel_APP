package com.example.hotelapp.CustomerSide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.hotelapp.MainActivity;
import com.example.hotelapp.R;

public class Customer_landing_page extends AppCompatActivity {
    
    Button my_res,extend_res,feedback,logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.landingpage_user);


        my_res = findViewById(R.id.my_reservation);
        my_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Customer_landing_page.this, My_Reservations.class);
                startActivity(intent);
                finish();
            }
        });

        extend_res = findViewById(R.id.Aufenthalt_verlängern);
        extend_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Customer_landing_page.this, Extend_Stay.class);
                startActivity(intent);
                finish();
            }
        });
        feedback.findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Customer_landing_page.this, Feedback.class);
                startActivity(intent);
                finish();
            }
        });
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Customer_landing_page.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(Customer_landing_page.this, "Successfully Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
