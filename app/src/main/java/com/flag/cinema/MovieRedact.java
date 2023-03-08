package com.flag.cinema;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MovieRedact extends AppCompatActivity {

    EditText name,type, trailer,date,releaseDate,length,category,rating,description;
    Button btn_send;
    Bundle extras;
    Intent intent;
    SQLiteHelper dbHelper;
    SQLiteHelper database_helper;

    SQLiteDatabase sqLiteDatabaseObj;
    int m_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d32f2f")));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        dbHelper = new SQLiteHelper(this);
        name =(EditText)findViewById(R.id.name);
        type =(EditText)findViewById(R.id.type);
        trailer =(EditText)findViewById(R.id.trailer);
        date =(EditText)findViewById(R.id.date);
        releaseDate =(EditText)findViewById(R.id.releaseDate);
        length =(EditText)findViewById(R.id.length);
        category =(EditText)findViewById(R.id.category);
        rating =(EditText)findViewById(R.id.rating);
        description =(EditText)findViewById(R.id.description);
        btn_send=findViewById(R.id.btn_send);

        intent = getIntent();
        extras = intent.getExtras();
        m_id=extras.getInt("id_movie");

        prepareAdminMovie(m_id);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().length()<2)
                    name.setError("Название должно быть > 2");
                else if(type.getText().toString().length()<4)
                    type.setError("Жанра должен быть > 4");
                else if(!trailer.getText().toString().contains("https://www.youtube.com/"))
                    trailer.setError("Трейлер должен содержать https://www.youtube.com/");
                else if(date.getText().toString().length()<5)
                    date.setError("Дата должна быть > 5");
                else if(releaseDate.getText().toString().length()<5)
                    releaseDate.setError("Дата релиза должна быть > 5");
                else if(length.getText().toString().length()<2)
                    length.setError("Продолжительность должна быть > 2");
                else if(category.getText().toString().length()<1)
                    category.setError("Категория должна быть > 1");
                else if(rating.getText().toString().length()<1)
                    rating.setError("Рейтинг должен быть > 1");
                else {
                    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference mDbRef = mDatabase.getReference();
                    mDbRef.child("movies").child(String.valueOf(m_id)).child("category").setValue(category.getText().toString());
                    mDbRef.child("movies").child(String.valueOf(m_id)).child("date_time").setValue(date.getText().toString());
                    mDbRef.child("movies").child(String.valueOf(m_id)).child("length").setValue(length.getText().toString());
                    mDbRef.child("movies").child(String.valueOf(m_id)).child("name").setValue(name.getText().toString());
                    mDbRef.child("movies").child(String.valueOf(m_id)).child("rating").setValue(rating.getText().toString());
                    mDbRef.child("movies").child(String.valueOf(m_id)).child("relaDate").setValue(releaseDate.getText().toString());
                    mDbRef.child("movies").child(String.valueOf(m_id)).child("trile").setValue(trailer.getText().toString());
                    mDbRef.child("movies").child(String.valueOf(m_id)).child("type").setValue(type.getText().toString());
//                    String query = "UPDATE MovieTable SET " +
//                            "Movie_name='" + name.getText().toString() + "'," +
//                            "Movie_type='" + type.getText().toString() + "'," +
//                            "MovieTrailer='" + trailer.getText().toString() + "'," +
//                            "Movie_date_time='" + date.getText().toString() + "'," +
//                            "Movie_releaseDate='" + releaseDate.getText().toString() + "'," +
//                            "MovieLength='" + length.getText().toString() + "'," +
//                            "MovieCategory='" + category.getText().toString() + "'," +
//                            "Movie_rating='" + rating.getText().toString() + "' " +
//                            "WHERE m_id=" + m_id;
//                    SQLiteDatabase db = dbHelper.getWritableDatabase();
//                    db.execSQL(query);
                    Intent intent = new Intent(MovieRedact.this, HomeActivity.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                }
            }
        });

    }
        public void prepareAdminMovie(int m_id){
            SQLiteDatabase MyDB = database_helper.getReadableDatabase();
            Cursor cursor = MyDB.rawQuery("Select m_id, Movie_name, MovieImg_url1, Movie_type, MovieLength, Movie_rating, MovieTrailer, Movie_releaseDate, MovieCategory, Movie_date_time, Movie_description, Movie_price  FROM  MovieTable where m_id = ?", new String[]{String.valueOf(m_id)});
            if (cursor.moveToFirst()) {
                name.setText(cursor.getString(1));
                type.setText(cursor.getString(3));
                length.setText(cursor.getString(4));
                rating.setText(cursor.getString(5));
                releaseDate.setText(cursor.getString(7));
                category.setText(cursor.getString(8));
                date.setText(cursor.getString(9));

            }}



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
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Выход на главный экран")
                .setMessage("Вы уверены, что хотите отменить редактирование?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MovieRedact.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    int i=1;
                })
                .setNegativeButton("Нет", null)
                .show();
    }
}
