package com.example.misha.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.misha.data.Priority;
import com.example.misha.data.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DbManager {
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    public DbManager(Context context){
        dbHelper = new DbHelper(context);
    }
    public void openDb(){
        db = dbHelper.getWritableDatabase();
    }
    public void closeDb(){
        dbHelper.close();
    }

    public long insertTask(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(DbConst.TASK_TITLE, task.getTitle());
        cv.put(DbConst.TASK_DESCRIPTION, task.getDescription());
        cv.put(DbConst.TASK_START_DATE, task.getStartDate());
        cv.put(DbConst.TASK_DUE_DATE, task.getDueDate());
        cv.put(DbConst.TASK_PRIORITY, task.getPriority().name());
        cv.put(DbConst.TASK_COMPLETE, task.isCompleted() ? 1 : 0);

        return db.insert(DbConst.TASK_TABLE_NAME, null, cv);
    }

    @SuppressLint("Range")
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(DbConst.TASK_TABLE_NAME, null, null, null, null, null, DbConst.TASK_DUE_DATE + " ASC");

        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndex(DbConst.TASK_ID)));
            task.setTitle(cursor.getString(cursor.getColumnIndex(DbConst.TASK_TITLE)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(DbConst.TASK_DESCRIPTION)));
            task.setStartDate(cursor.getLong(cursor.getColumnIndex(DbConst.TASK_START_DATE)));
            task.setDueDate(cursor.getLong(cursor.getColumnIndex(DbConst.TASK_DUE_DATE)));
            task.setPriority(Priority.valueOf(cursor.getString(cursor.getColumnIndex(DbConst.TASK_PRIORITY))));
            task.setCompleted(cursor.getInt(cursor.getColumnIndex(DbConst.TASK_COMPLETE)) == 1);

            tasks.add(task);
        }
        cursor.close();
        return tasks;
    }

    @SuppressLint("Range")
    public List<Task> getTasksForDay(long dayTimestamp) {
        // Рассчитываем начало и конец дня
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dayTimestamp);

        // Устанавливаем время на начало дня (00:00:00)
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startOfDay = calendar.getTimeInMillis();

        // Устанавливаем время на конец дня (23:59:59)
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long endOfDay = calendar.getTimeInMillis();

        // Формируем запрос
        String selection = DbConst.TASK_DUE_DATE + " >= ? AND " + DbConst.TASK_DUE_DATE + " <= ?";
        String[] selectionArgs = {String.valueOf(startOfDay), String.valueOf(endOfDay)};

        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(DbConst.TASK_TABLE_NAME, null, selection, selectionArgs, null, null, DbConst.TASK_DUE_DATE + " ASC");

        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndex(DbConst.TASK_ID)));
            task.setTitle(cursor.getString(cursor.getColumnIndex(DbConst.TASK_TITLE)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(DbConst.TASK_DESCRIPTION)));
            task.setStartDate(cursor.getLong(cursor.getColumnIndex(DbConst.TASK_START_DATE)));
            task.setDueDate(cursor.getLong(cursor.getColumnIndex(DbConst.TASK_DUE_DATE)));
            task.setPriority(Priority.valueOf(cursor.getString(cursor.getColumnIndex(DbConst.TASK_PRIORITY))));
            task.setCompleted(cursor.getInt(cursor.getColumnIndex(DbConst.TASK_COMPLETE)) == 1);

            tasks.add(task);
        }
        cursor.close();
        return tasks;
    }

    public int updateTask(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(DbConst.TASK_TITLE, task.getTitle());
        cv.put(DbConst.TASK_DESCRIPTION, task.getDescription());
        cv.put(DbConst.TASK_START_DATE, task.getStartDate());
        cv.put(DbConst.TASK_DUE_DATE, task.getDueDate());
        cv.put(DbConst.TASK_PRIORITY, task.getPriority().name());
        cv.put(DbConst.TASK_COMPLETE, task.isCompleted() ? 1 : 0);

        String whereClause = DbConst.TASK_ID + " = ?";
        String[] whereArgs = {String.valueOf(task.getId())};

        return db.update(DbConst.TASK_TABLE_NAME, cv, whereClause, whereArgs);
    }

    public int deleteTask(int id) {
        String whereClause = DbConst.TASK_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};

        return db.delete(DbConst.TASK_TABLE_NAME, whereClause, whereArgs);
    }
}