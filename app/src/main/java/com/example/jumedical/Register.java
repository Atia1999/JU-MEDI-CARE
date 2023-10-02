package com.example.jumedical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {



    //create object of database reference class to access forebases's realtime database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jumedicare-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText name= findViewById(R.id.name);
        final EditText email=findViewById(R.id.email);
        final EditText phone =findViewById(R.id.phone);
        final EditText password= findViewById(R.id.password);
        final EditText conpass= findViewById(R.id.conPass);

        final Button register = findViewById(R.id.register);
        final TextView login_now= findViewById(R.id.login_now);

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
}