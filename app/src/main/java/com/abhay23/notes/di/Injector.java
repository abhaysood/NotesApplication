package com.abhay23.notes.di;

import android.app.Application;
import com.abhay23.notes.notes.NotesActivity;

public interface Injector {
  void init(Application application);

  void inject(NotesActivity notesActivity);
}
