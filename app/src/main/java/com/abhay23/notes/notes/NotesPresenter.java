package com.abhay23.notes.notes;

import com.abhay23.notes.BasePresenter;

class NotesPresenter extends BasePresenter implements NotesView {

  private final NotesView view;

  NotesPresenter(NotesView view) {
    this.view = view;
  }

  @Override public void onViewCreated(boolean isNewLaunch) {

  }
}
