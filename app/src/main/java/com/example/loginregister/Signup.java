package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Signup extends AppCompatActivity {

    TextInputEditText textInputEditText_username, textInputEditText_password, textInputEditText_confirmPassword, textInputEditText_email;
    Button Signup_btn;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize the input fields
        textInputEditText_username = findViewById(R.id.username);
        textInputEditText_password = findViewById(R.id.password);
        textInputEditText_confirmPassword = findViewById(R.id.confirmPassword);
        textInputEditText_email = findViewById(R.id.email);
        Signup_btn = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        textViewLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        Signup_btn.setOnClickListener(view -> {
            String username, password, confirmPassword, email;
            username = String.valueOf(textInputEditText_username.getText());
            password = String.valueOf(textInputEditText_password.getText());
            confirmPassword = String.valueOf(textInputEditText_confirmPassword.getText());
            email = String.valueOf(textInputEditText_email.getText());

            if (!username.equals("") && !password.equals("") && !confirmPassword.equals("") && !email.equals("")) {
                if (password.equals(confirmPassword)) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {
                        String[] field = new String[3];
                        field[0] = "username";
                        field[1] = "email";
                        field[2] = "password";

                        String[] data = new String[3];
                        data[0] = username;
                        data[1] = email;
                        data[2] = password;

                        PutData putData = new PutData("http://192.168.1.6:8080/LoginRegister/signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if (result.equals("Sign Up Success")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
