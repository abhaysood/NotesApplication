package com.abhay23.notes.notes;

import com.abhay23.notes.di.ActivityScope;
import com.abhay23.notes.di.AppComponent;
import dagger.Component;

@ActivityScope @Component(modules = NotesModule.class, dependencies = AppComponent.class)
public interface NotesComponent {
  void inject(NotesActivity notesActivity);
}
