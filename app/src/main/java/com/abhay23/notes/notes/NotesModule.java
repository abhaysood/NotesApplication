package com.abhay23.notes.notes;

import com.abhay23.notes.di.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module public class NotesModule {
  private final NotesActivity notesActivity;

  public NotesModule(NotesActivity notesActivity) {
    this.notesActivity = notesActivity;
  }

  @Provides
  @ActivityScope
  public NotesPresenter provideNotestPresenter() {
    return new NotesPresenter(notesActivity);
  }

}
