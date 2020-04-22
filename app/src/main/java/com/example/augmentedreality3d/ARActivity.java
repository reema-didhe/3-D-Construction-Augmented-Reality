package com.example.augmentedreality3d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;
import com.wikitude.common.permission.PermissionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ARActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 1018;
    private ArchitectView architectView;
    private ImageView screenshot_image;
    private final PermissionManager permissionManager = ArchitectView.getPermissionManager();
    private boolean architectViewLoaded;
    String modalPath;
    private Button button_photo;
    private  String sharePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r);

        this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey("dVQIFDkzbeU/Mla/IOzIdF2huUX3ToKOa/khpP1gxRlwnt+u47zsTrvyCnU6now36qv4h8U3XNPYyonFAHHS+bgL+xWO+Lm1sFf5o5L9IdFSPa4Zn+1gasSuJBkLnYJGGUpVoODzOch7yGvNDv/2cS3aNa9zlEarrwlSku8U/8ZTYWx0ZWRfXydDMF1GojiYfiBopOy/3pARijHAGcX2+lUJ8Nc5lYzNaT6YhV4D+cbAbglKy16SxfJOZzicCNy/3dPgMdss+9FICsSTbnnVDxCv4wVPdJnq9AYcNo+9eMJZu84B7Dffiq9yzaZywbQEo8k3f1NUL/PEsuM/6x64RRU9K/gP5wkUEeSpYJXgZASfnUwoqcBdwtYx9OeVFAiEC9Al6OcwONVPAFh2+q7+T2/m6DQfcPTD+bwHh4+6HUreVHWGuxfTgMCnBE34W2KW+OKxcI2YX50MTbsTXc7sb6IlC43HtRipjD8IaStdmGTrr7hwzHhZ9Btuh5F+RcB1dlwHUFXyQuCHHvrfoENioAhnChq4NmZ9enzKRQU/sxQ46sZZX7inBnKtMDIVzfdmg8ucl/qoXlHfHcs1S7I22k8rUMD3EmxWFe/his6Fi7nD1iEDf5+GJw+HRNzlsAT1Cboe5u/zPE1ke7zXT3BneST1A7odIpl1ouAVGNTLVteBsQ/o+LKRRv9OlX7ebtQejpB08cCrYaYbCdXq9ENw0KDJCMzaBh6YckBgBu8h88odae+o8X8DTNSD78xaGm0IhH5Ejew9zRtG8s9dEqFeaUucq4Yrnr8unJ0ZZXJEXxlDzLLRlu+Rmr+6hoNhNoiZSz7xRHRmmoVqQ/Cu4hN22efCCkHrG3vFNg+h/6HVm4FV12ELC0Y/QoOGaAoVJ0f1");
        //config.setLicenseKey("pSF9qUAXprdA9yCg9SWRkwDMGC96Il7vupGuE8fMyxliBcgMx53HxOsTSHz4yHijJYdAePoXyzZo0IVX7I8D/ZFfBdHKX0mUv2lbAzGzXLxTihPLfkmRETLlec0k9WcI62vGKcVcx5jyTCOKWNOO1LKZEL6VLv7izVFYEyjmW6BTYWx0ZWRfX8XlkGBK8xt63PgwJfASVmw6B+M3Mz0R1TD/G5qghCq4xsSV1+zBN9LiySE2Oci9I5diCShKY9/QUnIMwtLuFuLAMj6bA3o1q+0AQ/+E8ZteLXCelh0YmQ8jTqlw+OlFK6m0bUuTOvUrulbbsfJFgejPNN7hvo/3zq8BiQWWwMZQbsT4NeRi7M6bKCGBKUrq949fSETrg4CMLtRSt3DV9bV8Ui4RpedmVSaUML40TTw7zMvlIHA+e4izua31Ru0ev6QJcfhZ++QKhVVCRiPrMbTYKegDT3YeHczBEdaeMtOsJ8pZ3r3ZHoz/p1J4gYdsMHPS8o5gD+XvKexEPFuOB0UMlHl57YJ5WB+7OID9d+pHPt+3KZpaTGZSmwEl++nrbNGbI/djdYC4Nmj5qERWj1PZZBPkiUKcHEc7KNlbaISVQHqzSCGaAvU7FT5m4hzEegLtGm31lN70hOyt0VrbJ/xZtiQND4L208QcCQi6PZjRXXbGstkPDHp3qBjopyDd6Mw0fbWAVvP96h5qj8kg4AVufX2lCZH0UyXtc0HQ75oWpcSW0mL28QU=");
        this.architectView.onCreate( config );
        Intent intent = getIntent();
        modalPath = intent.getExtras().getString("urlPath");
        Toast.makeText(ARActivity.this,modalPath,Toast.LENGTH_LONG).show();

        //ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        //back button
       /* button_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot();
                //screenshot_image.setVisibility(View.VISIBLE);
            }
        });*/

    }

    private void takeScreenshot() {
        View view1 = getWindow().getDecorView().getRootView();
        view1.setDrawingCacheEnabled(true);

        Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
        view1.setDrawingCacheEnabled(false);

        String filePath = Environment.getExternalStorageDirectory()+"/Download"+ Calendar.getInstance().getTime().toString()+".jpg";
        File fileScreenshot = new File(filePath);

        if (!fileScreenshot.exists()) {
            fileScreenshot.mkdir();
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileScreenshot);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void share(String sharePath){

        Log.d("ffff",sharePath);
        File file = new File(sharePath);
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent .setType("image/*");
        intent .putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(intent );

    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        architectView.onPostCreate();

        checkPermission();

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(ARActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA)) {
                // Explain to the user why we need to read the contacts
            }

            ActivityCompat.requestPermissions(ARActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            loadArchitectView();
        }
    }


    @Override
    protected void onResume(){
        super.onResume();
        try {
            if (architectViewLoaded) {
                architectView.onResume();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (architectViewLoaded) {
            architectView.onPause();

        }

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (architectViewLoaded) {
            architectView.onDestroy();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ArchitectView.getPermissionManager().onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                loadArchitectView();
            }
        }
    }

    private void loadArchitectView() {
        try {
            this.architectView.load(modalPath);
            architectViewLoaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
