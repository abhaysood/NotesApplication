package com.abhay23.notes.db;

import com.abhay23.notes.model.Note;
import com.squareup.sqlbrite.BriteDatabase;
import java.util.List;
import rx.Observable;

public class LocalDataStore {

  private BriteDatabase db;

  public LocalDataStore(BriteDatabase db) {
    this.db = db;
  }

  public Observable<List<Note>> getNotes() {
    return db.createQuery(DbOpenHelper.TABLE, DbOpenHelper.GET_ALL_NOTES_QUERY)
        .mapToList(Note.MAPPER);
  }

  public void createNote(Note note) throws IllegalStateException {
    if (db.insert(DbOpenHelper.TABLE, new Note.Builder(note).build()) > 0) {
      return;
    }

    throw new IllegalStateException("Could not create note: " + note.toString());
  }

  public Observable<Note> getNote(long noteId) {
    return db.createQuery(DbOpenHelper.TABLE, DbOpenHelper.GET_NOTE_BY_ID_QUERY,
        String.valueOf(noteId)).mapToOne(Note.MAPPER);
  }

  public void updateNote(Note note) {
    db.update(DbOpenHelper.TABLE, new Note.Builder(note).build(), Note.ID + "=" + note.getId());
  }

  public void deleteNote(long noteId) {
    db.delete(DbOpenHelper.TABLE, Note.ID + "=" + noteId);
  }
}