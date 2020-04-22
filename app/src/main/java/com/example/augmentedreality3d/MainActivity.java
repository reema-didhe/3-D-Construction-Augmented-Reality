package com.example.augmentedreality3d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView nView;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private ViewPager viewPager;
    private MyPager myPager;
    private CircleIndicator circleIndicator;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout =  findViewById(R.id.drawer_layout);
        nView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setupDrawerContent(nView);

        myPager = new MyPager(this);
        viewPager = findViewById(R.id.viewPagerHeader);
        viewPager.setAdapter(myPager);
        circleIndicator = findViewById(R.id.circle);
        circleIndicator.setViewPager(viewPager);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        CardView one_bhk_cardview = (CardView) findViewById(R.id.onebhk); // creating a CardView and assigning a value.

        one_bhk_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"One BHK model loading",Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this,ARActivity.class);
                i.putExtra("urlPath", "file:///android_asset/One_Bhk/index.html");
                startActivity(i);

            }
        });

        CardView two_bhk_cardview = (CardView) findViewById(R.id.twobhk); // creating a CardView and assigning a value.

        two_bhk_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Two BHK model loading",Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this,ARActivity.class);
                i.putExtra("urlPath", "file:///android_asset/Two_Bhk/index.html");
                startActivity(i);

            }
        });

        CardView three_bhk_cardview = (CardView) findViewById(R.id.threebhk ); // creating a CardView and assigning a value.

        three_bhk_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Three BHK model loading",Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this,ARActivity.class);
                i.putExtra("urlPath", "file:///android_asset/Three_Bhk/index.html");
                startActivity(i);

            }
        });

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            // Android home
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            // manage other entries if you have it ...
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_menu, menu);

        UserSetup();

        return true;
    }

    public void UserSetup() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final ImageView userimage = findViewById(R.id.nav_user_image);
        TextView username = findViewById(R.id.nav_user_name);
        TextView useremail = findViewById(R.id.nav_user_email);

        if (user != null) {

            if (user.getPhotoUrl() != null) {
                     Picasso.get()
                        .load(user.getPhotoUrl())
                        .into(userimage, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError(Exception e) {
                            }
                        });

            }
            //username.setText(user.getDisplayName());
            /*User user1 = new User();
            username.setText(user1.fullName);*/
            useremail.setText(user.getEmail());
        }
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
            /*case R.id.nav_account:
                Toast.makeText(this, "My Account", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.nav_account:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
               /* profileFragment = ProfileFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent, profileFragment, ProfileFragment.TAG).addToBackStack(null).commit();*/
                break;
            case R.id.nav_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case R.id.nav_camera:
                startActivity(new Intent(getApplicationContext(), MyModels.class));
//                Intent i = new Intent();
//                //i.setClassName(mClassToLaunchPackage, mClassToLaunch);
//                i.setClassName("com.example.augmentedreality3d", "com.example.CoreSamples.app.ImageTargets.ImageTargets");
//                startActivity(i);
                break;
            /*case R.id.nav_whishlist:
                break;*/
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            default:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        //setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }

}
