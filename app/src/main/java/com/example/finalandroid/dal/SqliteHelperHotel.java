package com.example.finalandroid.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.finalandroid.model.Hotel;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelperHotel extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HotelList.db";
    private static int DATABASE_VERSION = 2;
    public SqliteHelperHotel(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlHotel = "CREATE TABLE hotel(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT, address TEXT, describe TEXT, star TEXT, image TEXT, phone TEXT)";
        sqLiteDatabase.execSQL(sqlHotel);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public long addHotel(Hotel i){
        ContentValues values = new ContentValues();
        values.put("id", i.getId());
        values.put("name", i.getName());
        values.put("address", i.getAddress());
        values.put("describe", i.getDescribe());
        values.put("star", i.getStar());
        values.put("image", i.getImage());
        values.put("phone", i.getPhone());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("hotel", null, values);
    }

    // lay theo date
    public List<Hotel> searchByAddress(String diachi){
        List<Hotel> list = new ArrayList<>();
        String whereClause = "address like ?";
        String[] whereArgs = {"%" + diachi + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("hotel", null, whereClause, whereArgs, null, null,
                null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String address = rs.getString(2);
            String describe = rs.getString(3);
            String star = rs.getString(4);
            String image = rs.getString(5);
            String phone = rs.getString(6);
            list.add(new Hotel(id, name, address, describe, star, image, phone));
        }
        return list;
    }

    // lay theo name
    public List<Hotel> searchByName(String name1){
        List<Hotel> list = new ArrayList<>();
        String whereClause = "address like ?";
        String[] whereArgs = {"%" + name1 + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("hotel", null, whereClause, whereArgs, null, null,
                null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String address = rs.getString(2);
            String describe = rs.getString(3);
            String star = rs.getString(4);
            String image = rs.getString(5);
            String phone = rs.getString(6);
            list.add(new Hotel(id, name, address, describe, star, image, phone));
        }
        return list;
    }


    // lay theo name
    public List<Hotel> getAllHotel(){
        List<Hotel> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("hotel", null, null, null, null, null,
                null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String address = rs.getString(2);
            String describe = rs.getString(3);
            String star = rs.getString(4);
            String image = rs.getString(5);
            String phone = rs.getString(6);
            list.add(new Hotel(id, name, address, describe, star, image, phone));
        }
        return list;
    }

    public int deleteAllHotel(int id){
        ContentValues values = new ContentValues();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        return sqLiteDatabase.delete("hotel",whereClause, whereArgs);
    }
}
