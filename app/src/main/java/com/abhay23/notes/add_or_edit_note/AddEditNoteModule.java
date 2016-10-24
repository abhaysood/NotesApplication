package com.abhay23.notes.add_or_edit_note;

import com.abhay23.notes.di.ActivityScope;
import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;
import dagger.Module;
import dagger.Provides;

@Module public class AddEditNoteModule {

  private final AddEditNoteActivity addEditNoteActivity;

  public AddEditNoteModule(AddEditNoteActivity addEditNoteActivity) {
    this.addEditNoteActivity = addEditNoteActivity;
  }

  @ActivityScope
  @Provides
  public AddEditNotePresenter provideEditNotePresenter(NotesManager notesManager) {
    return new AddEditNotePresenter(addEditNoteActivity, notesManager);
  }
}
