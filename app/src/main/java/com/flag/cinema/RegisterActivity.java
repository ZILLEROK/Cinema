package com.flag.cinema;

import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText name,email,mobile,password;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Boolean EditTextEmptyHolder;
    String NameHolder, EmailHolder, MobileHolder, PasswordHolder;
    String F_Result = "Not_Found";
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#d32f2f")));


        TextView login = (TextView)findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();


        Button registerBtn = (Button)findViewById(R.id.registerbtn);
        sqLiteHelper = new SQLiteHelper(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (EditText)findViewById(R.id.nameET);
                email = (EditText)findViewById(R.id.emailET);
                mobile = (EditText)findViewById(R.id.mobileET);
                password = (EditText)findViewById(R.id.passwordET);

                if(name.getText().toString().length()<2)
                    name.setError("Имя должно быть > 2");
                else if(!email.getText().toString().contains("@"))
                    email.setError("Почта введена некорректно");
                else if(mobile.getText().toString().length()<4)
                    mobile.setError("Телефон должен быть > 4");
                else if(password.getText().toString().length()<6)
                    password.setError("Пароль должен быть >6");
                else {

                    // User userInfo = new User(name);
                    String email1 = email.getText().toString();
                    String password1 = password.getText().toString();
                    Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    mAuth.createUserWithEmailAndPassword(email1,password1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                Users userInfo = new Users(name.getText().toString(),email.getText().toString(),password.getText().toString(),mobile.getText().toString());
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("users").child(user.getUid()).setValue(userInfo);
                                Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                                startActivity(mainIntent);

                        }
                    }
                    );
                }

//                if(name.getText().toString().length()<2)
//                    name.setError("Имя должно быть > 2");
//                else if(!email.getText().toString().contains("@"))
//                    email.setError("Почта введена некорректно");
//                else if(mobile.getText().toString().length()<4)
//                    mobile.setError("Телефон должен быть > 4");
//                else if(password.getText().toString().length()<4)
//                    password.setError("Пароль должен быть >4");
//                else {
//                SQLiteDataBaseBuild();
//                SQLiteTableBuild();
//                CheckEditTextStatus();
//                CheckingEmailAlreadyExistsOrNot();
//                EmptyEditTextAfterDataInsert();
//            }
        }});

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
    public void SQLiteDataBaseBuild(){
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_Name + " VARCHAR, " + SQLiteHelper.Table_Column_2_Email + " VARCHAR, " + SQLiteHelper.Table_Column_3_Mobile + " VARCHAR, " + SQLiteHelper.Table_Column_4_Password + " VARCHAR);");
    }

    public void InsertDataIntoSQLiteDatabase(){

        if(EditTextEmptyHolder == true)
        {
            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,email,mobile,password) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+MobileHolder+"','"+PasswordHolder+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(RegisterActivity.this,"Пользователь успешно зарегистрирован", Toast.LENGTH_LONG).show();
            Intent in = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(in);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else {
            Toast.makeText(RegisterActivity.this,"Заполните все поля", Toast.LENGTH_LONG).show();
        }

    }


    public void EmptyEditTextAfterDataInsert(){
        name.getText().clear();
        email.getText().clear();
        mobile.getText().clear();
        password.getText().clear();
    }

    public void CheckEditTextStatus(){
        NameHolder = name.getText().toString() ;
        EmailHolder = email.getText().toString();
        MobileHolder = mobile.getText().toString();
        PasswordHolder = password.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){
            EditTextEmptyHolder = false ;
        }
        else {
            EditTextEmptyHolder = true ;
        }
    }


    public void CheckingEmailAlreadyExistsOrNot(){
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                F_Result = "Email Found";
                cursor.close();
            }
        }
        CheckFinalResult();
    }

    public void CheckFinalResult(){

        if(F_Result.equalsIgnoreCase("Email Found"))
        {
            Toast.makeText(RegisterActivity.this,"Email уже существует",Toast.LENGTH_LONG).show();
        }
        else {
            InsertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found" ;
    }


    public void createUser(View view) {
        if(name.getText().toString().length()<2)
            name.setError("Имя должно быть > 2");
        else if(!email.getText().toString().contains("@"))
            email.setError("Почта введена некорректно");
        else if(mobile.getText().toString().length()<4)
            mobile.setError("Телефон должен быть > 4");
        else if(password.getText().toString().length()<4)
            password.setError("Пароль должен быть >4");
        else {
        Users userInfo = new Users(name.getText().toString(),email.getText().toString(),password.getText().toString(),mobile.getText().toString());
        // User userInfo = new User(name);
            String email1 = email.getText().toString();
            String password1 = password.getText().toString();
            Intent mainIntent = new Intent(this, LoginActivity.class);
            mAuth.createUserWithEmailAndPassword(userInfo.getEmail(),userInfo.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("users").child(user.getUid()).setValue(userInfo);
                        Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                        startActivity(mainIntent);
                    }else{
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}