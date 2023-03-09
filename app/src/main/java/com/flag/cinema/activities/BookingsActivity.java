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

import com.flag.cinema.models.Bookings;
import com.flag.cinema.adapters.ClickListener1;
import com.flag.cinema.R;
import com.flag.cinema.adapters.RecyclerBookAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingsActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerBookAdapter recyclerBookAdapter;
    private ArrayList<Bookings> bookList;
    public static String id_movie = "id_movie";
    public static  String id_book ="id_book";
    public static  String title ="title";
    public static  String date ="date";
    public static  String seat ="seat";
    public static  String user ="user";


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";


    SQLiteDatabase sqLiteDatabaseObj;
    String currentUser="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d32f2f")));


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(BookingsActivity.this, HomeActivity.class);
                        startActivity(a);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.navigation_dashboard:
                        break;
                    case R.id.navigation_notifications:
                        Intent b = new Intent(BookingsActivity.this, ProfileActivity.class);
                        startActivity(b);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                }
                return false;
            }
        });



        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        currentUser = sharedpreferences.getString(Email, "");
        bookList = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerBookAdapter = new RecyclerBookAdapter(bookList);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDbRef = mDatabase.getReference();

        mDbRef.child("bookings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();

                for (DataSnapshot snapshotIter : snapshotIterator){
                    if(currentUser.equals("admin@mail.ru")){
                        //adds every child attributes to an array list called flightList without filtering
                        Bookings bookings = new Bookings(snapshotIter.child("id").getValue().toString()
                                ,snapshotIter.child("title").getValue().toString()
                                ,snapshotIter.child("date").getValue().toString()
                                ,snapshotIter.child("seats").getValue().toString()
                                ,snapshotIter.child("user").getValue().toString()
                                ,snapshotIter.child("seat_index").getValue().toString()
                                ,Integer.parseInt(snapshotIter.child("movie_id").getValue().toString()));
//
                        //Toast.makeText(HomeActivity.this,Integer.parseInt(snapshotIter.child("imgUrl").getValue().toString().toString()),Toast.LENGTH_LONG).show();
                        bookList.add(bookings);}
                    else if(snapshotIter.child("user").getValue().toString().equals(currentUser) && !currentUser.equals("admin@mail.ru")){
                    //adds every child attributes to an array list called flightList without filtering
                    Bookings bookings = new Bookings(snapshotIter.child("id").getValue().toString()
                            ,snapshotIter.child("title").getValue().toString()
                            ,snapshotIter.child("date").getValue().toString()
                            ,snapshotIter.child("seats").getValue().toString()
                            ,snapshotIter.child("user").getValue().toString()
                            ,snapshotIter.child("seat_index").getValue().toString()
                            ,Integer.parseInt(snapshotIter.child("movie_id").getValue().toString()));
//

                    bookList.add(bookings);}
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BookingsActivity.this);

                recyclerView.setLayoutManager(layoutManager);
                recyclerBookAdapter.setOnItemClickListener(new ClickListener1<Bookings>(){
                    @Override
                    public void onItemClick(Bookings data) {

                        Intent intent = new Intent(BookingsActivity.this, TicketActivity.class);
                        Bundle extras = new Bundle();
                        extras.putInt(id_movie, data.getMovie_id());
                        extras.putString(id_book, data.getId());
                        extras.putString(title,data.getTitle());
                        extras.putString(date,data.getDate());
                        extras.putString(seat, data.getSeats());
                        extras.putString(user,data.getUser());
                        intent.putExtras(extras);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
                });

                recyclerView.setAdapter(recyclerBookAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            //recyclerViewAdapter = new RecyclerViewAdapter(movieList,getApplicationContext());
//





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