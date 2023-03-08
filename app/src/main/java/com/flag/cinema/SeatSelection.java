package com.flag.cinema;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeatSelection extends AppCompatActivity {

    Button bookseats;
    public static  String title,showDate,seatsList,seatsIndex;
    public static int id_movie, price;

    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String s="";


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";

    String currentUser="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d32f2f")));
//        bookedSeats.clear();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDbRef = mDatabase.getReference();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NumberedAdapter(54));

        bookseats =(Button)findViewById(R.id.bookseats);


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        currentUser = sharedpreferences.getString(Email, "");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id_movie = extras.getInt("id_movie");
        title = extras.getString("title");
        price = extras.getInt("price");
        showDate = extras.getString("date");
        NumberedAdapter.seats = new ArrayList<>();

       NumberedAdapter.bookedSeats.clear();
//        sqLiteHelper = new SQLiteHelper(this);
//        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
//        cursor = sqLiteDatabaseObj.query(SQLiteHelper.SEATS_TABLE_NAME, null, " "  + SQLiteHelper.Seats_Table_Title_Name + " = ? AND "  + SQLiteHelper.Seats_Table_Show_Date + " = ? ", new String[]{SeatSelection.title,SeatSelection.showDate}, null, null, null);
//        while (cursor.moveToNext()) {
//            NumberedAdapter.bookedSeats.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Seats_Table_Seat_Index)).trim());
//        }
//        cursor.close();
        mDbRef.child("bookings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                for (DataSnapshot snapshotIter : snapshotIterator){
                    if(snapshotIter.child("date").getValue().toString().equals(extras.getString("date"))&&
                            snapshotIter.child("title").getValue().toString().equals(extras.getString("title"))) {
                        NumberedAdapter.bookedSeats.add(snapshotIter.child("seat_index").getValue().toString().trim());

                    }}
//                for(int i=0;i<NumberedAdapter.bookedSeats.size();i++)
//                {
//                    if(NumberedAdapter.position==(Integer.parseInt(NumberedAdapter.bookedSeats.get(i).trim()))){
//                        NumberedAdapter.holder.textView.setBackgroundResource(R.drawable.seat_booked);
//                    }
//                }
                bookseats.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        seatsList = NumberedAdapter.seats.toString();
                        seatsIndex = NumberedAdapter.seatindex.toString();
                        seatsList = seatsList.substring(1, seatsList.length() - 1);
                        seatsIndex = seatsIndex.substring(1, seatsIndex.length() - 1);
                        String[] seatsNames=seatsList.split(",");

                        String[] seatsNumbers=seatsIndex.split(",");
                        if( NumberedAdapter.seats.size()!=0) {

                            Intent intent = new Intent(SeatSelection.this, PaymentActicity.class);
                            Bundle extras = new Bundle();
                            extras.putString("title",title);
                            extras.putInt("id_movie",id_movie);
                            extras.putStringArray("seatsNames",seatsNames);
                            extras.putStringArray("seatsNumbers",seatsNumbers);
                            extras.putInt("seats", seatsNames.length);
                            extras.putString("currentUser", currentUser);
                            extras.putString("date", showDate);
                            extras.putInt("price", price);

                            intent.putExtras(extras);
                            startActivity(intent);
                            NumberedAdapter.seats.clear();
                            NumberedAdapter.seatindex.clear();

                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Пожалуйста, выберите место",Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            //recyclerViewAdapter = new RecyclerViewAdapter(movieList,getApplicationContext());
//





        });


    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}