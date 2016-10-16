package com.abhay23.notes.edit_note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.abhay23.notes.BaseActivity;
import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.R;
import com.abhay23.notes.di.Injector;
import javax.inject.Inject;

public class EditNoteActivity extends BaseActivity implements EditNoteView {
  private static final String ARG_NOTE_ID = "arg_note_id";

  @Inject EditNotePresenter presenter;

  public static void start(Context context, long id) {
    Intent intent = new Intent(context, EditNoteActivity.class);
    intent.putExtra(ARG_NOTE_ID, id);
    context.startActivity(intent);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_edit_note);
    super.onCreate(savedInstanceState);
  }

  @Override protected void inject(Injector injector) {
    injector.inject(this);
  }

  @NonNull @Override public BasePresenter getPresenter() {
    return presenter;
  }
}
