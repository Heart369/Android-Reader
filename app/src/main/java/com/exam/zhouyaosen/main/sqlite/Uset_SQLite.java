package com.exam.zhouyaosen.main.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Uset_SQLite extends SQLiteOpenHelper {
    public Uset_SQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="create table js(uid varchar,pass varchar,name varchar)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    @SuppressLint("Range")
    public String queryNameByUid(String uid) {
        String name = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"name"};
        String selection = "uid = ?";
        String[] selectionArgs = {uid};

        Cursor cursor = db.query("js", columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();

        return name;
    }

        public void updateNameByUid(String uid, String newName) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", newName);

            String whereClause = "uid = ?";
            String[] whereArgs = {uid};

            db.update("js", values, whereClause, whereArgs);
        }


}
