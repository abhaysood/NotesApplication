package com.abhay23.notes.di;

import com.abhay23.notes.model.NotesManager;
import com.abhay23.notes.util.RxUtils;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { AppModule.class }) public interface AppComponent {

  NotesManager provideNotesManager();

  RxUtils provideRxUtils();
}
