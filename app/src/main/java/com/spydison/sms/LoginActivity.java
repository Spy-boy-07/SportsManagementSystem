package com.spydison.sms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
        finish();
        return;
    }

    Button LoginButt = findViewById(R.id.LogButt);
        LoginButt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LoginUser();
        }
    });

    TextView LogToReg = findViewById(R.id.RegOnLog);
        LogToReg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switchToRegister();
        }
    });
    }

    private void switchToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void LoginUser() {
        EditText ETLogUN = findViewById(R.id.LogEmail);
        EditText ETLogpass = findViewById(R.id.LogPass);

        String username = ETLogUN.getText().toString();
        String pass = ETLogpass.getText().toString();

        if (username.isEmpty()||pass.isEmpty()){
            Toast.makeText(this, "Please fill all the entries! ", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(username, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            showMainActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}