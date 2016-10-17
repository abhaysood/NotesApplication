package com.abhay23.notes.edit_note;

import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.model.Note;
import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;
import java.util.Date;
import rx.Subscription;

public class EditNotePresenter extends BasePresenter {

  private final EditNoteView view;
  private final NotesManager notesManager;
  private final RxUtils rxUtils;

  public EditNotePresenter(EditNoteView view, NotesManager notesManager, RxUtils rxUtils) {
    this.view = view;
    this.notesManager = notesManager;
    this.rxUtils = rxUtils;
  }

  @Override public void onViewCreated(boolean isNewLaunch) {
    view.initView();

    if (view.getNoteId() == -1) {
      view.setScreenTitle("Add Note");
      return;
    }

    view.setScreenTitle("Edit Note");
    if (isNewLaunch) {
      Subscription subscription = notesManager.loadNote(view.getNoteId())
          .compose(rxUtils.applySchedulers())
          .subscribe(this::onNoteLoaded, this::handleNoteLoadingError);
      addSubscription(subscription);
    }
  }

  private void handleNoteLoadingError(Throwable throwable) {
    view.showErrorLoadingNote();
  }

  private void onNoteLoaded(Note note) {
    view.showNote(note);
  }

  public void onSaveButtonClicked(long noteId, String title, String description, String imagePath) {
    if (noteId != -1) {
      Note note = new Note(noteId, title, description, imagePath, new Date());
      notesManager.updateNote(note);

    } else {
      Note note = new Note(System.currentTimeMillis(), title, description, imagePath, new Date());
      notesManager.saveNote(note);
    }
    view.killScreen();
  }

  public void onDeleteNoteClicked() {
    notesManager.deleteNote(view.getNoteId());
    view.killScreen();
  }
}
