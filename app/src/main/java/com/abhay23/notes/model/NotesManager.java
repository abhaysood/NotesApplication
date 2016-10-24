package com.abhay23.notes.model;

import com.abhay23.notes.db.LocalDataStore;
import com.abhay23.notes.exceptions.NoNotesAvailableException;
import java.util.List;
import rx.Observable;
import rx.exceptions.Exceptions;

public final class NotesManager {

  private final LocalDataStore localDataStore;

  public NotesManager(LocalDataStore localDataStore) {
    this.localDataStore = localDataStore;
  }

  public Observable<List<Note>> loadNotes() {
    return localDataStore.getNotes().map(this::verifyNotesExistOrThrow);
  }

  public Observable<Note> loadNote(long noteId) {
    return localDataStore.getNote(noteId);
  }

  public void createNote(Note note) {
    localDataStore.createNote(note);
  }

  public void updateNote(Note note) {
    localDataStore.updateNote(note);
  }

  public void deleteNote(long noteId) {
    localDataStore.deleteNote(noteId);
  }

  private List<Note> verifyNotesExistOrThrow(List<Note> notes) {
    if (notes == null || notes.isEmpty()) {
      throw Exceptions.propagate(new NoNotesAvailableException());
    }
    return notes;
  }
}
