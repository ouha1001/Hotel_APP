package com.example.hotelapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
            Toast.makeText(this,"Empty",Toast.LENGTH_SHORT);
        }
        else {
            StringBuffer sb = new StringBuffer();
            while (c.moveToNext()){
                sb.append("+++++++++++++++++++++++++++ \n");
                sb.append(" ID "+c.getString(0)+" \n ");
                sb.append(" Vorname "+c.getString(1)+" \n ");
                sb.append(" Nachname "+c.getString(2)+" \n ");
                sb.append(" Geburtsdatum "+c.getString(3)+" \n ");
                sb.append(" Email "+c.getString(4)+" \n ");
                sb.append(" Tel "+c.getString(5)+" \n ");
                sb.append(" Land "+c.getString(6)+" \n ");


                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setCancelable(true);
                b.setTitle("Kunde Informationen");
                b.setMessage(sb.toString());
                AlertDialog a=b.create();

                b.show();


            }
        }

    }

}