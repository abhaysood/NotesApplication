package com.abhay23.notes.model;

import android.util.Log;
import com.jakewharton.rxrelay.PublishRelay;
import java.util.List;
import rx.Observable;

public class NotesManager {

  public static String TAG = "NotesManager";
  private final PublishRelay<Note> onNoteChangedRelay = PublishRelay.create();

  private final LocalDataStore localDataStore;

  public NotesManager(LocalDataStore localDataStore) {
    this.localDataStore = localDataStore;
    subscribeToLocalStoreNoteChanges();
  }

  public Observable<List<Note>> loadNotes() {
    return Observable.fromCallable(localDataStore::getNotes);
  }

  public Observable<Note> loadNote(long noteId) {
    return Observable.fromCallable(() -> localDataStore.getNote(noteId));
  }

  public Observable<Note> subscribeToNoteChanges() {
    return onNoteChangedRelay;
  }

  private void subscribeToLocalStoreNoteChanges() {
    localDataStore.subscribeToNoteChanges().subscribe(note -> {
      onNoteChangedRelay.call(note);
      Log.d(TAG, "subscribeToLocalStoreNoteChanges: onNoteChanged");
    });
  }

  public void saveNote(Note note) {
    localDataStore.saveNote(note);
  }

  public void updateNote(Note note) {
    localDataStore.updateNote(note);
  }

  public void deleteNote(long noteId) {
    localDataStore.deleteNote(noteId);
  }
}
