package com.flag.cinema.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.flag.cinema.models.Bookings;
import com.flag.cinema.models.Movie;
import com.flag.cinema.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActicity extends AppCompatActivity {

    TextView movieName,amount;
    EditText cardNumberET, dateET, cvv, address;
    Button payment;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    Bundle extras;
    Intent intent;

    Movie movie = new Movie("1","1","1","1","1","1","1","1","1",1,1,1);
    int m_id, price, seats;
    private DatabaseReference mDatabase;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";
    public static  String seatsIndex, seatsList;
    String title="", showDate="", currentUser="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_acticity);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d32f2f")));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        movieName =(TextView)findViewById(R.id.movieName);
        amount=(TextView)findViewById(R.id.amount);
        payment=(Button)findViewById(R.id.paymentbtn);
        cvv =(EditText)findViewById(R.id.cvv);
        address=(EditText)findViewById(R.id.address);
        dateET =(EditText)findViewById(R.id.dateET);
        cardNumberET =(EditText)findViewById(R.id.numberET);




        intent = getIntent();
        extras = intent.getExtras();
        price=extras.getInt("price");
        m_id=extras.getInt("id_movie");
//        prepareMovie(m_id);
        title = extras.getString("title");
        seats = extras.getInt("seats");
        showDate = extras.getString("date");

        currentUser=extras.getString("currentUser");


        int amountval = price* seats;


        amount.setText(Integer.toString(amountval)+" руб.");
        movieName.setText(title);

        payment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validation();

            }
        });

        cardNumberET.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Remove spacing char
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
            }
        });
        dateET.addTextChangedListener(new TextWatcher() {
            private static final char space = '/';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Remove spacing char
                if (s.length() > 0 && (s.length() % 3) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                if (s.length() > 0 && (s.length() % 3) == 0) {
                    char c = s.charAt(s.length() - 1);
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 1) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
            }
        });
    }
    public void addBook(String title, String showDates, String seatIndices, String seatNames, String currentUser, int movie_id){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference databaseReference = mDatabase.push();
        Bookings book = new Bookings(databaseReference.getKey(),title,showDates,seatNames,currentUser,seatIndices,movie_id);
        mDatabase.child("bookings").child(databaseReference.getKey()).setValue(book);}

    public void validation(){
        if(cardNumberET.length()<12)
            cardNumberET.setError("Номер карты должен быть > 12");
        else if(dateET.length()<4)
            dateET.setError("Дата должна быть > 4");
        else if(address.length()<6)
            address.setError("Адрес должен быть > 6");
        else if(cvv.length()<2)
            address.setError("CVV должен быть > 2");
        else{
            String [] seatsNumbers=extras.getStringArray("seatsNumbers");
            String [] seatsNames=extras.getStringArray("seatsNames");

            for (int i = 0; i < seatsNames.length; i++) {
                seatsList = seatsNames[i];
                seatsIndex = seatsNumbers[i];
                addBook(title, showDate, seatsIndex, seatsList,currentUser, m_id);
//
            }
//            NumberedAdapter.seats.clear();
            Intent intent = new Intent(PaymentActicity.this, BookingsActivity.class);
            Toast.makeText(getApplicationContext(),"Оплачено успешно",Toast.LENGTH_LONG).show();


            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
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
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Выход на главный экран")
                .setMessage("Вы уверены, что хотите отменить оплату?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PaymentActicity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    int i=1;
                })
                .setNegativeButton("Нет", null)
                .show();
    }
}