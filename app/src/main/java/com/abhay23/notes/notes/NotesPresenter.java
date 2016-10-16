package com.abhay23.notes.notes;

import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.model.Note;
import com.abhay23.notes.model.NotesManager;
import java.util.List;
import rx.Observable;

class NotesPresenter extends BasePresenter {

  private final NotesView view;
  private final NotesManager notesManager;

  NotesPresenter(NotesView view, NotesManager notesManager) {
    this.view = view;
    this.notesManager = notesManager;
  }

  @Override public void onViewCreated(boolean isNewLaunch) {
    view.initView();

    if (isNewLaunch) {
      notesManager.getNotes()
          .flatMap(Observable::from)
          .toSortedList(this::compareCreationDate)
          .subscribe(this::onNotesLoaded, this::onErrorLoadingNotes);
    }
  }

  private void onNotesLoaded(List<Note> notes) {
    view.showNotes(notes);
  }

  private void onErrorLoadingNotes(Throwable throwable) {

  }

  private int compareCreationDate(Note note, Note note2) {
    return note.getCreatedAt().compareTo(note2.getCreatedAt());
  }
}
