package com.flag.cinema;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";
    TextView emailTxt,nameTxt,mobileTxt;
    String emailVal="";
    String mobileVal="";
    String nameVal="";
    SQLiteHelper database_helper;
    SQLiteDatabase sqLiteDatabaseObj;
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d32f2f")));

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        emailVal = sharedpreferences.getString(Email, "");
        database_helper = new SQLiteHelper(this);

        emailTxt =(TextView)findViewById(R.id.tv_email);
        nameTxt=(TextView)findViewById(R.id.tv_name);
        mobileTxt=(TextView)findViewById(R.id.tv_phone);
        logoutBtn =(Button)findViewById(R.id.logoutBtn);

        emailTxt.setText(emailVal);
        getProfile();
        nameTxt.setText(nameVal);
        mobileTxt.setText(mobileVal);

        BottomNavigationView navView = findViewById(R.id.nav_view);


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent b = new Intent(ProfileActivity.this,HomeActivity.class);

                        startActivity(b);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.navigation_dashboard:
                        Intent a = new Intent(ProfileActivity.this,BookingsActivity.class);
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
                Intent b = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(b);
                finish();
            }
        });
    }


    public void getProfile() {
        String select_query= "SELECT name,mobile FROM  UserTable where email =  '" + "" +emailVal+ "" + "' ";

        sqLiteDatabaseObj = database_helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabaseObj.rawQuery(select_query, null);
        if (cursor.moveToFirst()) {
            do {
                nameVal=cursor.getString(0);
                mobileVal=cursor.getString(1);
            }while (cursor.moveToNext());
        }
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