package com.abhay23.notes.edit_note;

import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;

public class EditNotePresenter extends BasePresenter {

  private final NotesManager notesManager;
  private final RxUtils rxUtils;

  public EditNotePresenter(NotesManager notesManager, RxUtils rxUtils) {
    this.notesManager = notesManager;
    this.rxUtils = rxUtils;
  }

  @Override public void onViewCreated(boolean isNewLaunch) {

  }
}
