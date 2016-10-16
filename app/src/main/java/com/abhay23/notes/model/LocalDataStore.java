package com.abhay23.notes.model;

import android.support.annotation.Nullable;
import android.util.Log;
import com.jakewharton.rxrelay.PublishRelay;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;

public class LocalDataStore {

  public static String TAG = "LocalDataStore";
  private ArrayList<Note> notes;

  public LocalDataStore() {
    notes = new ArrayList<>();
  }

  private final PublishRelay<Note> onNoteChangedRelay = PublishRelay.create();

  @Nullable public List<Note> getNotes() {
    return notes;
  }

  public void saveNote(Note note) {
    Log.d(TAG, "saveNote");
    notes.add(note);
    onNoteChangedRelay.call(note);
  }

  public Observable<Note> subscribeToNoteChanges() {
    return onNoteChangedRelay;
  }
}
