package com.example.hotelapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private Button move;

    public String getCustomer_Id() {
        return Customer_Id;
    }

    public void setCustomer_Id(String customer_Id) {
        Customer_Id = customer_Id;
    }

    private String Customer_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database db =new Database(this);

        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable =(AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();


        TextView username = findViewById(R.id.user_name);
        TextView password = findViewById(R.id.password);

        MaterialButton loginBtn = findViewById(R.id.loginbtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(MainActivity.this, "za3ma ? " + db.getdate("2023-01-15","2023-01-20"), Toast.LENGTH_SHORT).show();
                Intent intent,intent1;
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                 Toast.makeText(MainActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                  intent = new Intent(MainActivity.this, HomePageActivity.class);
                 startActivity(intent);
             } else {
                 Database db = new Database(MainActivity.this);
                 String[] strings = username.getText().toString().split("\\.");
                 if (!db.checkusername(strings[0])) {
                     //Toast.makeText(MainActivity.this, "USER NOT FOUND", Toast.LENGTH_SHORT).show();
                 } else {
                     if ((db.checkpassword(strings[1], password.getText().toString().replace("-", "")))) {
                         //Toast.makeText(MainA, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();

                          intent1 = new Intent(MainActivity.this, MainActivity2.class);
                         startActivity(intent1);
                         setCustomer_Id(strings[0]);

                     } else {
                         Toast.makeText(MainActivity.this, "masd9atch", Toast.LENGTH_SHORT).show();
                     }
                 }
             }
            }
        });
    }
}