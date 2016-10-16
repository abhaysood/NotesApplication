package com.abhay23.notes.di;

import com.abhay23.notes.NotesApplication;
import com.abhay23.notes.model.Database;
import com.abhay23.notes.model.NotesManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class AppModule {

  private final NotesApplication application;

  public AppModule(NotesApplication application) {
    this.application = application;
  }

  @Singleton
  @Provides
  public NotesManager provideNotesManager(Database database) {
    return new NotesManager(database);
  }

  @Singleton
  @Provides
  public Database provideDatabase() {
    return new Database();
  }
}
