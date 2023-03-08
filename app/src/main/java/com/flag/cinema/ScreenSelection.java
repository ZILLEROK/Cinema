package com.flag.cinema;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class ScreenSelection extends AppCompatActivity implements
        View.OnClickListener{

    int image, id_movie, price;
    String title,rating,runtime,genre, trailer, category, relase, description, user;
    private TextView titleText;
    private TextView ratingText;
    private TextView genreText;
    private TextView movie_category;
    private TextView descriptionText;
    private TextView date_tv_data;
    private TextView priceText;
    private ImageView imageText;
    SQLiteHelper database_helper;
    SQLiteDatabase sqLiteDatabaseObj;
    private Button btn_date, delete, add;
    private FloatingActionButton play_button;
    private String date;

    Bundle extras;
    Intent intent;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_selection);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d32f2f")));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        database_helper = new SQLiteHelper(this);

         intent = getIntent();
         extras = intent.getExtras();
        title=extras.getString("titleIntent");
         id_movie = extras.getInt("id_movie");
         image = extras.getInt("imageIntent");
        price= extras.getInt("priceIntent");
        user = extras.getString("user");
        date = extras.getString("dateIntent");
        trailer = extras.getString("trailerIntent");
        movie_category = (TextView)findViewById(R.id.movie_category);
        descriptionText = findViewById(R.id.description);
        date_tv_data = (TextView)findViewById(R.id.date_tv_data);
        titleText = (TextView)findViewById(R.id.title);
        ratingText = (TextView)findViewById(R.id.rating);
        genreText = (TextView)findViewById(R.id.genre);
        priceText = findViewById(R.id.price);
        imageText = (ImageView)findViewById(R.id.image);
        //et_date = (EditText)findViewById(R.id.in_date);
        btn_date = (Button) findViewById(R.id.btn_date);
        delete = (Button) findViewById(R.id.delete);
        add = (Button) findViewById(R.id.edit);
        play_button = findViewById(R.id.playButton);
        imageText.setImageResource(image);
//        prepareMovie(id_movie);

        titleText.setText(title);
        genreText.setText(extras.getString("genreIntent"));
        ratingText.setText(extras.getString("ratingIntent")+" | "+extras.getString("runtimeIntent"));
        date_tv_data.setText(extras.getString("relaseIntent"));
        movie_category.setText(extras.getString("categoryIntent"));
        descriptionText.setText(extras.getString("descriptionIntent"));
        priceText.setText(extras.getInt("priceIntent")+" руб.");
        //play_button.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        String str = extras.getString("dateIntent");
        String[] arrOfStr = str.split(",", 100);
        List<String> timing= Arrays.asList(arrOfStr);
        Spinner spinner = (Spinner) findViewById(R.id.timings);
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,timing);
        spinner.setAdapter(adapter);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = spinner.getSelectedItem().toString();
                sendData(date);
            }
        });
        if(user.equals("admin@mail.ru")){
            delete.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference mDbRef = mDatabase.getReference();
                        mDbRef.child("movies").child(String.valueOf(id_movie)).removeValue();
                    Intent intent = new Intent(ScreenSelection.this,HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ScreenSelection.this, MovieRedact.class);
                    Bundle extras = new Bundle();
                    extras.putInt("id_movie",id_movie);
                    intent.putExtras(extras);
                    startActivity(intent);

                }
            });
        }


        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(trailer)));
        Log.i("url ",trailer);
        }
    });
    }

    private void sendData(String date){
        Intent intent = new Intent(ScreenSelection.this, SeatSelection.class);
        Bundle extras = new Bundle();
        extras.putString("title",titleText.getText().toString());
        extras.putString("date",date);

        extras.putInt("price",price);
        extras.putInt("id_movie",id_movie);
        intent.putExtras(extras);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
//    public void prepareMovie(int m_id){
//        SQLiteDatabase MyDB = database_helper.getReadableDatabase();
//        Cursor cursor = MyDB.rawQuery("Select m_id, Movie_name, MovieImg_url1, Movie_type, MovieLength, Movie_rating, MovieTrailer, Movie_releaseDate, MovieCategory, Movie_date_time, Movie_description, Movie_price  FROM  MovieTable where m_id = ?", new String[]{String.valueOf(m_id)});
//        if (cursor.moveToFirst()) {
//            titleText.setText(cursor.getString(1));
//            genreText.setText(cursor.getString(3));
//            ratingText.setText(cursor.getString(5)+" | "+cursor.getString(4));
//            date_tv_data.setText(cursor.getString(7));
//            movie_category.setText(cursor.getString(8));
//            descriptionText.setText(cursor.getString(10));
//            priceText.setText(cursor.getString(11)+" руб.");
//
//        }}


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

    @Override
    public void onClick(View view) {

    }
}