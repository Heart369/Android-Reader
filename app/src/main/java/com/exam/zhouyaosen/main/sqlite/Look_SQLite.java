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
@SuppressLint("Range")
public class Look_SQLite extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "js";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_ICON = "icon";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_BOOK_ID = "bookid";
    private static final String COLUMN_LOOK_ID = "lookid";
    private static final String COLUMN_LOOK_PO = "lookpo";
    private static final String COLUMN_PROGRESS = "progress";

    public Look_SQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public  void delete(List<Book> books,List<Boolean> b){
        SQLiteDatabase db = getWritableDatabase();
        for (int i = 0; i < b.size(); i++) {
            if (b.get(i)){
                db.delete(TABLE_NAME, COLUMN_BOOK_ID + "=?", new String[]{books.get(i).getId()});
            }
        }
        db.close();
    }
    public void updateDataByTitle(String title, String newTime, String newLookPo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, newTime);
        values.put(COLUMN_LOOK_PO, newLookPo);

        String whereClause = COLUMN_TITLE + " = ?";


        String[] whereArgs = {title};

        db.update(TABLE_NAME, values, whereClause, whereArgs);

        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_TITLE + " VARCHAR, " +
                COLUMN_ICON + " VARCHAR, " +
                COLUMN_TIME + " VARCHAR, " +
                COLUMN_BOOK_ID + " VARCHAR, " +
                COLUMN_LOOK_ID + " VARCHAR, " +
                COLUMN_LOOK_PO + " VARCHAR, " +
                COLUMN_PROGRESS + " INTEGER)"; // Add the new column to the table definition
        db.execSQL(sql);
    }


         public List<Book> getData() {
        List<Book> dataList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // 查询数据库表中的所有数据
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        // 遍历查询结果并将数据添加到 dataList 列表中
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String icon = cursor.getString(cursor.getColumnIndex(COLUMN_ICON));
            String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
            String bookId = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID));
            String lookId = cursor.getString(cursor.getColumnIndex(COLUMN_LOOK_ID));
            String lookPo = cursor.getString(cursor.getColumnIndex(COLUMN_LOOK_PO));

            Book lookData = new Book(bookId,title,Long.parseLong(time),icon);
            dataList.add(lookData);
        }

        // 关闭游标和数据库连接
        cursor.close();
        db.close();

        return dataList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement the necessary upgrade logic here
    }

    public String insertRecord(String title, String icon, String time, String bookId, String lookId, String po, int progress) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if bookId already exists in the database
        String existingLookId = getLookIdByBookId(bookId);
        if (existingLookId != null) {
            db.close();
            return existingLookId;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_ICON, icon);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_BOOK_ID, bookId);
        values.put(COLUMN_LOOK_ID, lookId);
        values.put(COLUMN_LOOK_PO, po);
        values.put(COLUMN_PROGRESS, progress); // Add the progress value

        db.insert(TABLE_NAME, null, values);
        db.close();

        return lookId;
    }

    public String getLookIdByBookId(String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_LOOK_ID};
        String selection = COLUMN_BOOK_ID + " = ?";
        String[] selectionArgs = {bookId};

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String lookId = cursor.getString(cursor.getColumnIndex(COLUMN_LOOK_ID));
            cursor.close();
            return lookId;
        } else {
            cursor.close();
            return null;
        }
    }

    public String getLookIdByBookPo(String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_LOOK_PO};
        String selection = COLUMN_BOOK_ID + " = ?";
        String[] selectionArgs = {bookId};

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String lookId = cursor.getString(cursor.getColumnIndex(COLUMN_LOOK_PO));
            cursor.close();
            return lookId;
        } else {
            cursor.close();
            return null;
        }
    }


    // DELETE operation - Delete a record
    public void deleteRecord(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_TITLE + " = ?", new String[]{title});
        db.close();
    }

    public int getProgressByBookId(String bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_PROGRESS};
        String selection = COLUMN_BOOK_ID + " = ?";
        String[] selectionArgs = {bookId};

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            int progress = cursor.getInt(cursor.getColumnIndex(COLUMN_PROGRESS));
            cursor.close();
            return progress;
        } else {
            cursor.close();
            return -1; // Or any other default value to indicate no progress found
        }
    }

    public void updateProgressByBookId(String bookId, int newProgress) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PROGRESS, newProgress);

        String whereClause = COLUMN_BOOK_ID + " = ?";
        String[] whereArgs = {bookId};

        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

}
