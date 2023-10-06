package com.example.jumedical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Events extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // Events
        CardView ev1=findViewById(R.id.event1);
        ev1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(Events.this,Doctor.class));
                String url = "https://www.juniv.edu/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

            }
        });
        CardView ev2=findViewById(R.id.event2);
        ev2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(Events.this,Doctor.class));
                String url = "https://www.juniv.edu/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

            }
        });
        CardView ev3=findViewById(R.id.event3);
        ev3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(Events.this,Doctor.class));
                String url = "https://www.juniv.edu/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

            }
        });
        CardView ev4=findViewById(R.id.event4);
        ev4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(Events.this,Doctor.class));
                String url = "https://www.juniv.edu/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

            }
        });
        CardView ev5=findViewById(R.id.event5);
        ev1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(Events.this,Doctor.class));
                String url = "https://www.juniv.edu/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

            }
        });
        CardView ev6=findViewById(R.id.event6);
        ev6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(Events.this,Doctor.class));
                String url = "https://www.uiu.ac.bd/events/voluntary-blood-donation-campaign-summer-2023/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

            }
        });



        //Nav Bar
        drawerLayout=findViewById(R.id.drawerlayout9);
        navigationView=findViewById(R.id.navigation_view9);
        toolbar=findViewById(R.id.toolbar9);
        setSupportActionBar(toolbar);

        //hide or show menu items
        Menu menu=navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Navigation_open,R.string.Navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch ( item.getItemId()){

            case R.id.nav_home:
                Intent intent = new Intent(Events.this,Home.class);
                startActivity(intent);
                break;
            case R.id.nav_share:
            {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + getPackageName()));

                intent.setPackage("com.android.vending");

                startActivity(intent);
                break;
            }
            case R.id.nav_login:
                intent = new Intent(Events.this,Login.class);
                startActivity(intent);
                break;
            case R.id.nav_email:
            {String recipientEmail = "jucse28.346@gmail.com";

                String subject = "Subject"; // Set your subject here

                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEmail});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intent, "Send Email"));
                    break;}}
            case R.id.nav_rate:
            {
                String destination = "Jahangirnagar University Medical Centar";

                if (destination.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter the destination location", Toast.LENGTH_SHORT).show();
                } else {
                    Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + destination);
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
            }
            case R.id.nav_call:
            {
                String phoneNumber = "01846848676";

                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));

                startActivity(intent);
            }





        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}