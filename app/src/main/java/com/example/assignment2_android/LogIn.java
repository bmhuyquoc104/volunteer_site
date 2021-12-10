package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment2_android.databaseFirestore.UserDatabase;
import com.example.assignment2_android.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class LogIn extends AppCompatActivity {


    EditText username;
    EditText password;
    Button login;
    TextView register, forgotPassword;
    List<User> oneUserlist;
    UserDatabase userDatabase;
    FirebaseFirestore db;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = findViewById(R.id.userNamLogIn);
        password = findViewById(R.id.passwordLogIn);
        oneUserlist = new ArrayList<>();
        login = findViewById(R.id.signIn);
        userDatabase = new UserDatabase();
        db = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);
        forgotPassword = findViewById(R.id.forgotPassword);

            login.setOnClickListener(view -> {
                String inputUserName = username.getText().toString();
                System.out.println("Huyyyyyyyyyyyyyyyyy" + inputUserName);
                String inputUserPassword = password.getText().toString();
                System.out.println("Huyyyyyyyyyyyyyyyyy" + inputUserPassword);
                userDatabase.getUserByUserNameAndPassword(this, db, inputUserPassword, inputUserName, oneUserlist);
                new Handler(Looper.getMainLooper()).postDelayed(() ->{
                    System.out.println("list ne !!!!" + oneUserlist);
                    System.out.println(oneUserlist.size());
                    if (oneUserlist.size() != 0) {
                        Intent intent = new Intent(this, VolunteerHome.class);
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try {
                            startActivity(intent);
                            Toast.makeText(LogIn.this, "You have successfully login!", Toast.LENGTH_LONG).show();

                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(LogIn.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LogIn.this, "Wrong Username Or Password. Please try again!", Toast.LENGTH_LONG).show();
                    }
                },2000);

            });


    }

    public void switchToRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(LogIn.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
        }
    }


}