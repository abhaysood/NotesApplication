package com.abhay23.notes.model;

import java.util.List;
import rx.Observable;

public class NotesManager {

  private final Database database;

  public NotesManager(Database database) {
    this.database = database;
  }

  public Observable<List<Note>> getNotes() {
    return Observable.fromCallable(database::getNotes);
  }
}
