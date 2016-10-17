package com.abhay23.notes.edit_note;

import com.abhay23.notes.di.ActivityScope;
import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;
import dagger.Module;
import dagger.Provides;

@Module public class EditNoteModule {

  private final EditNoteActivity editNoteActivity;

  public EditNoteModule(EditNoteActivity editNoteActivity) {
    this.editNoteActivity = editNoteActivity;
  }

  @ActivityScope
  @Provides
  public EditNotePresenter provideEditNotePresenter(NotesManager notesManager, RxUtils rxUtils) {
    return new EditNotePresenter(editNoteActivity, notesManager, rxUtils);
  }
}
