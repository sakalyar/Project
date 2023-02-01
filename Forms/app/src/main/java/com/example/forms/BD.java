package com.example.forms;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BD extends SQLiteOpenHelper {

    public static final String BIRD_WATCHER = "BIRD_WATCHER";
    public static final String COLUMN_WATCHER_LOGIN = "WATCHER_LOGIN";
    public static final String ID = "WATCHER_ID";
    public static final String WATCHER_PASSWORD = "WATCHER_PASSWORD";
    public static final String BIRDS_WATCHED = "BIRDS_WATCHED";
    List<Form> formsDb;

    public BD(@Nullable Context context) {
        super(context, "watcher.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + BIRD_WATCHER +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_WATCHER_LOGIN + " TEXT, " + WATCHER_PASSWORD + " TEXT, " +
                BIRDS_WATCHED + " TEXT)";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public boolean addOne(WatcherModel watcherModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String name = WatcherModel.getName();
        cv.put(COLUMN_WATCHER_LOGIN, name);
        cv.put(WATCHER_PASSWORD, WatcherModel.getPassword());
        cv.put(BIRDS_WATCHED, WatcherModel.getBirds());

        long insert = db.insert(BIRD_WATCHER, null, cv);
        if (insert == -1) return false;
        return true;
    }
}
