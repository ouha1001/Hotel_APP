package com.example.hotelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.UFormat;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;

public class Database extends SQLiteOpenHelper {

    private Context context;
    private static  final  String DB_NAME = "HotelManagement";
    private static  final  int Version_db = 1;
    // Table Reservation
    private static  final  String TName = "Reservations";
    private static  final  String ID_Reservation = "ID_Reservation";
    private static  final  String Id_Kunde_R = "Id_Kunde";
    private static  final  String Id_Zimmer_R = "Id_Zimmer";
    private static  final  String DateIn = "DateIn";
    private static  final  String DateOut = "DateOut";
    private static  final  String Gesamt = "Gesamt";
    // Table Kunden
    private static  final  String TName2 = "Kunden";
    private static  final  String ID_Kunde = "ID_Kunde";
    private static  final  String Vorname = "Vorname";
    private static  final  String Nachname = "Nachname";
    private static  final  String Geburtstag = "Geburtstag";
    private static  final  String Email = "Email";
    private static  final  String Tel = "Tel";
    private static  final  String Nationalität = "Nationalität";
    // Table zimmer
    private static  final  String TName3 = "Zimmer";
    private static  final  String ID_Zimmer = "ID_Zimmer";
    private static  final  String Type = "Type";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, Version_db);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE "+TName+
                        "("+ID_Reservation+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        Id_Kunde_R+" INTEGER ,"+
                        Id_Zimmer_R+"INTEGER ,"+
                        DateIn+" DATE,"+
                        DateOut+"DATE,"+
                        Gesamt+" INTEGER);";
        String query1 =
                "CREATE TABLE "+TName2+
                        "("+ID_Kunde+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        Vorname+" TEXT,"+
                        Nachname+"TEXT,"+
                        Geburtstag+" DATE,"+
                        Email+"TEXT,"+
                        Tel+" INTEGER," +
                        Nationalität+"TEXT);";
        String query2 =
                "CREATE TABLE "+TName3+
                        "("+ID_Zimmer+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        Type+" VARCHAR(50));";
        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TName);
        db.execSQL("DROP TABLE IF EXISTS " + TName2);
        db.execSQL("DROP TABLE IF EXISTS " + TName3);
        onCreate(db);
    }

    void addReservation(int id_Kunde, int id_Zimmer, Date date_In , Date date_Out, int gesamt){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Id_Kunde_R, id_Kunde);
        contentValues.put(Id_Zimmer_R, id_Zimmer);
        contentValues.put(DateIn, String.valueOf(date_In));
        contentValues.put(DateOut, String.valueOf(date_Out));
        contentValues.put(Gesamt, gesamt);

        long result = database.insert(TName, null, contentValues);

        if(result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context, "Successfully!", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean addUser(String vorname,String nachname,Date geburtstag , String email, int tel, String land){

        boolean res=false;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Vorname,vorname);
        contentValues.put(Nachname,nachname);
        contentValues.put(Geburtstag,String.valueOf(geburtstag));
        contentValues.put(Email,email);
        contentValues.put(Tel,String.valueOf(tel));
        contentValues.put(Nationalität,land);

        long result = database.insert(TName2, null, contentValues);

        if(result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
            res=false;
        }else {
            Toast.makeText(context, "Successfully!", Toast.LENGTH_SHORT).show();
            res=true;
        }


        return res;
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TName;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;

        if(database != null){
            cursor = database.rawQuery(query, null);
        }
        return cursor;
        }
    }