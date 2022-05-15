package com.example.finalandroid.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.finalandroid.User;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Hotel.db";
    private static int DATABASE_VERSION = 1;
    public SqliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE userTable(" +
                "id INTEGER PRIMARY KEY," +
                "email TEXT, name TEXT, password TEXT, phone TEXT, age TEXT, address TEXT, typeLogin TEXT, gender TEXT, image TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    public long addItem(User i){
        ContentValues values = new ContentValues();
        values.put("id", i.getId());
        values.put("email", i.getEmail());
        values.put("name", i.getName());
        values.put("password", i.getPassword());
        values.put("phone", i.getPhone());
        values.put("age", i.getAge());
        values.put("address", i.getAddress());
        values.put("typeLogin", i.getTypeLogin());
        values.put("gender", i.getGener());
        values.put("image", i.getImage());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("userTable", null, values);
    }
    public User getUser(){
        List<User> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("userTable", null, null, null, null, null,
                null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String email = rs.getString(1);
            String name = rs.getString(2);
            String password = rs.getString(3);
            String phone = rs.getString(4);
            String age = rs.getString(5);
            String address = rs.getString(6);
            String typeLogin = rs.getString(7);
            String gender = rs.getString(8);
            String image = rs.getString(9);
            list.add(new User(id, email, name, password, phone, age, address, typeLogin, gender, image));
        }
        if(list.size() > 0)
        return list.get(0);
        else return null;
    }

    public int delete(int id){
        ContentValues values = new ContentValues();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        return sqLiteDatabase.delete("userTable",whereClause, whereArgs);
    }

    public int update(User i){
        ContentValues values = new ContentValues();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        values.put("email", i.getEmail());
        values.put("name", i.getName());
        values.put("password", i.getPassword());
        values.put("phone", i.getPhone());
        values.put("age", i.getAge());
        values.put("address", i.getAddress());
        values.put("typeLogin", i.getTypeLogin());
        values.put("gender", i.getGener());
        values.put("image", i.getImage());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(i.getId())};
        return sqLiteDatabase.update("userTable",values, whereClause, whereArgs);
    }

}
