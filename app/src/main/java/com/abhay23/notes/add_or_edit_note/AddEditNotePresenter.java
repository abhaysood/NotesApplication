package com.abhay23.notes.add_or_edit_note;

import android.os.Bundle;
import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.R;
import com.abhay23.notes.model.Note;
import com.abhay23.notes.model.NotesManager;
import java.util.Date;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class AddEditNotePresenter extends BasePresenter {

  private static final String SAVE_PICTURE_PATH = "save_picture_path";
  private final AddEditNoteView view;
  private final NotesManager notesManager;
  private String picturePath;

  public AddEditNotePresenter(AddEditNoteView view, NotesManager notesManager) {
    this.view = view;
    this.notesManager = notesManager;
  }

  public void onViewCreated() {
    view.initView();
    if (view.getNoteId() == -1) {
      view.setScreenTitle(R.string.add_note);
      view.showImage(null);
    } else {
      view.setScreenTitle(R.string.edit_note);
    }
  }

  public void onViewReady() {
    Subscription subscription = notesManager.loadNote(view.getNoteId())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onNoteLoaded, this::handleNoteLoadingError);
    addSubscription(subscription);
  }

  private void handleNoteLoadingError(Throwable throwable) {
    view.showErrorLoadingNote();
  }

  private void onNoteLoaded(Note note) {
    view.showNote(note);
    String imagePath = note.getImagePath();
    view.showImage(imagePath);
    setPicturePath(imagePath);
  }

  public void onSaveButtonClicked(long noteId, String title, String description) {
    if (noteId != -1) {
      Note note = new Note(noteId, title, description, getPicturePath(), new Date());
      notesManager.updateNote(note);
    } else {
      Note note =
          new Note(System.currentTimeMillis(), title, description, getPicturePath(), new Date());
      notesManager.createNote(note);
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
    savedInstanceState.getString(SAVE_PICTURE_PATH, getPicturePath());
    view.showImage(getPicturePath());
  }

  public void saveState(Bundle outState) {
    setPicturePath(outState.getString(SAVE_PICTURE_PATH, null));
  }
}
