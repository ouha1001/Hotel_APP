package com.example.hotelapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class HomePageActivity extends AppCompatActivity {

    Button logoutbtn;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);
        db=new Database(this);
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
        Intent intent = new Intent(this, MyRow.class);
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

    public void ShowClients(View view){

        Cursor c=db.readAllData();
        if(c.getCount()==0){
            Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            StringBuilder sb = new StringBuilder();
            while (c.moveToNext()){
                sb.append("+++++++++++++++++++++++++++ \n");
                sb.append(" ID ").append(c.getString(0)).append(" \n ");
                sb.append(" Vorname ").append(c.getString(1)).append(" \n ");
                sb.append(" Nachname ").append(c.getString(2)).append(" \n ");
                sb.append(" Geburtsdatum ").append(c.getString(3)).append(" \n ");
                sb.append(" Email ").append(c.getString(4)).append(" \n ");
                sb.append(" Tel ").append(c.getString(5)).append(" \n ");
                sb.append(" Land ").append(c.getString(6)).append(" \n ");
            }

                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setCancelable(true);
                b.setTitle("Kunde Informationen");
                b.setMessage(sb.toString());
                b.create();
                b.show();



        }

    }

}