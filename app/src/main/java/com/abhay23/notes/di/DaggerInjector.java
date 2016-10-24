package com.abhay23.notes.di;

import android.app.Application;
import com.abhay23.notes.NotesApplication;
import com.abhay23.notes.add_or_edit_note.AddEditNoteActivity;
import com.abhay23.notes.add_or_edit_note.AddEditNoteModule;
import com.abhay23.notes.add_or_edit_note.DaggerAddEditNoteComponent;
import com.abhay23.notes.notes.DaggerNotesComponent;
import com.abhay23.notes.notes.NotesActivity;
import com.abhay23.notes.notes.NotesModule;

public final class DaggerInjector implements Injector {
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

  @Override public void inject(AddEditNoteActivity addEditNoteActivity) {
    DaggerAddEditNoteComponent.builder()
        .addEditNoteModule(new AddEditNoteModule(addEditNoteActivity))
        .appComponent(appComponent)
        .build()
        .inject(addEditNoteActivity);
  }
}
