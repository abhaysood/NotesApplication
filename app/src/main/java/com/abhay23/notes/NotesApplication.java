package com.abhay23.notes;

import android.app.Application;
import com.abhay23.notes.di.DaggerInjector;
import com.abhay23.notes.di.Injector;

public class NotesApplication extends Application {

  private Injector daggerInjector;

  public void initDagger() {
    getDaggerInjector().init(this);
  }

  public Injector getDaggerInjector() {
    if (daggerInjector == null) {
      daggerInjector = new DaggerInjector();
    }
    return daggerInjector;
  }

  @Override public void onCreate() {
    super.onCreate();
    initDagger();
  }
}
