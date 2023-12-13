package vn.edu.stu.petshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText etUser, etPass;
    String userStateFile = "UserState";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
    }
    private void addControls() {
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLogin();
            }
        });
    }
    private void xuLyLogin() {
        String userName = etUser.getText().toString();
        String password = etPass.getText().toString();
        if (userName.equalsIgnoreCase("admin") && password.equalsIgnoreCase("123")){
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("username", userName);
            startActivity(intent);

            // Save user credentials to SharedPreferences
            saveUserCredentials(userName, password);
        }else{
            // Login failed
            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();

            // Clear user credentials from SharedPreferences
            clearUserCredentials();
        }

    }
    private void saveUserCredentials(String userName, String password) {
        SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", userName);
        editor.putString("password", password);
        editor.apply();
    }
    private void clearUserCredentials() {
        SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();
    }
}