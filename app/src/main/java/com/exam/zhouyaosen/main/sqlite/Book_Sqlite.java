package com.exam.zhouyaosen.main.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.exam.zhouyaosen.main.sqlite.bean.Book;

import java.util.ArrayList;
import java.util.List;

public class Book_Sqlite extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "js";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ICON = "icon";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TIME = "time";

    public Book_Sqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(" +
                COLUMN_ID + " varchar primary key," +
                COLUMN_ICON + " varchar," +
                COLUMN_TITLE + " varchar," +
                COLUMN_TIME + " varchar)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 在数据库版本更新时执行操作（如果需要）
    }


    // 添加数据
    public void addData(String id, String icon, String title, String time) {
        SQLiteDatabase db = getWritableDatabase();

        // Check if the title already exists in the database
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_TITLE}, COLUMN_TITLE + " = ?", new String[]{title}, null, null, null);
        if (cursor.moveToFirst()) {
            // Title exists, update the time
            ContentValues values = new ContentValues();
            values.put(COLUMN_TIME, time);
            db.update(TABLE_NAME, values, COLUMN_TITLE + " = ?", new String[]{title});
        } else {
            // Title does not exist, insert new data
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, id);
            values.put(COLUMN_ICON, icon);
            values.put(COLUMN_TITLE, title);
            values.put(COLUMN_TIME, time);
            db.insert(TABLE_NAME, null, values);
        }

        cursor.close();
        db.close();
    }
    public  void delete(List<Book> books,List<Boolean> b){
        SQLiteDatabase db = getWritableDatabase();
        for (int i = 0; i < b.size(); i++) {
            if (b.get(i)){
                db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{books.get(i).getId()});
            }
        }
        db.close();
    }

    // 删除数据
    public void deleteData(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});
        db.close();
    }

    // 更新数据
    public void updateData(String id, String newIcon, String newTitle, String newTime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ICON, newIcon);
        values.put(COLUMN_TITLE, newTitle);
        values.put(COLUMN_TIME, newTime);
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{id});
        db.close();
    }
    @SuppressLint("Range")
    // 查询所有数据
    public List<Book> getAllData() {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String icon = cursor.getString(cursor.getColumnIndex(COLUMN_ICON));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                Book book = new Book(id,  title,Long.parseLong(time),icon );
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }
}
