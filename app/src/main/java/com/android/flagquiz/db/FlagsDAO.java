package com.android.flagquiz.db;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class FlagsDAO {

    public ArrayList<FlagsModel> getRandomTenQuestions(FlagsDatabase flagsDatabase) {
        ArrayList<FlagsModel> result = new ArrayList<>();
        SQLiteDatabase liteDatabase = flagsDatabase.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = liteDatabase.rawQuery("SELECT * FROM flagquizgametable ORDER BY RANDOM () LIMIT 10", null);

        int flagIdIndex = cursor.getColumnIndex("flag_id");
        int flagNameIndex = cursor.getColumnIndex("flag_name");
        int flagImageIndex = cursor.getColumnIndex("flag_image");

        while (cursor.moveToFirst()) {
            FlagsModel model = new FlagsModel(
                    cursor.getInt(flagIdIndex),
                    cursor.getString(flagNameIndex),
                    cursor.getString(flagImageIndex)
            );
            result.add(model);
        }
        return result;
    }

    public ArrayList<FlagsModel> getRandomTreeOptions(FlagsDatabase flagsDatabase, int flag_id) {
        ArrayList<FlagsModel> result = new ArrayList<>();
        SQLiteDatabase liteDatabase = flagsDatabase.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = liteDatabase.rawQuery("SELECT * FROM flagquizgametable WHERE flag_id != " + flag_id + " ORDER BY RANDOM () LIMIT 3", null);

        int flagIdIndex = cursor.getColumnIndex("flag_id");
        int flagNameIndex = cursor.getColumnIndex("flag_name");
        int flagImageIndex = cursor.getColumnIndex("flag_image");

        while (cursor.moveToFirst()) {
            FlagsModel model = new FlagsModel(
                    cursor.getInt(flagIdIndex),
                    cursor.getString(flagNameIndex),
                    cursor.getString(flagImageIndex)
            );
            result.add(model);
        }
        return result;
    }
}
