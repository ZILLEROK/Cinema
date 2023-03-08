package com.flag.cinema;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SQLiteHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME="UserDataBase479";

    public static final String TABLE_NAME="UserTable";

    public static final String Table_Column_ID="id";
    public static final String Table_Column_1_Name="name";
    public static final String Table_Column_2_Email="email";
    public static final String Table_Column_3_Mobile="mobile";
    public static final String Table_Column_4_Password="password";
    private DatabaseReference mDatabase;
    public static final String SEATS_TABLE_NAME="SeatsTable";

    public static final String Seats_Table_Column_ID="s_id";
    public static final String Seats_Table_Title_Name="title_name";
    public static final String Seats_Table_Screen_Name="screen_name";
    public static final String Seats_Table_Show_Name="show_name";
    public static final String Seats_Table_Show_Date="show_date";
    public static final String Seats_Table_Seat_Index="seat_index";
    public static final String Seats_Table_Seat_Name="seat_name";
    public static final String Seats_Booked_user ="seats_booked_user";
    public static final String Seats_Movie_ID = "movie_id";
    //public static  class MovieTable{
    public static final String MOVIE_TABLE_NAME="MovieTable";
        public static final String Movie_ID = "m_id";
        public static final String Movie_NAME = "Movie_name";
        public static final String Movie_img_url = "MovieImg_url1";
        public static final String Movie_Type = "Movie_type";
        public static final String Movie_trailer= "MovieTrailer";
    public static final String Movie_date_time= "Movie_date_time";
        public static final String Movie_releaseDate = "Movie_releaseDate";
        public static final String Movie_Length = "MovieLength";
        public static final String Movie_category= "MovieCategory"; // +13   +18
        public static final String Movie_Rating= "Movie_rating";
    public static final String Movie_Description= "Movie_description";
    public static final String Movie_Price= "Movie_price";


    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID +" INTEGER PRIMARY KEY, "
                +Table_Column_1_Name+" VARCHAR, "+Table_Column_2_Email+" VARCHAR, "+Table_Column_3_Mobile+" VARCHAR, "
                +Table_Column_4_Password+" VARCHAR)";
        String CREATE_SEATS_TABLE="CREATE TABLE IF NOT EXISTS "+SEATS_TABLE_NAME+" ("+Seats_Table_Seat_Index+" VARCHAR, "+Seats_Table_Seat_Name+" VARCHAR)";
        String CREATE_MOVIE_TABLE="CREATE TABLE IF NOT EXISTS "+ MOVIE_TABLE_NAME+" ("+Movie_ID +" INTEGER PRIMARY KEY, "
                +Movie_NAME+" VARCHAR, "+Movie_img_url+" INTEGER, "+Movie_Type+" VARCHAR, "+Movie_trailer+" VARCHAR, "
                +Movie_releaseDate+" VARCHAR, "+Movie_Length+" VARCHAR, "+Movie_category+" VARCHAR, "+Movie_Rating+" VARCHAR, "
                +Movie_date_time+" VARCHAR, "+Movie_Description+" VARCHAR , "+Movie_Price+" INTEGER)";
        database.execSQL(CREATE_TABLE);
        database.execSQL(CREATE_SEATS_TABLE);
        database.execSQL(CREATE_MOVIE_TABLE);


        addMovie1(1,"Железный человек",R.drawable.iron_man,"Боевик",
                "https://www.youtube.com/watch?v=fMKZMI8ByTs","09.10.2022","16+",
        "12:00 14.12.2022,18:00 14.12.2022", "120 минут","8.1",
                "Миллиардер-изобретатель Тони Старк попадает в плен к Афганским террористам, которые пытаются заставить его создать оружие массового поражения. В тайне от своих захватчиков Старк конструирует высокотехнологичную киберброню, которая помогает ему сбежать. Однако по возвращении в США он узнаёт, что в совете директоров его фирмы плетётся заговор, чреватый страшными последствиями. Используя своё последнее изобретение, Старк пытается решить проблемы своей компании радикально.", 10, database);
//        addMovie1(2,"Терминатор",R.drawable.terminator,"Боевик",
//                "https://www.youtube.com/watch?v=uA4k5Vc5jFc","10.11.2022","18+",
//                "14:00 15.12.2022,14:00 20.12.2022", "140 минут","9.2",
//                "История противостояния солдата Кайла Риза и киборга-терминатора, прибывших в 1984-й год из пост-апокалиптического будущего, где миром правят машины-убийцы, а человечество находится на грани вымирания. Цель киборга: убить девушку по имени Сара Коннор, чей ещё нерождённый сын к 2029 году выиграет войну человечества с машинами. Цель Риза: спасти Сару и остановить Терминатора любой ценой.", 11);
////
//        addMovie1(3,"Робокоп",R.drawable.robo,"Боевик",
//                "https://www.youtube.com/watch?v=fCKt93Y6bKY","11.08.2022","16+",
//                "10:00 16.12.2022,17:00 16.12.2022", "80 минут","8.2",
//                "После гибели одного из лучших полицейских врачи-экспериментаторы создают из него неуязвимого киборга Робокопа, который в одиночку борется с бандой преступников. Однако прочная броня не спасает Робокопа от мучительных, обрывочных воспоминаний о прошлом: он постоянно видит кошмарные сны, в которых погибает от рук жестоких преступников. Теперь он не только ждёт правосудия,… но и жаждет мести!.", 12);
//
//        addMovie1(4,"Бегущий по лезвию 2049",R.drawable.bladerun,"Боевик",
//                "https://www.youtube.com/watch?v=taQW31SVPCk","12.08.2022","18+",
//                "11:00 17.12.2022,15:00 17.12.2022", "90 минут","8.5",
//                "Продолжение культового фильма «Бегущий по лезвию», действие которого разворачивается через несколько десятилетий.", 15);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+SEATS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+MOVIE_TABLE_NAME);
        onCreate(db);

    }
    public void addMovie(int id, String movie_name,
                          int movie_img, String movie_type, String movie_trailer,
                          String movie_releaseDate,
                          String movie_category, String movie_date_time, String movie_Length,
                          String movie_Rating, String movie_Description, int movie_Price, SQLiteDatabase sd){
        ContentValues c= new ContentValues();
        c.put(Movie_ID,id);
        c.put(Movie_NAME,movie_name);
        c.put(Movie_img_url,movie_img);
        c.put(Movie_Type,movie_type);
        c.put(Movie_trailer,movie_trailer);
        c.put(Movie_date_time,movie_date_time);
        c.put(Movie_releaseDate,movie_releaseDate);
        c.put(Movie_Length,movie_Length);
        c.put(Movie_category,movie_category);
        c.put(Movie_Rating,movie_Rating);
        c.put(Movie_Description,movie_Description);
        c.put(Movie_Price,movie_Price);
        sd.insert(MOVIE_TABLE_NAME,null,c);
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("movies").child(movie_name).setValue(movie_img);

    }

    public void addMovie1(int  id, String movie_name,
                         int movie_img, String movie_type, String movie_trailer,
                         String movie_releaseDate,
                         String movie_category, String movie_date_time, String movie_Length,
                         String movie_Rating, String movie_Description, int movie_Price, SQLiteDatabase sd){
       Movie movie = new Movie(movie_name, movie_type, movie_Length, movie_category, movie_Rating,movie_trailer, movie_releaseDate, movie_date_time, movie_Description, movie_img, id, movie_Price);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("movies").child(String.valueOf(id)).setValue(movie);
//        mDatabase.child("movies").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
//                ContentValues c = new ContentValues();
//                for (DataSnapshot snapshotIter : snapshotIterator) {
//                    //adds every child attributes to an array list called flightList without filtering
//                    c.put(Movie_ID, snapshotIter.child("id").getValue(Integer.class));
//                    c.put(Movie_NAME, snapshotIter.child("name").getValue().toString());
//                    c.put(Movie_img_url, snapshotIter.child("imgUrl").getValue(Integer.class));
//                    c.put(Movie_Type, snapshotIter.child("type").getValue(String.class));
//                    c.put(Movie_trailer, snapshotIter.child("trile").getValue(String.class));
//                    c.put(Movie_date_time, snapshotIter.child("date_time").getValue(String.class));
//                    c.put(Movie_releaseDate, snapshotIter.child("relaDate").getValue(String.class));
//                    c.put(Movie_Length, snapshotIter.child("length").getValue(String.class));
//                    c.put(Movie_category, snapshotIter.child("category").getValue(String.class));
//                    c.put(Movie_Rating, snapshotIter.child("rating").getValue(String.class));
//                    c.put(Movie_Description, snapshotIter.child("description").getValue(String.class));
//                    c.put(Movie_Price, snapshotIter.child("price").getValue(Integer.class));
//                    sd.insert(MOVIE_TABLE_NAME, null, c);
////                    Movie movie = new Movie(snapshotIter.child("name").getValue().toString()
////                            , snapshotIter.child("type").getValue(String.class)
////                            , snapshotIter.child("length").getValue(String.class)
////                            , snapshotIter.child("category").getValue(String.class)
////                            , snapshotIter.child("rating").getValue(String.class)
////                            , snapshotIter.child("trile").getValue(String.class)
////                            , snapshotIter.child("relaDate").getValue(String.class)
////                            , snapshotIter.child("date_time").getValue(String.class)
////                            , snapshotIter.child("description").getValue(String.class)
////                            , snapshotIter.child("imgUrl").getValue(Integer.class)
////                            , snapshotIter.child("id").getValue(Integer.class)
////                            , snapshotIter.child("price").getValue(Integer.class));
//
//                }
////
////
////                //recyclerViewAdapter = new RecyclerViewAdapter(movieList,getApplicationContext());
//////
////
////            }
////
////
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        }
    }
