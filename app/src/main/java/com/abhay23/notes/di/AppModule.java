package com.abhay23.notes.di;

import android.app.Application;
import com.abhay23.notes.NotesApplication;
import com.abhay23.notes.db.LocalDataStore;
import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;
import com.squareup.sqlbrite.BriteDatabase;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module final class AppModule {

  private final NotesApplication application;

  public AppModule(NotesApplication application) {
    this.application = application;
  }

  @Singleton @Provides Application provideApplication() {
    return application;
  }

  @Singleton @Provides public NotesManager provideNotesManager(LocalDataStore localDataStore) {
    return new NotesManager(localDataStore);
  }

  @Singleton @Provides public LocalDataStore provideDatabase(BriteDatabase briteDatabase) {
    return new LocalDataStore(briteDatabase);
  }

  @Singleton @Provides public RxUtils provideRxUtils() {
    return new RxUtils();
  }
}
