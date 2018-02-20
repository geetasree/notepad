package com.example.welcome.smartnotepad.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.welcome.smartnotepad.DBHelper.NotepadHelper;
import com.example.welcome.smartnotepad.DBModel.NotepadModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Welcome on 18-Jun-17.
 */

public class NotepadManager {
    private NotepadHelper helper;
    private SQLiteDatabase database;
    private Context context;

    public NotepadManager(Context context) {
        this.context = context;
        helper=new NotepadHelper(context);
    }

    public void openDatabase() {
        database = helper.getWritableDatabase();
    }

    private void close() {
        database.close();
    }

    public ArrayList<NotepadModel> getAllNoteTitle() {
        openDatabase();
        ArrayList<NotepadModel> NoteData = new ArrayList<NotepadModel>();
       // Cursor cursor = database.query(NotepadHelper.TABLE_NOTEPAD, null, null, null, null, null, null);
        Cursor cursor =  database.rawQuery("SELECT * FROM notepad order by id desc", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(NotepadHelper.COL_ID));
                String noteTitle = cursor.getString(cursor.getColumnIndex(NotepadHelper.COL_TITLE));
                String noteDescription = cursor.getString(cursor.getColumnIndex(NotepadHelper.COL_DESCRIPTION));
                String noteTs = cursor.getString(cursor.getColumnIndex(NotepadHelper.COL_TS));
                noteTs= noteTs.substring(0, 12);
                NotepadModel noteInfo = new NotepadModel(id, noteTitle, noteDescription, noteTs);
                NoteData.add(noteInfo);
                cursor.moveToNext();
            }
            this.close();
        }
        return NoteData;
    }

    public NotepadModel getSpecificNote(int id) {
        openDatabase();

        NotepadModel noteInfo=null;
        Cursor cursor = database.rawQuery("SELECT * FROM notepad where id="+id+"", null);

        if (cursor != null && cursor.getCount()== 1) {
            cursor.moveToFirst();
            int noteId = cursor.getInt(cursor.getColumnIndex(NotepadHelper.COL_ID));
            String noteTitle = cursor.getString(cursor.getColumnIndex(NotepadHelper.COL_TITLE));
            String noteDescription = cursor.getString(cursor.getColumnIndex(NotepadHelper.COL_DESCRIPTION));
            String noteTs = cursor.getString(cursor.getColumnIndex(NotepadHelper.COL_TS));
            noteInfo = new NotepadModel(noteId, noteTitle, noteDescription, noteTs);
            Log.d("*** Title",noteTitle);
            this.close();
        }
        return noteInfo;
    }

    public boolean insertData(NotepadModel mNotepadModel) {
        openDatabase();

        ContentValues cv = new ContentValues();
        cv.put(NotepadHelper.COL_TITLE, mNotepadModel.getTitle());
        cv.put(NotepadHelper.COL_DESCRIPTION, mNotepadModel.getDescription());
        cv.put(NotepadHelper.COL_TS, DateFormat.getDateTimeInstance().format(new Date()));

        long inserted = database.insert(NotepadHelper.TABLE_NOTEPAD, null, cv);
        close();
        database.close();

        if (inserted > 0) {
            return true;
        } else return false;
    }

    public boolean updateData(NotepadModel mNotepadModel) {
        openDatabase();

        ContentValues cv = new ContentValues();
        cv.put(NotepadHelper.COL_TITLE, mNotepadModel.getTitle());
        cv.put(NotepadHelper.COL_DESCRIPTION, mNotepadModel.getDescription());
        cv.put(NotepadHelper.COL_TS, mNotepadModel.getTs());
        String where = "id=?";
        String[] whereArgs = new String[] {String.valueOf(mNotepadModel.getId())};

        long inserted = database.update(NotepadHelper.TABLE_NOTEPAD, cv, where ,whereArgs);
        close();
        database.close();

        if (inserted > 0) {
            return true;
        } else return false;
    }

    public boolean deleteData(int id) {
        openDatabase();
        return database.delete(NotepadHelper.TABLE_NOTEPAD, NotepadHelper.COL_ID + "=" + id, null) > 0;
    }
}
