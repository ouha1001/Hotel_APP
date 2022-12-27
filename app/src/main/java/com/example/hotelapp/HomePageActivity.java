package com.example.hotelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity {

    Button logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);

        ConstraintLayout constraintLayout = findViewById(R.id.homePageLayout);
        AnimationDrawable animationDrawable =(AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


        logoutbtn = findViewById(R.id.logout_btn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(HomePageActivity.this, "Succesfully Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void reservierungenBtn(View view) {
        Intent intent = new Intent(this, ReservierungenList.class);
        startActivity(intent);
    }

    public void newReservationBtn(View view){
        Intent intent = new Intent(this, NewReservation.class);
        startActivity(intent);
    }

    public void newUser(View view){
        Intent intent = new Intent(this, AddNewUser.class);
        startActivity(intent);
    }

}