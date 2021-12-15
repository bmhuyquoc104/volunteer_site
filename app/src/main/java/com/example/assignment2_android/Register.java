package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment2_android.databaseFirestore.UserDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    //Declare edittext
    EditText username, password, confirmPassword,age,email;
    //Declare button
    Button register;
    ProgressDialog pd;
    //Declare Firebase
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pd = new ProgressDialog(this);
        //Initialize value for firestore
        db = FirebaseFirestore.getInstance();

        //Binding edit text and button to its value
        username = findViewById(R.id.userNameRegister);
        password = findViewById(R.id.passwordRegister);
        age = findViewById(R.id.userAgeRegister);
        email = findViewById(R.id.emailRegister);
        confirmPassword = findViewById(R.id.confirmPasswordRegister);
        register = findViewById(R.id.registerButton);

        //Function register new User
        register.setOnClickListener(view ->{
            String inputUserName = username.getText().toString();
            String inputUserPassword = password.getText().toString();
            String inputUserConfirmPassword = confirmPassword.getText().toString();
            String inputUserEmail = email.getText().toString();
            String inputUserAge = age.getText().toString();

            //Call method to register new user by id,email,age,password
            UserDatabase.registerUser(inputUserName,inputUserEmail,inputUserAge,Register.this,inputUserPassword,inputUserConfirmPassword,pd,db);
        });
    }

    //Switch to login activity
    public void switchToLogIn(View view) {
        Intent intent = new Intent(this, LogIn.class);
        // Delete all stacks before to avoid stack memory redundant and collapse between stacks
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
        }
    }
}