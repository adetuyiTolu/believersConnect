package com.crevator.believers.data.domain.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.crevator.believers.data.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slimfit on 1/23/2017.
 */

public class UserLocalDb {
    private static UserLocalDb INSTANCE;
    private DbHelper mDbHelper;
    String[] projection = {
            PersistenceContract.Entry.COLUMN_ID,
            PersistenceContract.Entry.COLUMN_USERNAME,
            PersistenceContract.Entry.COLUMN_FIRST_NAME,
            PersistenceContract.Entry.COLUMN_LAST_NAME,
            PersistenceContract.Entry.COLUMN_PIC_URL
    };

    private UserLocalDb(Context context) {
        mDbHelper = new DbHelper(context);
    }

    public static UserLocalDb getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserLocalDb(context);
        }
        return INSTANCE;
    }

    public void getUsers() {

        List<User> users = new ArrayList<User>();
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor = database.query(PersistenceContract.Entry.TABLE_NAME,
                projection, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String userName = cursor.getString(cursor.getColumnIndexOrThrow
                        (PersistenceContract.Entry.COLUMN_USERNAME)
                );
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow
                        (PersistenceContract.Entry.COLUMN_FIRST_NAME)
                );
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow
                        (PersistenceContract.Entry.COLUMN_LAST_NAME)
                );
                String pictureUrl = cursor.getString(cursor.getColumnIndexOrThrow
                        (PersistenceContract.Entry.COLUMN_PIC_URL)
                );
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow
                        (PersistenceContract.Entry.COLUMN_ID));

                User user = new User();
                user.setUserId(userId);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPictureUrl(pictureUrl);
                user.setUserName(userName);
                users.add(user);
            }
            if (cursor != null) {
                cursor.close();
            }

            database.close();
        }
    }

    public void getUser(int mUserId) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        String selection = PersistenceContract.Entry.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(mUserId)};

        Cursor cursor = database.query(PersistenceContract.Entry.TABLE_NAME, projection,
                selection, selectionArgs, null, null, null);
        User user = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String userName = cursor.getString(cursor.getColumnIndexOrThrow
                    (PersistenceContract.Entry.COLUMN_USERNAME)
            );
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow
                    (PersistenceContract.Entry.COLUMN_FIRST_NAME)
            );
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow
                    (PersistenceContract.Entry.COLUMN_LAST_NAME)
            );
            String pictureUrl = cursor.getString(cursor.getColumnIndexOrThrow
                    (PersistenceContract.Entry.COLUMN_PIC_URL)
            );
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow
                    (PersistenceContract.Entry.COLUMN_ID));

            user.setUserId(userId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPictureUrl(pictureUrl);
            user.setUserName(userName);

        }
        if (cursor != null) {
            cursor.close();
        }

        database.close();
    }

    public void saveUser(User user) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PersistenceContract.Entry.COLUMN_ID, user.getUserId());
        values.put(PersistenceContract.Entry.COLUMN_USERNAME, user.getUserName());
        values.put(PersistenceContract.Entry.COLUMN_FIRST_NAME, user.getFirstName());
        values.put(PersistenceContract.Entry.COLUMN_LAST_NAME, user.getLastName());
        values.put(PersistenceContract.Entry.COLUMN_PIC_URL, user.getPictureUrl());

        database.insert(PersistenceContract.Entry.TABLE_NAME, null, values);

        database.close();
    }

    public void updateUser(User user) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PersistenceContract.Entry.COLUMN_USERNAME, user.getUserName());
        values.put(PersistenceContract.Entry.COLUMN_FIRST_NAME, user.getFirstName());
        values.put(PersistenceContract.Entry.COLUMN_LAST_NAME, user.getLastName());
        values.put(PersistenceContract.Entry.COLUMN_PIC_URL, user.getPictureUrl());

        String selection = PersistenceContract.Entry.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(user.getUserId())};

        database.update(PersistenceContract.Entry.TABLE_NAME, values, selection, selectionArgs);

        database.close();
    }

}
