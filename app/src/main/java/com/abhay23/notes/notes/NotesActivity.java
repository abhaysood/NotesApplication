package com.abhay23.notes.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.abhay23.notes.BaseActivity;
import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.R;
import com.abhay23.notes.model.Note;
import java.util.List;
import javax.inject.Inject;

public class NotesActivity extends BaseActivity implements NotesView {

  @Inject NotesPresenter presenter;

  @Override public void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_notes);
    super.onCreate(savedInstanceState);
  }

  @NonNull @Override public BasePresenter getPresenter() {
    return presenter;
  }

  @Override public void initView() {
  }

  @Override public void showNotes(List<Note> notes) {

  }
}
