package com.flag.cinema.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.flag.cinema.models.Movie;
import com.flag.cinema.R;
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


    SharedPreferences sharedpreferences;

    Bundle extras;
    Intent intent;

    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

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


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }






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

    private void deleteBook(String id_book) {

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