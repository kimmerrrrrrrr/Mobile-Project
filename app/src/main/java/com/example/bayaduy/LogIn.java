package com.example.bayaduy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {

    Button b1, b2, b3;
    EditText uName, Pass;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        databaseHelper = new DatabaseHelper(this);

        uName = findViewById(R.id.uName);
        Pass = findViewById(R.id.Pass);

        b1 = (Button) findViewById(R.id.btnBack2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(
                        LogIn.this,
                        MainActivity.class);
                startActivity(intent1);
            }
        });

        b2 = (Button) findViewById(R.id.btnLogIn2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = uName.getText().toString();
                String password = Pass.getText().toString();

                Boolean checkLogin = databaseHelper.CheckLogin(username, password);
                if(checkLogin) {
                    Toast.makeText(getApplicationContext(), "Log In Successful", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(
                            LogIn.this,
                            WelcomePage.class);
                    startActivity(intent1);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b3 = (Button) findViewById(R.id.btnReg);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(
                        LogIn.this,
                        SignUp.class);
                startActivity(intent1);
            }
        });
    }
}