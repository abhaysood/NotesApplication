package com.abhay23.notes.di;

import android.app.Application;
import com.abhay23.notes.NotesApplication;
import com.abhay23.notes.edit_note.DaggerEditNoteComponent;
import com.abhay23.notes.edit_note.EditNoteActivity;
import com.abhay23.notes.edit_note.EditNoteModule;
import com.abhay23.notes.notes.DaggerNotesComponent;
import com.abhay23.notes.notes.NotesActivity;
import com.abhay23.notes.notes.NotesModule;

public class DaggerInjector implements Injector {
  private static AppComponent appComponent;

  public void init(Application application) {
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule((NotesApplication) application))
        .build();
  }

  @Override public void inject(NotesActivity notesActivity) {
    DaggerNotesComponent.builder()
        .notesModule(new NotesModule(notesActivity))
        .appComponent(appComponent)
        .build()
        .inject(notesActivity);
  }

  @Override public void inject(EditNoteActivity editNoteActivity) {
    DaggerEditNoteComponent.builder()
        .editNoteModule(new EditNoteModule(editNoteActivity))
        .appComponent(appComponent)
        .build()
        .inject(editNoteActivity);
  }
}
