package com.abhay23.notes.model;

import android.os.Handler;
import android.util.Log;
import com.jakewharton.rxrelay.PublishRelay;
import java.util.Date;
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
    new Handler().postDelayed(() -> {
      Note note = new Note(1L, "new note", "new note", "new note", new Date());
      localDataStore.saveNote(note);
    }, 7000);

    return Observable.fromCallable(localDataStore::getNotes);
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
}
