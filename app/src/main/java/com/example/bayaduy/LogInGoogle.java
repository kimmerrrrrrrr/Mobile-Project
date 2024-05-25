package com.example.bayaduy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LogInGoogle extends Activity {

    DatabaseHelper databaseHelper;
    LinearLayout userListLayout;
    List<String> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_google);

        databaseHelper = new DatabaseHelper(this);
        userListLayout = findViewById(R.id.userListLayout);
        userList = new ArrayList<>();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.7));
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -40;

        getWindow().setAttributes(params);

        displayUserData();
    }

    private void displayUserData() {
        Cursor cursor = null;
        try {
            cursor = databaseHelper.getReadableDatabase().rawQuery("SELECT username FROM user", null);

            if (cursor.moveToFirst()) {
                userList.clear();

                do {
                    @SuppressLint("Range") final String username = cursor.getString(cursor.getColumnIndex("username"));
                    userList.add(username);

                    LinearLayout userLayout = new LinearLayout(this);
                    userLayout.setOrientation(LinearLayout.HORIZONTAL);

                    Button userButton = new Button(this);
                    userButton.setText(username);
                    userButton.setTextSize(12);
                    userButton.setPadding(8, 8, 8, 8);
                    userButton.setBackgroundColor(getResources().getColor(R.color.blue));
                    userButton.setTextColor(getResources().getColor(R.color.white));
                    userButton.setGravity(Gravity.CENTER);

                    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1.0f
                    );
                    buttonParams.setMargins(0, 8, 4, 0);

                    userButton.setLayoutParams(buttonParams);

                    userButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (attemptLogin(username)) {
                                Intent intent = new Intent(LogInGoogle.this, WelcomePage.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LogInGoogle.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    Button deleteButton = new Button(this);
                    deleteButton.setText("Delete");
                    deleteButton.setTextSize(12);
                    deleteButton.setPadding(8, 6, 8, 6);
                    deleteButton.setBackgroundColor(getResources().getColor(R.color.blue_jeans));
                    deleteButton.setTextColor(getResources().getColor(R.color.white));
                    deleteButton.setGravity(Gravity.CENTER);

                    LinearLayout.LayoutParams deleteButtonParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    deleteButtonParams.setMargins(0, 8, 0, 0);

                    deleteButton.setLayoutParams(deleteButtonParams);

                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AlertDialog.Builder(LogInGoogle.this)
                                    .setTitle("Confirm Delete")
                                    .setMessage("Are you sure you want to delete this user?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deleteUser(username);
                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                    });

                    userLayout.addView(userButton);
                    userLayout.addView(deleteButton);

                    userListLayout.addView(userLayout);
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "No user data found", Toast.LENGTH_SHORT).show();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private boolean attemptLogin(String username) {
        Cursor cursor = null;
        try {
            SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT password FROM user WHERE username=?", new String[]{username});
            if (cursor.moveToFirst()) {
                return true;
            }
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void deleteUser(String username) {
        for (int i = 0; i < userListLayout.getChildCount(); i++) {
            View child = userListLayout.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout userLayout = (LinearLayout) child;
                Button userButton = (Button) userLayout.getChildAt(0);
                if (userButton.getText().toString().equals(username)) {
                    userListLayout.removeView(userLayout);
                    Toast.makeText(this, "User removed from the list", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }
}