package com.example.welcome.smartnotepad.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Welcome on 18-Jun-17.
 */

public class NotepadHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "smartNotepad";
    private static final int DATABASE_VERSION = 1;

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_TS = "timestamp";

    public static final String TABLE_NOTEPAD = "notepad";
    private static final String CREATE_NOTEPAD_TABLE = " CREATE TABLE " + TABLE_NOTEPAD +
            "( " + COL_ID + " INTEGER PRIMARY KEY," + COL_TITLE + " TEXT, "
            + COL_DESCRIPTION + " TEXT, " + COL_TS + " TEXT )";


    public NotepadHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NOTEPAD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
