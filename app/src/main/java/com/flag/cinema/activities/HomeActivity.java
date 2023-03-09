package com.flag.cinema.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flag.cinema.adapters.ClickListener;
import com.flag.cinema.models.Movie;
import com.flag.cinema.R;
import com.flag.cinema.adapters.RecyclerViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity{

    public RecyclerViewAdapter recyclerViewAdapter;
    public static ArrayList<Movie> movieList;
    public static String id_movie = "id_movie";
    public static String imageIntent = "imageIntent";
    public static  String titleIntent ="titleIntent";
    public static  String ratingIntent ="ratingIntent";
    public static  String runtimeIntent ="runtimeIntent";
    public static  String genreIntent ="genreIntent";
    public static  String dateIntent ="dateIntent";
    public static  String trailerIntent ="trailerIntent";
    public static  String categoryIntent ="categoryIntent";
    public static  String relaseIntent ="relaseIntent";
    public static  String descriptionIntent ="descriptionIntent";
    public static  String priceIntent ="priceIntent";

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";

    SQLiteDatabase sqLiteDatabaseObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d32f2f")));

        movieList = new ArrayList<>();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDbRef = mDatabase.getReference();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_dashboard:
                        Intent a = new Intent(HomeActivity.this, BookingsActivity.class);
                        startActivity(a);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.navigation_notifications:
                        Intent b = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(b);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                }
                return false;
            }
        });


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
//        Toast.makeText(getApplicationContext(),sharedpreferences.getString(Email, ""),Toast.LENGTH_LONG).show();


        String currentUser = sharedpreferences.getString(Email, "");

        mDbRef.child("movies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();

                for (DataSnapshot snapshotIter : snapshotIterator){
                    //adds every child attributes to an array list called flightList without filtering
                    Movie movie = new Movie(snapshotIter.child("name").getValue().toString()
                            ,snapshotIter.child("type").getValue().toString()
                            ,snapshotIter.child("length").getValue().toString()
                            ,snapshotIter.child("category").getValue().toString()
                            ,snapshotIter.child("rating").getValue().toString()
                            ,snapshotIter.child("trile").getValue().toString()
                            ,snapshotIter.child("relaDate").getValue().toString()
                            ,snapshotIter.child("date_time").getValue().toString()
                            ,snapshotIter.child("description").getValue().toString()
                            ,Integer.parseInt(snapshotIter.child("imgUrl").getValue().toString())
                            ,Integer.parseInt(snapshotIter.child("id").getValue().toString())
                            ,Integer.parseInt(snapshotIter.child("price").getValue().toString()));
//                    movie1(movie);
                    //Toast.makeText(HomeActivity.this,Integer.parseInt(snapshotIter.child("imgUrl").getValue().toString().toString()),Toast.LENGTH_LONG).show();
                    movieList.add(movie);
            }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);

                recyclerViewAdapter = new RecyclerViewAdapter(movieList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerViewAdapter.setOnItemClickListener(new ClickListener<Movie>(){
                    @Override
                    public void onItemClick(Movie data) {

                        Intent intent = new Intent(HomeActivity.this, ScreenSelection.class);
                        Bundle extras = new Bundle();
                        extras.putInt(id_movie, data.getId());
                        extras.putInt(imageIntent, data.getImgUrl());
                        extras.putString(dateIntent,data.getDate_time());
                        extras.putString(trailerIntent,data.getTrile());
                        extras.putString(categoryIntent,data.getCategory());
                        extras.putString(titleIntent,data.getName());
                        extras.putString(ratingIntent,data.getRating());
                        extras.putString(runtimeIntent,data.getLength());
                        extras.putString(genreIntent,data.getType());
                        extras.putString(relaseIntent,data.getRelaDate());
                        extras.putString(descriptionIntent,data.getDescription());
                        extras.putInt(priceIntent,data.getPrice());
                        extras.putString("user", currentUser);
                        intent.putExtras(extras);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
                recyclerView.setAdapter(recyclerViewAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }






        });


    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Выход назад")
                .setMessage("Вы уверены, что хотите выйти?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }

                })
                .setNegativeButton("Нет", null)
                .show();
    }

}