package com.flag.cinema;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TicketActivity extends AppCompatActivity implements
        View.OnClickListener{

    Movie movie = new Movie("1","1","1","1","1","1","1","1","1",1,1,1);
    int image, m_id;
    String id_book, title,rating,runtime,genre, trailer, category, relase, user, seat, date, currentUser;
    private TextView titleText;
    private TextView ratingText;
    private TextView genreText;
    private TextView descriptionText;
    private TextView movie_category;
    private TextView number_Text;
    private TextView timeText;
    private TextView priceText;
    private TextView seatText;
    private TextView date_tv_data;
    private TextView userview;
    private TextView user1;
    private TextView duration;
    private ImageView imageText;


    private Button btn_del;
    private FloatingActionButton play_button;

    SQLiteHelper database_helper;
    SharedPreferences sharedpreferences;
    SQLiteDatabase sqLiteDatabaseObj;
    Bundle extras;
    Intent intent;
    Context c;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d32f2f")));
        database_helper = new SQLiteHelper(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

         currentUser = sharedpreferences.getString(Email, "");

        intent = getIntent();
        extras = intent.getExtras();
        m_id = extras.getInt("id_movie");
        id_book = extras.getString("id_book");
        title = extras.getString("title");
        date = extras.getString("date");
        seat = extras.getString("seat");
        user = extras.getString("user");
        timeText= findViewById(R.id.time);
        user1= findViewById(R.id.user);
        priceText = findViewById(R.id.price);
        userview=findViewById(R.id.userview);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDbRef = mDatabase.getReference();

        number_Text= findViewById(R.id.number_tick);
        movie_category = (TextView)findViewById(R.id.movie_category);
        date_tv_data = (TextView)findViewById(R.id.date_tv_data);
        titleText = (TextView)findViewById(R.id.title);
        descriptionText = (TextView)findViewById(R.id.description);
        genreText = (TextView)findViewById(R.id.genre);
        imageText = (ImageView)findViewById(R.id.image);
        seatText = findViewById(R.id.seat);
        ratingText = findViewById(R.id.duration);

        btn_del = (Button) findViewById(R.id.btn_del);
        play_button = findViewById(R.id.playButton);
        //prepareMovie(m_id);
//        number_Text.setText(id_book+"");
//
//        titleText.setText(title);
//
//        genreText.setText(movie.getType());
//        movie_category.setText(movie.getCategory());
//        date_tv_data.setText(movie.getRelaDate());
//        seatText.setText(seat);
//        ratingText.setText(movie.getRating()+" | "+movie.getLength());
//        priceText.setText(movie.getPrice()+" руб.");
//        timeText.setText(date);
//        descriptionText.setText(movie.getDescription());

        mDbRef.child("movies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                for (DataSnapshot snapshotIter : snapshotIterator){
                    if(Integer.parseInt(snapshotIter.child("id").getValue().toString())==m_id){
                        number_Text.setText(id_book);
                        imageText.setImageResource(Integer.parseInt(snapshotIter.child("imgUrl").getValue().toString()));
                        titleText.setText(title);
                        genreText.setText(snapshotIter.child("type").getValue().toString());
                        movie_category.setText(snapshotIter.child("category").getValue().toString());
                        date_tv_data.setText(snapshotIter.child("relaDate").getValue().toString());
                        seatText.setText(seat);
                        ratingText.setText(snapshotIter.child("rating").getValue().toString()+" | "+snapshotIter.child("length").getValue().toString());
                        priceText.setText(Integer.parseInt(snapshotIter.child("price").getValue().toString())+" руб.");
                        timeText.setText(date);
                        descriptionText.setText(snapshotIter.child("description").getValue().toString());

                    //Toast.makeText(TicketActivity.this,Integer.parseInt(snapshotIter.child("type").getValue().toString().toString()),Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            //recyclerViewAdapter = new RecyclerViewAdapter(movieList,getApplicationContext());
//





        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });
        if(currentUser.equals("admin@mail.ru")){
            userview.setVisibility(View.VISIBLE);
            user1.setVisibility(View.VISIBLE);
            user1.setText(user);
        }
if(date.equals("")){
    play_button.setVisibility(View.INVISIBLE);
    userview.setVisibility(View.VISIBLE);
    descriptionText.setText("Здравствуйте, к сожалению, ваш сеанс отменён, обратитесь, пожалуйста, к администрации кинотеатра по номеру +375291518511");
}
else {
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(movie.getTrile())));
                Log.i("url ",movie.getTrile());
            }
        });}
    }
    private void prepareMovie(int m_id){
        SQLiteDatabase MyDB = database_helper.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select m_id, Movie_name, MovieImg_url1, Movie_type, MovieLength, Movie_rating, MovieTrailer, Movie_releaseDate, MovieCategory, Movie_date_time, Movie_description, Movie_price  FROM  MovieTable where m_id = ?", new String[]{String.valueOf(m_id)});

        if (cursor.moveToFirst()) {
                movie.setId(cursor.getInt(0));
                movie.setName(cursor.getString(1));
                movie.setImgUrl(cursor.getInt(2));
                movie.setType(cursor.getString(3));
                movie.setLength(cursor.getString(4));
                movie.setRating(cursor.getString(5));
                movie.setTrile(cursor.getString(6));
                movie.setRelaDate(cursor.getString(7));
                movie.setCategory(cursor.getString(8));
                movie.setDate_time(cursor.getString(9));
                movie.setDescription(cursor.getString(10));
                movie.setPrice(cursor.getInt(11));

        }}
    private void deleteBook(String id_book) {

//        String delete_query = "DELETE FROM SeatsTable WHERE s_id=?";
//        sqLiteDatabaseObj = database_helper.getWritableDatabase();
//        SQLiteStatement statement = sqLiteDatabaseObj.compileStatement(delete_query);
//        statement.clearBindings();
//        statement.bindDouble(1, (double)id_book);
//        statement.execute();
//        sqLiteDatabaseObj.close();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDbRef = mDatabase.getReference();
        mDbRef.child("bookings").child(id_book).removeValue();
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

    @Override
    public void onClick(View view) {

    }

    public void onDelete() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Отмена билета")
                .setMessage("Вы уверены, что хотите отменить билет?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteBook(id_book);
                        Intent intent = new Intent(TicketActivity.this, BookingsActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        Toast.makeText(TicketActivity.this,"Билет успешно отменён", Toast.LENGTH_LONG).show();
                    }

                })
                .setNegativeButton("Нет", null)
                .show();
    }
}