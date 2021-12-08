package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button guide;
    Button explore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guide = findViewById(R.id.guide);
        explore = findViewById(R.id.explore);

        guide.setOnClickListener(view ->{
           Intent intent = new Intent(MainActivity.this,UserGuide.class);
            try {
                startActivity(intent);
            }catch (ActivityNotFoundException e){
                Toast.makeText(MainActivity.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
            }
        });

        explore.setOnClickListener(view ->{
            Intent intent = new Intent( MainActivity.this,LogIn.class);
            try {
                startActivity(intent);
            }catch (ActivityNotFoundException e){
                Toast.makeText(MainActivity.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }
}