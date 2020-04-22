package com.example.augmentedreality3d;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {

    private Uri filUri, imageUri;
    private ImageView roundedimag, backimg;
    private Button browsebutton;
    private EditText nameedit, emailedit, passwordedit;
    private TextView savetext;
    private ImageButton editemail, editpassword;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        roundedimag = (ImageView) findViewById(R.id.userimage);
        browsebutton = (Button) findViewById(R.id.Browsebutton);
        nameedit = (EditText) findViewById(R.id.nameedit);
        emailedit = (EditText) findViewById(R.id.emailedit);
        passwordedit = (EditText) findViewById(R.id.passwordedit);

        editemail = (ImageButton) findViewById(R.id.editemail);
        editpassword = (ImageButton) findViewById(R.id.editpassword);

        backimg = (ImageView) findViewById(R.id.backimg);
        savetext = (TextView) findViewById(R.id.savetext);

        progressBar = findViewById(R.id.progressbar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        emailedit.setText(user.getEmail());


       /* // Load an image using Picasso library
        Picasso.get()
                .load("https://unsplash.com/s/photos/pink-flower")
                .into(roundedimag, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
        // Load an image using Glide library
        Glide.with(getApplicationContext())
                .load("https://unsplash.com/s/photos/pink-flower")
                .into(roundedimag);*/

        //Select the images
        browsebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        //back button
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region Back
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Edit email address
        editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailedit.setEnabled(true);
                emailedit.setFocusableInTouchMode(true); //to enable it
            }
        });

        //edit password
        editpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordedit.setEnabled(true);
                passwordedit.setFocusableInTouchMode(true); //to enable it
            }
        });

        //Save user data
        savetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname = nameedit.getText().toString();
                //final String username = userName.getText().toString();
                final String email = emailedit.getText().toString();
                String passowrd = passwordedit.getText().toString();

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email, passowrd)
                        .addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {

                                    User userInformation = new User(
                                            fullname, "Reema", email, "Female"
                                    );

                                    FirebaseDatabase.getInstance().getReference("User")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(userInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    });
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Profile Updation Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            filUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filUri);
                roundedimag.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImage() {
        if(filUri != null){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading..");
            progressDialog.show();

            StorageReference reference = storageReference.child("images/" + UUID.randomUUID().toString());
            reference.putFile(filUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}
