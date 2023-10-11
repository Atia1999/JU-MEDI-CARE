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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mDatabase;


    //create object of database reference class to access forebases's realtime database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jumedicare-default-rtdb.firebaseio.com/");

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button loginBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Registration code
        final EditText name= findViewById(R.id.name);
        final EditText email=findViewById(R.id.email);
        final EditText phone =findViewById(R.id.phone);
        final EditText password= findViewById(R.id.password);
        final EditText conpass= findViewById(R.id.conPass);

        final Button register = findViewById(R.id.register);
        final TextView login_now= findViewById(R.id.login_now);

        // Nav Bar
        drawerLayout=findViewById(R.id.drawerlayout6);
        navigationView=findViewById(R.id.navigation_view6);
        toolbar=findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar);

        //hide or show menu items
        Menu menu=navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_home).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Navigation_open,R.string.Navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get data from edit texts to string variable

                final String nametxt = name.getText().toString();
                final String emailtxt= email.getText().toString();
                final String phonetxt= phone.getText().toString();
                final String passtext= password.getText().toString();
                final String conpasstxt= conpass.getText().toString();

                // check if user fills all the fields before sending data to firebase

                if(nametxt.isEmpty() || emailtxt.isEmpty() || phonetxt.isEmpty() || passtext.isEmpty() || conpasstxt.isEmpty())
                {
                    Toast.makeText(Register.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                // check is passwords are matching with each other
                else if (!passtext.equals(conpasstxt)) {
                    Toast.makeText(Register.this, "Passwords are not matching ", Toast.LENGTH_SHORT).show();

                } else if (passtext.length()<4) {
                    Toast.makeText(Register.this, "Password is too short, Please recheck!", Toast.LENGTH_SHORT).show();

                }

                // check if passwords are matching
                else
                {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //check if phone is not registered before

                            if(snapshot.hasChild(phonetxt)){
                                Toast.makeText(Register.this, "Phone Number is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //sending data to firebase realtime database
                                //we are using phone number as unique attribute
                                // so all the other details come under phone number

                                databaseReference.child("users").child(phonetxt).child("username").setValue(nametxt);
                                databaseReference.child("users").child(phonetxt).child("email").setValue(emailtxt);
                                databaseReference.child("users").child(phonetxt).child("password").setValue(passtext);

                                // show a successful message and then finish the activity
                                Toast.makeText(Register.this, "User Registered Successfully :) ", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
        });

        login_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                Intent intent = new Intent(Register.this,Home.class);
                startActivity(intent);
                break;

            case R.id.nav_login:
                intent = new Intent(Register.this,Login.class);
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
            case R.id.nav_share:
            {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + getPackageName()));

                intent.setPackage("com.android.vending");

                startActivity(intent);
                break;
            }
            case  R.id.nav_logout: {
                mDatabase.child("users").child("isLoggedIn").setValue(false);


                // Sign out from Firebase Authentication (if used)
                // Navigate back to the LoginActivity or any other appropriate screen
                intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                break;
            }





        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}