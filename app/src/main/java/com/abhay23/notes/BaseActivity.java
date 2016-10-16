package com.abhay23.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getPresenter().onViewCreated(savedInstanceState == null);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    getPresenter().unsubscribe();
  }

  public abstract @NonNull BasePresenter getPresenter();

  public View getRootView() {
    return findViewById(android.R.id.content);
  }
}
