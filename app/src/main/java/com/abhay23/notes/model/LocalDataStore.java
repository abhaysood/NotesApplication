package com.abhay23.notes.model;

import android.support.annotation.Nullable;
import com.jakewharton.rxrelay.PublishRelay;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rx.Observable;

public class LocalDataStore {

  private ArrayList<Note> notes;

  public LocalDataStore() {
    notes = new ArrayList<>();
    notes.add(new Note(0, "Ok Google", "Please take a note.", null, new Date()));
  }

  private final PublishRelay<Note> onNoteChangedRelay = PublishRelay.create();

  @Nullable public List<Note> getNotes() {
    return notes;
  }

  public void saveNote(Note note) {
    notes.add(note);
    onNoteChangedRelay.call(note);
  }

  @Nullable public Note getNote(long noteId) {
    for (Note note : notes) {
      if (note.getId() == noteId) {
        return note;
      }
    }
    return null;
  }

  public void updateNote(Note note) {
    for (Note note1 : notes) {
      if (note.getId() == note1.getId()) {
        notes.remove(notes.indexOf(note1));
        notes.add(note);
        onNoteChangedRelay.call(note);
        return;
      }
    }

    throw new IllegalStateException("Could not update, note does not exist " + note.toString());
  }

  public void deleteNote(long noteId) {
    for (Note note : notes) {
      if (note.getId() == noteId) {
        notes.remove(notes.indexOf(note));
        onNoteChangedRelay.call(note);
        return;
      }
    }

    throw new IllegalStateException("Could not delete, note does not exist " + noteId);
  }

  public Observable<Note> subscribeToNoteChanges() {
    return onNoteChangedRelay;
  }
}
