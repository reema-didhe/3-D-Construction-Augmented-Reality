package com.example.augmentedreality3d;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText fullName, userName, emailId, password, confirmPassowrd;
    Button registerBtn;
    RadioButton radioButtonMale, radioButtonFemale;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String gender = "";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = findViewById(R.id.fullname);
        userName = findViewById(R.id.username);
        emailId = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassowrd = findViewById(R.id.confrimpassword);
        radioButtonMale = findViewById(R.id.male);
        radioButtonFemale = findViewById(R.id.female);
        progressBar = findViewById(R.id.progressbar);
        registerBtn = findViewById(R.id.register);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname = fullName.getText().toString();
                final String username = userName.getText().toString();
                final String email = emailId.getText().toString();
                String passowrd = password.getText().toString();


                //Validation on all textfields
                if (TextUtils.isEmpty(fullname)){
                    Toast.makeText(SignUpActivity.this, "Please enter the Full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(username)){
                    Toast.makeText(SignUpActivity.this, "Please enter the User name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(SignUpActivity.this, "Please enter the Email Id", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passowrd)){
                    Toast.makeText(SignUpActivity.this, "Please enter the Password", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (passowrd.length()<6){
                    Toast.makeText(SignUpActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }

                if(radioButtonMale.isChecked()){
                    gender = "Male";
                }
                if (radioButtonFemale.isChecked()){
                    gender = "Female";
                }

                progressBar.setVisibility(View.VISIBLE);


                    firebaseAuth.createUserWithEmailAndPassword(email, passowrd)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBar.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {

                                            User userInformation = new User(
                                                    fullname, username, email, gender
                                            );

                                            FirebaseDatabase.getInstance().getReference("User")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(userInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(SignUpActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                }
                                            });
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Registration Failed...User already exist", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

            }
        });


    }
}
