package com.abhay23.notes.add_or_edit_note;

import com.abhay23.notes.di.ActivityScope;
import com.abhay23.notes.di.AppComponent;
import dagger.Component;

@ActivityScope @Component(modules = AddEditNoteModule.class, dependencies = AppComponent.class)
public interface AddEditNoteComponent {
  void inject(AddEditNoteActivity addEditNoteActivity);
}
