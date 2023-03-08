package com.flag.cinema;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    public static final String UserEmail = "";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "emailKey";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d32f2f")));
        mAuth = FirebaseAuth.getInstance();
        email =(EditText)findViewById(R.id.emailET);
        password =(EditText)findViewById(R.id.passwordET);

        sqLiteHelper = new SQLiteHelper(this);

        TextView register = (TextView)findViewById(R.id.register);
        Button loginbtn = (Button)findViewById(R.id.loginbtn);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextStatus();
                Login();
            }
        });

    }
    public void Login(){


        if(EditTextEmptyHolder) {

                    mAuth.signInWithEmailAndPassword(EmailHolder,PasswordHolder).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginActivity.this,"Успех",Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Email, EmailHolder);
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra(UserEmail, EmailHolder);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    });
        }
        else {
            Toast.makeText(LoginActivity.this,"Пожалуйста, введите почту и пароль.",Toast.LENGTH_LONG).show();
        }
    }

    public void CheckEditTextStatus(){
        EmailHolder = email.getText().toString();
        PasswordHolder = password.getText().toString();

        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){
            EditTextEmptyHolder = false ;
        }
        else {
            EditTextEmptyHolder = true ;
        }
    }

    public void CheckFinalResult(){
        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {
            Toast.makeText(LoginActivity.this,"Успех",Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Email, EmailHolder);
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra(UserEmail, EmailHolder);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        else {
            Toast.makeText(LoginActivity.this,"Почта или пароль введены неверно",Toast.LENGTH_LONG).show();
        }
        TempPassword = "NOT_FOUND" ;
    }


//    public void createUser(View view) {
//        if(EditTextEmptyHolder) {
//        Intent mainIntent = new Intent(this, HomeActivity.class);
//        mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    startActivity(mainIntent);
//                }else{
//                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//        else {
//         Toast.makeText(LoginActivity.this,"Пожалуйста, введите почту и пароль.",Toast.LENGTH_LONG).show();
//       }
//    }

}