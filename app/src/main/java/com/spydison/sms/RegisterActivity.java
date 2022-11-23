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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            return;
        }

        Button RegButton = findViewById(R.id.RegButt);

        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });

        TextView RegtoLog = findViewById(R.id.logOnReg);
        RegtoLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchToLogin();
            }
        });
    }

    private void RegisterUser() {
        EditText ETRegName = findViewById(R.id.RegName);
        EditText ETRegUserName = findViewById(R.id.RegUN);
        EditText ETRegEmail = findViewById(R.id.RegEM);
        EditText ETRegPassword = findViewById(R.id.RegPass);

        String name = ETRegName.getText().toString();
        String usename = ETRegUserName.getText().toString();
        String email = ETRegEmail.getText().toString();
        String pass = ETRegPassword.getText().toString();

        if (name.isEmpty() || usename.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User ouruser = new User(usename, name, email);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(ouruser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            showMainMethod();
                                        }
                                    });
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();

//                            updateUI(null);
                        }
                    }
                });

    }

    private void showMainMethod() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void SwitchToLogin() {
        Intent intent = new Intent(this,LoginActivity.class );
        startActivity(intent);
        finish();
    }


}