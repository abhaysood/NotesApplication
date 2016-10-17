package com.abhay23.notes.add_or_edit_note;

import com.abhay23.notes.model.Note;

public interface EditNoteView {
  void initView();

  long getNoteId();

  void showNote(Note note);

  void showImage(String path);

  void setScreenTitle(String title);

  void killScreen();

  void showErrorLoadingNote();

  void openGallery();
}
