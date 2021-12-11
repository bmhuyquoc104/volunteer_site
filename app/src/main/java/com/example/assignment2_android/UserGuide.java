package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.assignment2_android.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserGuide extends AppCompatActivity {
    MainActivity activity;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(view ->{
           Intent intent = new Intent(UserGuide.this, MainActivity.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            try {
                startActivity(intent);
            }catch (ActivityNotFoundException e){
                Toast.makeText(UserGuide.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }
}