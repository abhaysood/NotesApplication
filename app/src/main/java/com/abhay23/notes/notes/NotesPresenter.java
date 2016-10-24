package com.abhay23.notes.notes;

import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.exceptions.NoNotesAvailableException;
import com.abhay23.notes.model.NotesManager;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

final class NotesPresenter extends BasePresenter {

  private final NotesView view;
  private final NotesManager notesManager;

  NotesPresenter(NotesView view, NotesManager notesManager) {
    this.view = view;
    this.notesManager = notesManager;
  }

  public void onViewReady() {
    Subscription subscription = notesManager.loadNotes()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(view::showNotes, this::onErrorLoadingNotes);
    addSubscription(subscription);
  }

  private void onErrorLoadingNotes(Throwable throwable) {
    if (throwable.getCause() instanceof NoNotesAvailableException) {
      view.showNoNotesError();
      return;
    }

    throw new IllegalStateException("Unable to load notes " + throwable.toString());
  }

  public void onAddNewNoteClick() {
    view.openAddNewNoteScreen();
  }
}
