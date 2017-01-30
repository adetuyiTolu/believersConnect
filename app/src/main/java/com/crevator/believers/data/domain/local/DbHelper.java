package com.crevator.believers.data.domain.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Slimfit on 1/23/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Users.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA = ",";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            PersistenceContract.Entry.TABLE_NAME + " (" +
            PersistenceContract.Entry.COLUMN_ID + INTEGER_TYPE + " PRIMARY KEY," +
            PersistenceContract.Entry.COLUMN_USERNAME + TEXT_TYPE + COMMA +
            PersistenceContract.Entry.COLUMN_FIRST_NAME + TEXT_TYPE + COMMA +
            PersistenceContract.Entry.COLUMN_LAST_NAME + TEXT_TYPE + COMMA +
            PersistenceContract.Entry.COLUMN_PIC_URL + TEXT_TYPE +
            " )";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
