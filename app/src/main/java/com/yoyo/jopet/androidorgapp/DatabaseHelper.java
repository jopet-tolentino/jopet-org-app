package com.yoyo.jopet.androidorgapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jopet on 5/30/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "yoyoDatabase";

    // Table Names
    private static final String TABLE_ENTRY = "entries";

    // TODOS Table - column names
    private static final String KEY_ENTRY_ID = "entry_id";
    private static final String KEY_ENTRY_ACTIVITY = "entry_activity";
    private static final String KEY_ENTRY_STATUS = "entry_status";
    private static final String KEY_ENTRY_TITLE = "entry_title";
    private static final String KEY_ENTRY_CONTENT = "entry_content";
    private static final String KEY_ENTRY_TYPE = "entry_type";

    // Table Create Statement
    // Entry table create statement
    private static final String CREATE_TABLE_ENTRY = "CREATE TABLE " + TABLE_ENTRY
            + "(" + KEY_ENTRY_ID + " INTEGER PRIMARY KEY," + KEY_ENTRY_ACTIVITY + " TEXT,"
            + KEY_ENTRY_STATUS + " INTEGER," + KEY_ENTRY_TITLE + " TEXT,"
            + KEY_ENTRY_CONTENT + " TEXT," + KEY_ENTRY_TYPE + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRY);

        // create new tables
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /*
    * Creating an entry
    */
    public long createEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ENTRY_TITLE, entry.getTitle());
        values.put(KEY_ENTRY_CONTENT, entry.getContent());
        values.put(KEY_ENTRY_ACTIVITY, entry.getActivity());
        values.put(KEY_ENTRY_STATUS, entry.getStatus());
        values.put(KEY_ENTRY_TYPE, entry.getType());

        // insert row
        long entry_id = db.insert(TABLE_ENTRY, null, values);

        return entry_id;
    }

    /*
    * get single entry
    */
    public Entry getEntry(long entry_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ENTRY + " WHERE "
                + KEY_ENTRY_ID + " = " + entry_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Entry en = new Entry();
        en.setId(c.getInt(c.getColumnIndex(KEY_ENTRY_ID)));
        en.setTitle((c.getString(c.getColumnIndex(KEY_ENTRY_TITLE))));
        en.setContent(c.getString(c.getColumnIndex(KEY_ENTRY_CONTENT)));
        en.setActivity(c.getString(c.getColumnIndex(KEY_ENTRY_ACTIVITY)));
        en.setStatus(c.getInt(c.getColumnIndex(KEY_ENTRY_STATUS)));
        en.setType(c.getInt(c.getColumnIndex(KEY_ENTRY_TYPE)));

        return en;
    }

    /*
    * getting all entries
    */
    public List<Entry> getAllEntries() {
        List<Entry> entries = new ArrayList<Entry>();
        String selectQuery = "SELECT  * FROM " + TABLE_ENTRY + " ORDER BY " + KEY_ENTRY_ID + " DESC";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Entry en = new Entry();
                en.setId(c.getInt(c.getColumnIndex(KEY_ENTRY_ID)));
                en.setTitle((c.getString(c.getColumnIndex(KEY_ENTRY_TITLE))));
                en.setContent(c.getString(c.getColumnIndex(KEY_ENTRY_CONTENT)));
                en.setActivity(c.getString(c.getColumnIndex(KEY_ENTRY_ACTIVITY)));
                en.setStatus(c.getInt(c.getColumnIndex(KEY_ENTRY_STATUS)));
                en.setType(c.getInt(c.getColumnIndex(KEY_ENTRY_TYPE)));
                // adding to note list
                entries.add(en);
            } while (c.moveToNext());
        }

        return entries;
    }

    public List<Entry> getNoteEntries() {
        List<Entry> entries = new ArrayList<Entry>();
        String selectQuery = "SELECT  * FROM " + TABLE_ENTRY + " WHERE " + KEY_ENTRY_TYPE + " = " + 0 + " ORDER BY " + KEY_ENTRY_ID + " DESC";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Entry en = new Entry();
                en.setId(c.getInt(c.getColumnIndex(KEY_ENTRY_ID)));
                en.setTitle((c.getString(c.getColumnIndex(KEY_ENTRY_TITLE))));
                en.setContent(c.getString(c.getColumnIndex(KEY_ENTRY_CONTENT)));
                en.setActivity(c.getString(c.getColumnIndex(KEY_ENTRY_ACTIVITY)));
                en.setStatus(c.getInt(c.getColumnIndex(KEY_ENTRY_STATUS)));
                en.setType(c.getInt(c.getColumnIndex(KEY_ENTRY_TYPE)));
                // adding to note list
                entries.add(en);
            } while (c.moveToNext());
        }

        return entries;
    }

    public List<Entry> getTodoEntries() {
        List<Entry> entries = new ArrayList<Entry>();
        String selectQuery = "SELECT  * FROM " + TABLE_ENTRY + " WHERE " + KEY_ENTRY_TYPE + " = " + 1;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Entry en = new Entry();
                en.setId(c.getInt(c.getColumnIndex(KEY_ENTRY_ID)));
                en.setTitle((c.getString(c.getColumnIndex(KEY_ENTRY_TITLE))));
                en.setContent(c.getString(c.getColumnIndex(KEY_ENTRY_CONTENT)));
                en.setActivity(c.getString(c.getColumnIndex(KEY_ENTRY_ACTIVITY)));
                en.setStatus(c.getInt(c.getColumnIndex(KEY_ENTRY_STATUS)));
                en.setType(c.getInt(c.getColumnIndex(KEY_ENTRY_TYPE)));
                // adding to note list
                entries.add(en);
            } while (c.moveToNext());
        }

        return entries;
    }

    /*
    * getting all entries
    */
    public Cursor getEntries() {
        List<Entry> notes = new ArrayList<Entry>();
        String selectQuery = "SELECT  * FROM " + TABLE_ENTRY;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /*
    * Updating an entry
    */
    public int updateEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ENTRY_TITLE, entry.getTitle());
        values.put(KEY_ENTRY_CONTENT, entry.getContent());
        values.put(KEY_ENTRY_ACTIVITY, entry.getActivity());
        values.put(KEY_ENTRY_STATUS, entry.getStatus());
        values.put(KEY_ENTRY_TYPE, entry.getType());

        // updating row
        return db.update(TABLE_ENTRY, values, KEY_ENTRY_ID + " = ?",
                new String[]{String.valueOf(entry.getId())});
    }

    /*
    * Deleting an entry
    */
    public void deleteEntry(long note_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ENTRY, KEY_ENTRY_ID + " = ?",
                new String[]{String.valueOf(note_id)});
    }
}
