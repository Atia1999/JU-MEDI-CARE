package com.example.jumedical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jumedical.R.id;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jumedicare-default-rtdb.firebaseio.com/");

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
                                    startActivity(new Intent(Login.this,MainActivity.class));
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
}