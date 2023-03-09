package com.flag.cinema.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.flag.cinema.models.Movie;
import com.flag.cinema.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";
    public static final String Phone = "phoneKey";
    public static final String Name = "nameKey";
    TextView emailTxt,nameTxt,mobileTxt;
    String emailVal="";
    String mobileVal="";
    String nameVal="";

    Button logoutBtn, addfilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        emailVal = sharedpreferences.getString(Email, "");
        nameVal = sharedpreferences.getString(Name, "");
        mobileVal = sharedpreferences.getString(Phone, "");


        emailTxt =(TextView)findViewById(R.id.tv_email);
        nameTxt=(TextView)findViewById(R.id.tv_name);
        mobileTxt=(TextView)findViewById(R.id.tv_phone);
        logoutBtn =(Button)findViewById(R.id.logoutBtn);
        addfilm =(Button)findViewById(R.id.addfilm);
        emailTxt.setText(emailVal);

//        nameTxt.setText(nameVal);
//        mobileTxt.setText(mobileVal);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDbRef = mDatabase.getReference();
        mDbRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();

                for (DataSnapshot snapshotIter : snapshotIterator){
                    if(snapshotIter.child("email").getValue().toString().equals(emailVal))//adds every child attributes to an array list called flightList without filtering
                        mobileTxt.setText(snapshotIter.child("phone").getValue().toString());
                    nameTxt.setText(snapshotIter.child("name").getValue().toString());
//
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent b = new Intent(ProfileActivity.this, HomeActivity.class);

                        startActivity(b);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.navigation_dashboard:
                        Intent a = new Intent(ProfileActivity.this, BookingsActivity.class);
                        startActivity(a);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.navigation_notifications:

                        break;
                }
                return false;
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();
                FirebaseAuth.getInstance().signOut();
                Intent b = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(b);
                finish();
            }
        });
        if(emailVal.equals("admin@mail.ru")) {
            addfilm.setVisibility(View.VISIBLE);
            addfilm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addMovie1(1, "Железный человек", R.drawable.iron_man, "Боевик",
                            "https://www.youtube.com/watch?v=fMKZMI8ByTs", "09.10.2022", "16+",
                            "12:00 14.12.2022,18:00 14.12.2022", "120 минут", "8.1",
                            "Миллиардер-изобретатель Тони Старк попадает в плен к Афганским террористам, которые пытаются заставить его создать оружие массового поражения. В тайне от своих захватчиков Старк конструирует высокотехнологичную киберброню, которая помогает ему сбежать. Однако по возвращении в США он узнаёт, что в совете директоров его фирмы плетётся заговор, чреватый страшными последствиями. Используя своё последнее изобретение, Старк пытается решить проблемы своей компании радикально.", 10);
                    addMovie1(2, "Терминатор", R.drawable.terminator, "Боевик",
                            "https://www.youtube.com/watch?v=uA4k5Vc5jFc", "10.11.2022", "18+",
                            "14:00 15.12.2022,14:00 20.12.2022", "140 минут", "9.2",
                            "История противостояния солдата Кайла Риза и киборга-терминатора, прибывших в 1984-й год из пост-апокалиптического будущего, где миром правят машины-убийцы, а человечество находится на грани вымирания. Цель киборга: убить девушку по имени Сара Коннор, чей ещё нерождённый сын к 2029 году выиграет войну человечества с машинами. Цель Риза: спасти Сару и остановить Терминатора любой ценой.", 11);
//
                    addMovie1(3, "Робокоп", R.drawable.robo, "Боевик",
                            "https://www.youtube.com/watch?v=fCKt93Y6bKY", "11.08.2022", "16+",
                            "10:00 16.12.2022,17:00 16.12.2022", "80 минут", "8.2",
                            "После гибели одного из лучших полицейских врачи-экспериментаторы создают из него неуязвимого киборга Робокопа, который в одиночку борется с бандой преступников. Однако прочная броня не спасает Робокопа от мучительных, обрывочных воспоминаний о прошлом: он постоянно видит кошмарные сны, в которых погибает от рук жестоких преступников. Теперь он не только ждёт правосудия,… но и жаждет мести!.", 12);

                    addMovie1(4, "Бегущий по лезвию 2049", R.drawable.bladerun, "Боевик",
                            "https://www.youtube.com/watch?v=taQW31SVPCk", "12.08.2022", "18+",
                            "11:00 17.12.2022,15:00 17.12.2022", "90 минут", "8.5",
                            "Продолжение культового фильма «Бегущий по лезвию», действие которого разворачивается через несколько десятилетий.", 15);
                    Toast.makeText(ProfileActivity.this, "Фильмы успешно добавлены", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void addMovie1(int  id, String movie_name,
                          int movie_img, String movie_type, String movie_trailer,
                          String movie_releaseDate,
                          String movie_category, String movie_date_time, String movie_Length,
                          String movie_Rating, String movie_Description, int movie_Price){
        Movie movie = new Movie(movie_name, movie_type, movie_Length, movie_category, movie_Rating,movie_trailer, movie_releaseDate, movie_date_time, movie_Description, movie_img, id, movie_Price);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("movies").child(String.valueOf(id)).setValue(movie);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Выход")
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