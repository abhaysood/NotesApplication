package com.abhay23.notes.di;

import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { AppModule.class }) public interface AppComponent {
}
