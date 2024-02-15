package com.example.perpustakaan.Handler;

import android.content.ContentValues;
import android.content.Context;

import com.example.perpustakaan.View.Database;
import com.example.perpustakaan.View.MainActivity;

public class UserHandler extends MainActivity {

    Database database;

    public UserHandler(Context context) {
        database = new Database(context);
    }

    public void openWrite() {
        sqLiteDatabase = database.getWritableDatabase();
    }

    public void openRead() {
        sqLiteDatabase = database.getReadableDatabase();
    }

    public long insertUser(String username, String password) {
        openWrite();
        contentValues = new ContentValues();
        contentValues.put(database.col_username, username);
        contentValues.put(database.col_password, password);
        return sqLiteDatabase.insert(database.table_user, null, contentValues);
    }

    public long updateUser(int id_user, String username, String password) {
        contentValues = new ContentValues();
        String whereClause = "id_user = ?";
        String[] whereArgs = {String.valueOf(id_user)};
        contentValues.put(database.col_username, username);
        contentValues.put(database.col_password, password);
        return sqLiteDatabase.update(database.table_user, contentValues, whereClause, whereArgs);
    }

    public long readUser(String username, String password) {
        openRead();
        id_data = -1;
        query = "select * from user where username = '" + username + "' and password = '" + password + "'";
        cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()){
            id_data = cursor.getInt(0);
        }
        return id_data;
    }
}
