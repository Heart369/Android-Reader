package com.exam.zhouyaosen.main.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.exam.zhouyaosen.main.sqlite.bean.SearchData;

import java.util.ArrayList;
import java.util.List;

public class Search_Sqlite extends SQLiteOpenHelper {
    private static final String DB_NAME = "search_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "js";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TIME = "time";

    public Search_Sqlite(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_TITLE + " VARCHAR, "
                + COLUMN_TIME + " VARCHAR)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 在数据库升级时执行操作
    }

    public void insertData(String title, String time) {
        SQLiteDatabase db = getWritableDatabase();

        // 先查询是否已存在相同标题的记录
        String[] columns = {COLUMN_TITLE};
        String selection = COLUMN_TITLE + "=?";
        String[] selectionArgs = {title};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        // 如果存在相同标题的记录，不进行插入操作
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return;
        }

        // 如果不存在相同标题的记录，则进行插入操作
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_TIME, time);
        db.insert(TABLE_NAME, null, values);

        cursor.close();
        db.close();
    }


    public void deleteData(String title) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_TITLE + "=?";
        String[] whereArgs = {title};
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }
    public void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void updateData(String oldTitle, String newTitle, String newTime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newTitle);
        values.put(COLUMN_TIME, newTime);
        String whereClause = COLUMN_TITLE + "=?";
        String[] whereArgs = {oldTitle};
        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    @SuppressLint("Range")
    public List<SearchData> getAllData() {
        List<SearchData> dataList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_TITLE, COLUMN_TIME};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
            SearchData data = new SearchData(title, time);
            dataList.add(data);
        }
        cursor.close();
        db.close();
        return dataList;
    }
}
