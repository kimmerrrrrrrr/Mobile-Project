package com.example.bayaduy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    DatabaseHelper databaseHelper;

    EditText uName, eMail, pass, cPass;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize DatabaseHelper and UI components
        databaseHelper = new DatabaseHelper(this);
        uName = findViewById(R.id.uName);
        eMail = findViewById(R.id.email);
        pass = findViewById(R.id.passWord);
        cPass = findViewById(R.id.cPassword);



        // Set onClick listener for login button
        b1 = findViewById(R.id.btnBack2);
        b1.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, LogIn.class);
            startActivity(intent);
        });

        b2 = findViewById(R.id.btnDone);
        b2.setOnClickListener(v -> {
            String username = uName.getText().toString();
            String email = eMail.getText().toString();
            String password = pass.getText().toString();
            String confirm_password = cPass.getText().toString();

            // Validate input fields
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Fields Required", Toast.LENGTH_SHORT).show();
            } else {
                if (password.equals(confirm_password)) {
                    boolean checkUsername = databaseHelper.CheckUsername(username);
                    if (checkUsername) {
                        boolean insert = databaseHelper.Insert(username, email, password);
                        if (insert) {
                            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                            uName.setText("");
                            eMail.setText("");
                            pass.setText("");
                            cPass.setText("");

                            // Navigate to login page
                            Intent intent = new Intent(SignUp.this, LogIn.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}