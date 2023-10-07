package com.example.jumedical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Doctor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mDatabase;

    private String[][] Doctor_Details={
            {"Name:DR.MD. SHAMSUR RAHMAN","Position: Chief Medical Officer","Email: drsrlabu19@gmail.com","Office Phone No: 1324"},
            {"Name:DR. MAMOTA MALLIKA","Position: Deputy Chief Medical Officer","Email:  medicalju123@gmail.com","Office Phone No: ex-1227"},
            {"Name:DR.MD. SHAMSUL ALAM KHAN (LITON)","Position: Deputy Chief Medical Officer","Email: medicalju123@gmail.com","Office Phone No: ex-1898"},
            {"Name:DR.DR. BIRENDRO KUMAR BISWAS","Position: Senior Medical Officer","Email: medicalju123@gmail.com","Office Phone No: EX-1392"},
            {"Name:DR.SHAMOL KUMAR SHIL","Position: Senior Medical Officer","Email: medicalju123@gmail.com","Office Phone No: 1324"},
            {"Name:DR.TOWHID HASSAN SHAH CHOWDHURY","Position: Senior Medical Officer","Email: medicalju123@gmail.com","Office Phone No: 1324"},
            {"Name:DR. ASMA SIDDIQA","Position: Medical Officer","Email: dr.elamoni@gmail.com","Office Phone No: 1324"},
            {"Name : DR. LIKHAN CHANDRA BALA","Position: Medical Officer (Part Time)","Email: dr.elamoni@gmail.com","Office Phone No: 1324"}
    };

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    HashMap<String,String> item;
    SimpleAdapter sa;
    ArrayList List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //nav bar
        drawerLayout=findViewById(R.id.drawerlayout4);
        navigationView=findViewById(R.id.navigation_view4);
        toolbar=findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        //hide or show menu items
        Menu menu=navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Navigation_open,R.string.Navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        navigationView.setCheckedItem(R.id.nav_home);

        List = new ArrayList<>();
        for (int i=0;i<Doctor_Details.length;i++)
        {
            item = new HashMap<String,String>();
            item.put("Line1",Doctor_Details[i][0]);
            item.put("Line2",Doctor_Details[i][1]);
            item.put("Line3",Doctor_Details[i][2]);
            item.put("Line4",Doctor_Details[i][3]);
            List.add(item);
        }
        sa = new SimpleAdapter(this,List,
                R.layout.multi_lines,
                new String[]{"Line1","Line2","Line3","Line4"},
                new int[]{R.id.lin_a,R.id.lin_b,R.id.lin_c,R.id.lin_d});
        ListView lst=findViewById(R.id.listviewdd);
        lst.setAdapter(sa);

    }

    //outside on create
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
                Intent intent = new Intent(Doctor.this,Home.class);
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
                intent = new Intent(Doctor.this,Login.class);
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
            case  R.id.nav_logout: {
                mDatabase.child("users").child("isLoggedIn").setValue(false);


                // Sign out from Firebase Authentication (if used)
                // Navigate back to the LoginActivity or any other appropriate screen
                intent = new Intent(Doctor.this, MainActivity.class);
                startActivity(intent);
                break;
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}