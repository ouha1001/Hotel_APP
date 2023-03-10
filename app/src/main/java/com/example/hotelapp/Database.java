package com.example.hotelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

public class Database extends SQLiteOpenHelper {

    private final Context context;
    private static  final  String DB_NAME = "HotelManagement.db";
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
    private static  final  String Land = "Land";


    Map<Integer, String> Kunde;
    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, Version_db);
        this.context=context;
        SQLiteDatabase db =this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Zimmer bloc1=new Zimmer();
        Map<Integer, String> rooms = bloc1.getRooms();
        String query =
                "CREATE TABLE "+TName+
                        "("+ID_Reservation+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        Id_Kunde_R+" INTEGER ,"+
                        Id_Zimmer_R+" INTEGER ,"+
                        DateIn+" TEXT NOT NULL ,"+
                        DateOut+" TEXT NOT NULL ,"+
                        Gesamt+" INTEGER ); ";
        String query1 =
                "CREATE TABLE "+TName2+
                        "("+ID_Kunde+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        Vorname+" TEXT ,"+
                        Nachname+" TEXT ,"+
                        Geburtstag+" TEXT ,"+
                        Email+" TEXT ,"+
                        Tel+" TEXT ," +
                        Land+" TEXT);";

        db.execSQL(query);
        db.execSQL(query1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TName);
        db.execSQL("DROP TABLE IF EXISTS " + TName2);
        //db.execSQL("DROP TABLE IF EXISTS " + TName3);
        onCreate(db);
    }

    void addReservation(int id_Kunde, int id_Zimmer, String date_In , String date_Out, int gesamt){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Id_Kunde_R, id_Kunde);
        contentValues.put(Id_Zimmer_R, id_Zimmer);
        contentValues.put(DateIn, date_In);
        contentValues.put(DateOut, date_Out);
        contentValues.put(Gesamt, gesamt);

        long result = database.insert(TName, null, contentValues);

        if(result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context, "Successfully!", Toast.LENGTH_SHORT).show();
        }


    }

    public void addUser(String vorname, String nachname, String geburtstag , String email, String tel, String land){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Vorname,vorname);
        contentValues.put(Nachname,nachname);
        contentValues.put(Geburtstag,geburtstag);
        contentValues.put(Email,email);
        contentValues.put(Tel,tel);
        contentValues.put(Land,land);

        long result = database.insert(TName2, null, contentValues);

        if(result == -1){
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully!", Toast.LENGTH_SHORT).show();
        }


    }


    public int getdate(String checkin, String checkout) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select " + Id_Zimmer_R + " from " + TName + " where " + DateIn + " not between ? and ? and " + DateOut + " not between ? and ?", new String[]{checkin, checkout, checkin, checkout});
        cursor.moveToNext();
        return cursor.getCount();
    }

    public boolean checkusername(String username) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TName2 + " where " + Vorname + " = ?", new String[]{username});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean checkpassword(String user_id, @NonNull String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select " + Geburtstag + " from " + TName2 + " where " + ID_Kunde + " =?", new String[]{user_id});
        cursor.moveToNext();
        if (cursor.getString(0).replace("-", "").equals(password)) {
            cursor.close();
            return true;
        }
        cursor.close();
        Toast.makeText(context, "Wrong password, please check format", Toast.LENGTH_SHORT).show();
        return false;
    }

    void updateReservation(String checkout, String checkin, int id_room, int id_reservation, int price) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Id_Zimmer_R, id_room);
        cv.put(Gesamt, price);
        cv.put(DateIn, checkin);
        cv.put(DateOut, checkout);

        long result = database.update(TName, cv, ID_Reservation + " =?", new String[]{String.valueOf(id_reservation)});
        if ((result == -1)) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TName2;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;

        if (database != null) {
            cursor = database.rawQuery(query, null);

        } else {
            Toast.makeText(context,"Empty Data",Toast.LENGTH_SHORT).show();
        }
        return cursor;
    }
    Cursor readAllData(String Id) {
        String query = "SELECT * FROM " + TName2+" WHERE  ID_Kunde = "+Id;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;

        if (database != null) {
            cursor = database.rawQuery(query, null);

        } else {
            Toast.makeText(context,"Empty Data",Toast.LENGTH_SHORT).show();
        }
        return cursor;
    }

    Cursor storeAllDates(String id_room){
        String query = " SELECT "+DateIn+" ," +DateOut+" FROM " + TName +" WHERE Id_Zimmer = ?";
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = null;


        if(database != null){

            cursor = database.rawQuery(query, new String[] {id_room});

        }

        return cursor;
    }


    Cursor readAllDataReservationbyId(String customer_id){
        String query = "SELECT R.ID_Reservation,R.Id_Kunde,R.Id_Zimmer,R.DateIn,R.DateOut,R.Gesamt," +
                " k.Vorname,k.Nachname FROM " + TName+" R Inner join "+ TName2+" K " +
                " ON R.Id_Kunde = K.ID_Kunde where R.Id_Kunde =?";

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor ;

        if(database != null){
            cursor = database.rawQuery(query, new String[]{ customer_id });

        }
        else {
            cursor=null;
            Toast.makeText(context,"Empty Data",Toast.LENGTH_SHORT).show();
        }
        return cursor;
    }
   Cursor storeAllDataReservation(String ID_Customer){
       String query = " SELECT * FROM " + TName +" WHERE Id_Kunde = ?";
       SQLiteDatabase database = this.getWritableDatabase();

       Cursor cursor = null;


       if(database != null){

           cursor = database.rawQuery(query, new String[] {ID_Customer});

       }

       return cursor;
   }

    public Cursor getdatesbyroom(String roomid) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor=null;
        if (database != null) {
            cursor = database.rawQuery("select " + DateIn + " , "+ DateOut+" from " + TName + " where " + Id_Zimmer_R+ " = ?", new String[]{roomid});

        } else {
            Toast.makeText(context, "Empty Data", Toast.LENGTH_SHORT).show();
        }
        return cursor;

    }

   Cursor readAllDataReservation(){
       String query = "SELECT R.ID_Reservation,R.Id_Kunde,R.Id_Zimmer,R.DateIn,R.DateOut,R.Gesamt," +
               " k.Vorname,k.Nachname FROM " + TName+" R Inner join "+ TName2+" K " +
               " ON R.Id_Kunde = K.ID_Kunde ";

       SQLiteDatabase database = this.getReadableDatabase();
       Cursor cursor ;

       if(database != null){
           cursor = database.rawQuery(query, null);

       }
       else {
           cursor=null;
           Toast.makeText(context,"Empty Data",Toast.LENGTH_SHORT).show();
       }
       return cursor;
   }

    void DeleteReservation(String Id_R){
        SQLiteDatabase database= this.getWritableDatabase();
        long resultat =database.delete(TName,"ID_Reservation = ?", new String[]{Id_R});
       if(resultat == -1){
           Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();

       }else {
           //Toast.makeText(context, "Successfully!", Toast.LENGTH_SHORT).show();

       }
    }





}