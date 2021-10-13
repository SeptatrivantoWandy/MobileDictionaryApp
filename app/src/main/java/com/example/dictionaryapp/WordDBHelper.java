package com.example.dictionaryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.autofill.UserData;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WordDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "LocalDataDB.db";
    public static final Integer DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tblfavoriteword";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_NAME = "wf_name";

    public static final String TABLE_NAME_DEF = "tblfavdefword";
    public static final String TABLE_COLUMN_ID_DEF = "wfd_id";
    public static final String TABLE_COLUMN_ID_WORD = "id";
    public static final String TABLE_COLUMN_IMAGE = "wfd_image";
    public static final String TABLE_COLUMN_TYPE = "wfd_type";
    public static final String TABLE_COLUMN_DEFINITION = "wfd_def";


    List<Word> wordf = new ArrayList<>();

    public WordDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertWordF(Word new_WF) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

//        cv.put(TABLE_COLUMN_ID, new_WF.getWordId());
        cv.put(TABLE_COLUMN_NAME, new_WF.getWord());

        db.insert(TABLE_NAME, null, cv);

    }

    public void insertWordFdef(Definitions new_WFdef) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

//        cv.put(TABLE_COLUMN_ID_DEF, new_WFdef.getDefinitionId());
        cv.put(TABLE_COLUMN_ID_WORD, new_WFdef.getWordid());
        cv.put(TABLE_COLUMN_IMAGE, new_WFdef.getImageUrl());
        cv.put(TABLE_COLUMN_TYPE, new_WFdef.getDefType());
        cv.put(TABLE_COLUMN_DEFINITION, new_WFdef.getDefDefinition());

        db.insert(TABLE_NAME_DEF, null, cv);
    }


    Cursor readAllWordFData () {
        SQLiteDatabase db = this.getReadableDatabase();
        String WF_query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = null;

//        if (db == null){
//            Toast.makeText((Context) cursor, "no data", Toast.LENGTH_SHORT).show();
//        }

        if (db != null) {
            cursor = db.rawQuery(WF_query, null);
        }

        return cursor;
    }

    Cursor readAllWordFDataDef () {
        SQLiteDatabase db = this.getReadableDatabase();
        String WFdef_query = "SELECT * FROM " + TABLE_NAME_DEF;

        Cursor cursor = null;

//        if (db == null){
//            Toast.makeText((Context) cursor, "no data", Toast.LENGTH_SHORT).show();
//        }

        if (db != null) {
            cursor = db.rawQuery(WFdef_query, null);
        }

        return cursor;
    }

    public boolean updateWF (Word update_WF) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TABLE_COLUMN_ID, update_WF.getWordId());
        cv.put(TABLE_COLUMN_NAME, update_WF.getWord());

        db.update(TABLE_NAME, cv, "id = ?", new String [] { update_WF.getWordId() } );

        return true;
    }

    public boolean updateWFdef (Definitions update_WFdef) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(TABLE_COLUMN_ID_DEF, update_WFdef.getDefinitionId());
        cv.put(TABLE_COLUMN_ID_WORD, update_WFdef.getWordid());
        cv.put(TABLE_COLUMN_IMAGE, update_WFdef.getImageUrl());
        cv.put(TABLE_COLUMN_TYPE, update_WFdef.getDefType());
        cv.put(TABLE_COLUMN_DEFINITION, update_WFdef.getDefDefinition());

        db.update(TABLE_NAME_DEF, cv, "id = ?", new String [] { update_WFdef.getDefinitionId() } );

        return true;
    }

    public int deleteWF(int id){
        SQLiteDatabase db =  this.getWritableDatabase();

        return db.delete(
                TABLE_NAME,
                "id=?",
                new String[]{ String.valueOf(id) }
        );
    }

    public int deleteWFdef(int id){
        SQLiteDatabase db =  this.getWritableDatabase();

        return db.delete(
                TABLE_NAME_DEF,
                "id=?",
                new String[]{ String.valueOf(id) }
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table_query = "CREATE TABLE " + TABLE_NAME + " (" + TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_COLUMN_NAME + " TEXT NOT NULL)";
        String create_table_query_def = "CREATE TABLE " + TABLE_NAME_DEF + " (" + TABLE_COLUMN_ID_DEF + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_COLUMN_ID_WORD + " TEXT NOT NULL, " + TABLE_COLUMN_IMAGE + " TEXT NOT NULL, " + TABLE_COLUMN_TYPE + " TEXT NOT NULL, " + TABLE_COLUMN_DEFINITION + " TEXT NOT NULL)";

        db.execSQL(create_table_query);
        db.execSQL(create_table_query_def);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
