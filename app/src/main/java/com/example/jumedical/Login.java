package com.example.jumedical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jumedical.R.id;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jumedicare-default-rtdb.firebaseio.com/");

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button loginBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        final EditText phone;
        phone = findViewById(id.phoneNo);
        final EditText password = findViewById(R.id.password);
        final Button login=findViewById(R.id.login);
        final TextView RegNow=findViewById(R.id.reg_now);

        //nav bar
        drawerLayout=findViewById(id.drawerlayout3);
        navigationView=findViewById(id.navigation_view3);
        toolbar=findViewById(id.toolbar3);
        setSupportActionBar(toolbar);

        //hide or show menu items
        Menu menu=navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_home).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Navigation_open,R.string.Navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        navigationView.setCheckedItem(R.id.nav_home);

        //LOG IN
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phonetxt = phone.getText().toString();
                final String passwordtxt = password.getText().toString();
                if(phonetxt.isEmpty())
                {
                    Toast.makeText(Login.this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                }
                if (passwordtxt.isEmpty()) {
                    Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();

                }
                else{

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //checl whether the phone number exist in database

                            if(snapshot.hasChild(phonetxt)) {
                                // phone number exists
                                // get password from firebase and match it with user entered password

                                final String getpassword = snapshot.child(phonetxt).child("password").getValue(String.class);

                                if(getpassword.equals(passwordtxt))
                                {
                                    Toast.makeText(Login.this, "Successfully Logged in !", Toast.LENGTH_SHORT).show();

                                    //open mainactivity and finish login activity
                                    startActivity(new Intent(Login.this,Home.class));
                                    finish();

                                }
                                else
                                {
                                    Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }

                            else{
                                Toast.makeText(Login.this, "Wrong Mobile No", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

        //open register activity
        RegNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this,Register.class));
            }
        });
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
                Intent intent = new Intent(Login.this,Home.class);
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
                intent = new Intent(Login.this,Login.class);
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