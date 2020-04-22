package com.example.augmentedreality3d;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login, register;
    ProgressBar progressbar;
    private FirebaseAuth firebaseAuth;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().setTitle("Login Form");

        //Initalizing all views
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.registerBtn);
        progressbar = findViewById(R.id.progressbar);
        profileImage = findViewById(R.id.profileImage);


        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailid = email.getText().toString();
                String passwrd = password.getText().toString();

                //Validation
                if(TextUtils.isEmpty(emailid)){
                    Toast.makeText(LoginActivity.this, "Please enter email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwrd)){
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                //check password length
                if (passwrd.length()<6){
                    Toast.makeText(LoginActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }

                firebaseAuth.signInWithEmailAndPassword(emailid, passwrd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login failed or User not available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ImageUpload.class));
            }
        });
    }

    public void btn_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
}
