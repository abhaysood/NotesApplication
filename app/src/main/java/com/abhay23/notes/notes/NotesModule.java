package com.abhay23.notes.notes;

import com.abhay23.notes.di.ActivityScope;
import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;
import dagger.Module;
import dagger.Provides;

@Module public class NotesModule {
  private final NotesActivity notesActivity;

  public NotesModule(NotesActivity notesActivity) {
    this.notesActivity = notesActivity;
  }

  @Provides @ActivityScope
  public NotesPresenter provideNotesPresenter(NotesManager notesManager, RxUtils rxUtils) {
    return new NotesPresenter(notesActivity, notesManager, rxUtils);
  }

  @Provides @ActivityScope public NotesAdapter provideNotesAdapter() {
    return new NotesAdapter(notesActivity);
  }
}
