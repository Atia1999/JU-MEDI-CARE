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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class Articles extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
    {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
        private String[][] Article_details={
                {"Walking Daily","","","Click More Details"},
                {"Covid 19 Awareness","","","Click More Details"},
                {"Drug ","","","Click More Details"},
                {"Personal Hygiene","","","Click More Details"},
                {"Dengue ","","","Click More Details"},


        };
         private int[] images={
                 R.drawable.article1,
                 R.drawable.article2,
                 R.drawable.article3,
                 R.drawable.article4,
                 R.drawable.article5,
         };
        HashMap<String,String> item;
        SimpleAdapter sa;
        ArrayList List;
        ListView lst;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);


        //Articles
        lst=findViewById(R.id.listviewArticle);
        List = new ArrayList<>();
        for (int i=0;i<Article_details.length;i++)
        {
            item = new HashMap<String,String>();
            item.put("Line1",Article_details[i][0]);
            item.put("Line2",Article_details[i][1]);
            item.put("Line3",Article_details[i][2]);
            item.put("Line4",Article_details[i][3]);
            //item.put("Line5",Article_details[i][4]);
            List.add(item);
        }
        sa = new SimpleAdapter(this,List,
                R.layout.multi_lines,
                new String[]{"Line1","Line2","Line3","Line4"},
                new int[]{R.id.lin_a,R.id.lin_b,R.id.lin_c,R.id.lin_d});
        lst.setAdapter(sa);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(Articles.this,ArticlesDetails.class);
                intent.putExtra("text1",Article_details[i][0]);
                intent.putExtra("text2",images[i]);
                startActivity(intent);
            }
        });
        //Nav Bar
        drawerLayout=findViewById(R.id.drawerlayout11);
        navigationView=findViewById(R.id.navigation_view11);
        toolbar=findViewById(R.id.toolbar11);
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
                    Intent intent = new Intent(Articles.this,Home.class);
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
                    intent = new Intent(Articles.this,Login.class);
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