package com.abhay23.notes.add_or_edit_note;

import android.support.annotation.StringRes;
import com.abhay23.notes.model.Note;

public interface AddEditNoteView {
  void initView();

  long getNoteId();

  void showNote(Note note);

  void showImage(String path);

  void setScreenTitle(@StringRes int title);

  void killScreen();

  void showErrorLoadingNote();

  void openGallery();
}
