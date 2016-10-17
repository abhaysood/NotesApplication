package com.abhay23.notes.add_or_edit_note;

import android.os.Bundle;
import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.model.Note;
import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;
import java.util.Date;
import rx.Subscription;

public class EditNotePresenter extends BasePresenter {

  private static final String SAVE_PICTURE_PATH = "save_picture_path";
  private final EditNoteView view;
  private final NotesManager notesManager;
  private final RxUtils rxUtils;
  private String picturePath;

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
    String imagePath = note.getImagePath();
    if (imagePath != null) {
      view.showImage(imagePath);
      setPicturePath(imagePath);
    }
  }

  public void onSaveButtonClicked(long noteId, String title, String description) {
    if (noteId != -1) {
      Note note = new Note(noteId, title, description, getPicturePath(), new Date());
      notesManager.updateNote(note);
    } else {
      Note note =
          new Note(System.currentTimeMillis(), title, description, getPicturePath(), new Date());
      notesManager.saveNote(note);
    }
    view.killScreen();
  }

  public void onDeleteNoteClicked() {
    notesManager.deleteNote(view.getNoteId());
    view.killScreen();
  }

  public void onImageClicked() {
    view.openGallery();
  }

  public void saveImagePath(String picturePath) {
    setPicturePath(picturePath);
  }

  public void setPicturePath(String picturePath) {
    this.picturePath = picturePath;
  }

  public String getPicturePath() {
    return picturePath;
  }

  public void restoreState(Bundle savedInstanceState) {
    if (getPicturePath() != null) {
      savedInstanceState.getString(SAVE_PICTURE_PATH, getPicturePath());
      view.showImage(getPicturePath());
    }
  }

  public void saveState(Bundle outState) {
    setPicturePath(outState.getString(SAVE_PICTURE_PATH, null));
  }
}
