package com.abhay23.notes.notes;

import android.support.v7.widget.LinearLayoutManager;
import com.abhay23.notes.di.ActivityScope;
import com.abhay23.notes.model.NotesManager;
import dagger.Module;
import dagger.Provides;

@Module public final class NotesModule {
  private final NotesActivity notesActivity;

  public NotesModule(NotesActivity notesActivity) {
    this.notesActivity = notesActivity;
  }

  @Provides @ActivityScope
  public NotesPresenter provideNotesPresenter(NotesManager notesManager) {
    return new NotesPresenter(notesActivity, notesManager);
  }

  @Provides @ActivityScope public NotesAdapter provideNotesAdapter() {
    return new NotesAdapter(notesActivity);
  }

  @Provides @ActivityScope public LinearLayoutManager provideLinearLayoutManager() {
    return new LinearLayoutManager(notesActivity, LinearLayoutManager.VERTICAL, false);
  }

}
