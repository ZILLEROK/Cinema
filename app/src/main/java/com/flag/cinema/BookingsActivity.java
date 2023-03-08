package com.flag.cinema;

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
    SQLiteHelper database_helper;

    SQLiteDatabase sqLiteDatabaseObj;
    String currentUser="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d32f2f")));
        database_helper = new SQLiteHelper(this);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(BookingsActivity.this,HomeActivity.class);
                        startActivity(a);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.navigation_dashboard:
                        break;
                    case R.id.navigation_notifications:
                        Intent b = new Intent(BookingsActivity.this,ProfileActivity.class);
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
//        if(currentUser.equals("admin@mail.ru")){
//            AdminPrepareBook();
//        }
//        else{
//        prepareBook();}
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerBookAdapter = new RecyclerBookAdapter(bookList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDbRef = mDatabase.getReference();
        mDbRef.child("bookings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();

                for (DataSnapshot snapshotIter : snapshotIterator){
                    if(snapshotIter.child("user").getValue().toString().equals(currentUser)){
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

//        recyclerBookAdapter.setOnItemClickListener(new ClickListener1<Bookings>(){
//            @Override
//            public void onItemClick(Bookings data) {
//
//                Intent intent = new Intent(BookingsActivity.this, TicketActivity.class);
//                Bundle extras = new Bundle();
//                extras.putInt(id_movie, data.getMovie_id());
//                extras.putString(id_book, data.getId());
//                extras.putString(title,data.getTitle());
//                extras.putString(date,data.getDate());
//                extras.putString(seat, data.getSeats());
//                extras.putString(user,data.getUser());
//                intent.putExtras(extras);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//
//            }
//        });
//
//        recyclerView.setAdapter(recyclerBookAdapter);
    }

//    public void prepareBook(){
//        String group_by_clause="GROUP by  s_id,title_name,show_date,seats_booked_user, seat_name, movie_id";
//        String select_query= "SELECT s_id,title_name,show_date,seats_booked_user,seat_name,movie_id FROM  SeatsTable where seats_booked_user = '" + "" +currentUser+ "" + "' "+group_by_clause;
//
//        sqLiteDatabaseObj = database_helper.getWritableDatabase();
//        Cursor cursor = sqLiteDatabaseObj.rawQuery(select_query,null);
//        if (cursor.moveToFirst()) {
//            do {
//                Bookings bookings = new Bookings();
//                bookings.setId(cursor.getInt(0));
//                bookings.setTitle(cursor.getString(1));
//                bookings.setDate(cursor.getString(2));
//                bookings.setUser(cursor.getString(3));
//                bookings.setSeats(cursor.getString(4));
//                bookings.setMovie_id(cursor.getInt(5));
//                bookList.add(bookings);
//            }while (cursor.moveToNext());
//        }
//
//    }
//    public void AdminPrepareBook(){
//
//        String select_query= "SELECT s_id,title_name,show_date,seats_booked_user,seat_name,movie_id FROM  SeatsTable";
//
//        sqLiteDatabaseObj = database_helper.getWritableDatabase();
//        Cursor cursor = sqLiteDatabaseObj.rawQuery(select_query,null);
//        if (cursor.moveToFirst()) {
//            do {
//                Bookings bookings = new Bookings();
//                bookings.setId(cursor.getInt(0));
//                bookings.setTitle(cursor.getString(1));
//                bookings.setDate(cursor.getString(2));
//                bookings.setUser(cursor.getString(3));
//                bookings.setSeats(cursor.getString(4));
//                bookings.setMovie_id(cursor.getInt(5));
//                bookList.add(bookings);
//            }while (cursor.moveToNext());
//        }
//
//    }

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