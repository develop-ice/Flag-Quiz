package com.android.flagquiz.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FlagsDatabase extends SQLiteOpenHelper {

    // DB
    private static final String DB_NAME = "flagquizgame.db";

    public FlagsDatabase(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `flagquizgametable` (\n" +
                "\t`flag_id`\tINTEGER,\n" +
                "\t`flag_name`\tTEXT,\n" +
                "\t`flag_image`\tTEXT\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);
    }
}
