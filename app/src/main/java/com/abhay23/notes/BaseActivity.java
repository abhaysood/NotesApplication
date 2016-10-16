package com.abhay23.notes;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.abhay23.notes.di.Injector;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject(((NotesApplication) getApplication()).getDaggerInjector());
    getPresenter().onViewCreated(savedInstanceState == null);
  }

  protected abstract void inject(Injector appComponent);

  @Override @CallSuper protected void onDestroy() {
    super.onDestroy();
    getPresenter().unsubscribe();
  }

  public abstract @NonNull BasePresenter getPresenter();

  public View getRootView() {
    return findViewById(android.R.id.content);
  }
}
