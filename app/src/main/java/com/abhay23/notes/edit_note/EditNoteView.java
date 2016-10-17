package com.abhay23.notes.edit_note;

import com.abhay23.notes.model.Note;

public interface EditNoteView {
  void initView();

  long getNoteId();

  void showNote(Note note);

  void setScreenTitle(String title);

  void killScreen();

  void showErrorLoadingNote();

}
