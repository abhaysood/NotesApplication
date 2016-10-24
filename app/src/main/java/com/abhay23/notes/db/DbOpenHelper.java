package com.abhay23.notes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.abhay23.notes.model.Note;

import static com.abhay23.notes.model.Note.CREATED_AT;
import static com.abhay23.notes.model.Note.ID;
import static com.abhay23.notes.model.Note.IMAGE_PATH;
import static com.abhay23.notes.model.Note.NOTE;
import static com.abhay23.notes.model.Note.TITLE;

public final class DbOpenHelper extends SQLiteOpenHelper {
  private static final int VERSION = 1;

  static final String DB_NAME = "todo.db";
  static final String TABLE = "notes_table";

  private static final String CREATE_NOTES_TABLE = ""
      + "CREATE TABLE "
      + TABLE
      + "("
      + ID
      + " INTEGER NOT NULL PRIMARY KEY,"
      + TITLE
      + " TEXT NOT NULL,"
      + NOTE
      + " TEXT NOT NULL,"
      + IMAGE_PATH
      + " TEXT,"
      + CREATED_AT
      + " INTEGER NOT NULL"
      + ")";

  static final String GET_ALL_NOTES_QUERY =
      "SELECT * FROM " + TABLE + " ORDER BY " + CREATED_AT + " DESC";

  static final String GET_NOTE_BY_ID_QUERY =
      "SELECT * FROM " + TABLE + " WHERE " + Note.ID + "=?";

  DbOpenHelper(Context context) {
    super(context, DB_NAME, null /* factory */, VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_NOTES_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
