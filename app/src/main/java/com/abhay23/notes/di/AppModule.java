package com.abhay23.notes.di;

import com.abhay23.notes.NotesApplication;
import com.abhay23.notes.model.LocalDataStore;
import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class AppModule {

  private final NotesApplication application;

  public AppModule(NotesApplication application) {
    this.application = application;
  }

  @Singleton @Provides public NotesManager provideNotesManager(LocalDataStore localDataStore) {
    return new NotesManager(localDataStore);
  }

  @Singleton @Provides public LocalDataStore provideDatabase() {
    return new LocalDataStore();
  }

  @Singleton @Provides public RxUtils provideRxUtils() {
    return new RxUtils();
  }
}
