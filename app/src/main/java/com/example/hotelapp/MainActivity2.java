package com.example.hotelapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity2 extends AppCompatActivity {

    Button logoutbtn, addFeedback;
    TextView welcome;
    Database db;
    String Customer_id;
    String sessionId;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        db=new Database(this);
        ConstraintLayout constraintLayout = findViewById(R.id.homePageLayout);
        AnimationDrawable animationDrawable =(AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        Customer_id = getIntent().getStringExtra("SESSION_ID");
        //welcome=findViewById(R.id.hello_user);

         sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        //welcome.setText(" hallo "+sessionId);
        logoutbtn = findViewById(R.id.logout_btn);
        logoutbtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(MainActivity2.this, "Succesfully Logout", Toast.LENGTH_SHORT).show();
        });
        //welcome=findViewById(R.id.hello_user);
        //welcome.setText(" Welcome "+sessionId);
/*
        addFeedback = findViewById(R.id.feedback);
        addFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setTitle("Add Feedback");
                final EditText input = new EditText(MainActivity2.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String feedbackText =  input.getText().toString().trim();
                        Toast.makeText(MainActivity2.this , "Feedback abgegeben!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity2.this, "Canceled" , Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
            }
        });

 */
    }

    public void reservierungenBtn(View view) {
        Intent intent = new Intent(this, MyRow.class);
        intent.putExtra("EXTRA1_SESSION_ID", Customer_id);
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

        Cursor c=db.readAllData(sessionId);
        if(c.getCount()==0){
            Toast.makeText(this,"Empty"+sessionId,Toast.LENGTH_SHORT).show();
        }
        else {
            StringBuilder sb = new StringBuilder();
            while (c.moveToNext()){
                sb.append("\n+++++++++++++++++++++++++++ \n" );
                sb.append("\n ID : ").append(c.getString(0)).append(" \n ");
                sb.append(" Vorname : ").append(c.getString(1)).append(" \n ");
                sb.append(" Nachname : ").append(c.getString(2)).append(" \n ");
                sb.append(" Geburtsdatum : ").append(c.getString(3)).append(" \n ");
                sb.append(" Email : ").append(c.getString(4)).append(" \n ");
                sb.append(" Tel : " ).append(c.getString(5)).append(" \n ");
                sb.append(" Land : ").append(c.getString(6)).append(" \n ");
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