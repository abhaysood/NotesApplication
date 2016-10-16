package com.abhay23.notes.notes;

import android.support.annotation.NonNull;
import android.util.Log;
import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.exceptions.NoNotesAvailableException;
import com.abhay23.notes.model.Note;
import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.exceptions.Exceptions;

class NotesPresenter extends BasePresenter {

  private static final String TAG = "NotesPresenter";
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
    loadNotes();
    subscribeToChanges();
  }

  private void subscribeToChanges() {
    Subscription subscription =
        notesManager.subscribeToNoteChanges().compose(rxUtils.applySchedulers()).doOnNext(note -> {
          Log.d(TAG, "subscribeToChanges: note added: " + note.toString());
        }).subscribe(view::onNoteUpdated, Throwable::printStackTrace);
    addSubscription(subscription);
  }

  private void loadNotes() {
    Subscription subscription = notesManager.loadNotes()
        .map(this::verifyNotesExistOrThrow)
        .compose(this::sortNotesByDate)
        .compose(rxUtils.applySchedulers())
        .subscribe(this::onNotesLoaded, this::onErrorLoadingNotes);
    addSubscription(subscription);
  }

  private Observable<List<Note>> sortNotesByDate(Observable<List<Note>> listObservable) {
    return listObservable.flatMap(Observable::from).toSortedList(this::compareNotesByDate);
  }

  private void onNotesLoaded(@NonNull List<Note> notes) {
    for (Note note : notes) {
      Log.d(TAG, note.toString());
    }
    view.showNotes(notes);
  }

  private void onErrorLoadingNotes(Throwable throwable) {
    if (throwable.getCause() instanceof NoNotesAvailableException) {
      view.showNoNotesError();
    }
  }

  private int compareNotesByDate(Note note, Note note2) {
    return note.getCreatedAt().compareTo(note2.getCreatedAt());
  }

  private List<Note> verifyNotesExistOrThrow(List<Note> notes) {
    if (notes == null || notes.isEmpty()) {
      throw Exceptions.propagate(new NoNotesAvailableException());
    }
    return notes;
  }
}
