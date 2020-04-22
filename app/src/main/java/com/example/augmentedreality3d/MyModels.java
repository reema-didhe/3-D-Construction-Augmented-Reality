package com.example.augmentedreality3d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MyModels extends AppCompatActivity {
    RecyclerView recyclerView;
    private ImageView backimg;
    String[] items = {"Item 0", "Item 1", "Item 2", "Item 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_models);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ModelAdapter(items, this));
        backimg = (ImageView) findViewById(R.id.backimg);

        //back button
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region Back
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

}