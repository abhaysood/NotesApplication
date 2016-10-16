package com.abhay23.notes.notes;

import android.support.annotation.NonNull;
import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.model.Note;
import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;
import java.util.List;
import rx.Observable;
import rx.Subscription;

class NotesPresenter extends BasePresenter {

  private final NotesView view;
  private final NotesManager notesManager;
  private final RxUtils rxUtils;

  NotesPresenter(NotesView view, NotesManager notesManager, RxUtils rxUtils) {
    this.view = view;
    this.notesManager = notesManager;
    this.rxUtils = rxUtils;
  }

  @Override public void onViewCreated(boolean isNewLaunch) {
    view.initView();

    if (isNewLaunch) {
      Subscription subscription = notesManager.getNotes()
          .flatMap(Observable::from)
          .toSortedList(this::compareCreationDate)
          .compose(rxUtils.applySchedulers())
          .subscribe(this::onNotesLoaded, this::onErrorLoadingNotes);

      addSubscription(subscription);
    }
  }

  private void onNotesLoaded(@NonNull List<Note> notes) {
    view.showNotes(notes);
  }

  private void onErrorLoadingNotes(Throwable throwable) {

  }

  private int compareCreationDate(Note note, Note note2) {
    return note.getCreatedAt().compareTo(note2.getCreatedAt());
  }
}
