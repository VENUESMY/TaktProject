package com.example.misha.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, DbConst.DATABASE_NAME, null, DbConst.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConst.CREATE_TESTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbConst.DROP_TASK_TABLE);
        onCreate(db);
    }
}