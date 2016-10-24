package com.abhay23.notes.di;

import android.app.Application;
import com.abhay23.notes.NotesApplication;
import com.abhay23.notes.db.DbModule;
import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { AppModule.class, DbModule.class }) public interface AppComponent {

  NotesManager provideNotesManager();

  RxUtils provideRxUtils();

  Application provideApplication();
}