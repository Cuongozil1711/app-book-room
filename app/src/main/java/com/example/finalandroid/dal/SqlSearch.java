package com.example.finalandroid.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.finalandroid.model.Hotel;
import com.example.finalandroid.model.NameSearch;

import java.util.ArrayList;
import java.util.List;

public class SqlSearch extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Search.db";
    private static int DATABASE_VERSION = 3;
    public SqlSearch(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE searchHotel(" +
                "id INTEGER PRIMARY KEY," +
                "nameSearch TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public long addSearch(NameSearch i){
        ContentValues values = new ContentValues();
        values.put("id", i.getId());
        values.put("nameSearch", i.getNameSearch());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("searchHotel", null, values);
    }

    public List<NameSearch> getAllKeySearch(){
        List<NameSearch> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("searchHotel", null, null, null, null, null,
                null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            list.add(new NameSearch(id, name));
        }
        return list;
    }

    public int delete(int id){
        ContentValues values = new ContentValues();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        return sqLiteDatabase.delete("searchHotel",whereClause, whereArgs);
    }
}
