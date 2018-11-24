package com.zzg.zcib.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatSQLiteHelper extends SQLiteOpenHelper {
    public ChatSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE chat (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "uid integer," +
                "fid integer," +
                "msgtype varchar(255)," +
                "msgcontent varchar(255)," +
                "time varchar(255)," +
                "fname varchar(255)," +
                "imgurl varchar(255)," +
                "fimg varchar(255));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
