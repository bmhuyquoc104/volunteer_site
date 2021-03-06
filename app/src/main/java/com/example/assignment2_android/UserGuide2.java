package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class UserGuide2 extends AppCompatActivity {
    //Declare button
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide2);
        //Binding button to its value
        back = (Button) findViewById(R.id.back);
        // Switch to volunteer home activity
        back.setOnClickListener(view ->{
            Intent intent = new Intent(UserGuide2.this, VolunteerHome.class);
            // Delete all stacks before to avoid stack memory redundant and collapse between stacks
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            try {
                startActivity(intent);
            }catch (ActivityNotFoundException e){
                Toast.makeText(UserGuide2.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
            }
        });
    }
}