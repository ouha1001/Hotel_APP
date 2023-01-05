package com.example.hotelapp.CustomerSide;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.hotelapp.MainActivity;
import com.example.hotelapp.R;

public class Customer_landing_page extends AppCompatActivity {
    
    Button my_res,extend_res,feedback;
    Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.landingpage_user);
        ConstraintLayout constraintLayout = findViewById(R.id.homePageLayout);
        AnimationDrawable animationDrawable =(AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


        logout.findViewById(R.id.button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Customer_landing_page.this, "logout successful", Toast.LENGTH_SHORT).show();
            }
        });

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
    }
}
