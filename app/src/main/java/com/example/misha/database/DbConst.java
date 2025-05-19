package com.example.misha.database;

public class DbConst {
    public static final String DATABASE_NAME = "TaktApp.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TASK_TABLE_NAME = "task";
    public static final String TASK_ID = "id";
    public static final String TASK_TITLE = "title";
    public static final String TASK_DESCRIPTION = "description";
    public static final String TASK_START_DATE = "startDate";
    public static final String TASK_DUE_DATE = "dueDate";
    public static final String TASK_PRIORITY = "priority";
    public static final String TASK_COMPLETE = "completed";

    public static final String CREATE_TESTS_TABLE = "CREATE TABLE " + TASK_TABLE_NAME + " (" +
            TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TASK_TITLE+ " TEXT NOT NULL," +
            TASK_DESCRIPTION +" TEXT," +
            TASK_START_DATE +" INTEGER," +
            TASK_DUE_DATE +" INTEGER," +
            TASK_PRIORITY +" TEXT," +
            TASK_COMPLETE+" INTEGER)";
    public static final String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + TASK_TABLE_NAME;
}
