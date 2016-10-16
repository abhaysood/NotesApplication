package com.abhay23.notes.model;

import com.abhay23.notes.exceptions.NoNotesAvailableException;
import java.util.List;
import rx.Observable;
import rx.exceptions.Exceptions;

public class NotesManager {

  private final Database database;

  public NotesManager(Database database) {
    this.database = database;
  }

  public Observable<List<Note>> getNotes() {
    return Observable.fromCallable(database::getNotes).map(this::verifyNotesExistOrThrow);
  }

  private List<Note> verifyNotesExistOrThrow(List<Note> notes) {
    if (notes == null || notes.isEmpty()) {
      throw Exceptions.propagate(new NoNotesAvailableException());
    }
    return notes;
  }
}
