package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;
    TextView register, forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = findViewById(R.id.userNamLogIn);
        password = findViewById(R.id.passwordLogIn);
        login = findViewById(R.id.signIn);

        forgotPassword = findViewById(R.id.forgotPassword);
    }

    public void switchToRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(LogIn.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
        }
    }

    public void signIn(View view) {
        Intent intent = new Intent(this,VolunteerHome.class);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("plain/text");
        String inputName = username.getText().toString();
        String inputPassWord = password.getText().toString();
        intent.putExtra("userInput",inputName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(LogIn.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
        }
    }
}