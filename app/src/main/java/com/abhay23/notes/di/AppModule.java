package com.abhay23.notes.di;

import com.abhay23.notes.NotesApplication;
import dagger.Module;

@Module public class AppModule {

  private final NotesApplication application;

  public AppModule(NotesApplication application) {
    this.application = application;
  }
}
