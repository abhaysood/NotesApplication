package com.abhay23.notes.di;

import android.app.Application;
import com.abhay23.notes.NotesApplication;

public class DaggerInjector implements Injector {
  private static AppComponent appComponent;

  public void init(Application application) {
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule((NotesApplication) application))
        .build();
  }
}
