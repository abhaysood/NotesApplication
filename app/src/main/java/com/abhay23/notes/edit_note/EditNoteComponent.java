package com.abhay23.notes.edit_note;

import com.abhay23.notes.di.ActivityScope;
import com.abhay23.notes.di.AppComponent;
import dagger.Component;

@ActivityScope @Component(modules = EditNoteModule.class, dependencies = AppComponent.class)
public interface EditNoteComponent {
  void inject(EditNoteActivity editNoteActivity);
}
