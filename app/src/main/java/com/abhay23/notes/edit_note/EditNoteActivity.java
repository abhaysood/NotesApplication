package com.abhay23.notes.edit_note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.abhay23.notes.BaseActivity;
import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.R;
import com.abhay23.notes.di.Injector;
import com.abhay23.notes.model.Note;
import com.jakewharton.rxbinding.widget.RxTextView;
import javax.inject.Inject;
import rx.Observable;

public class EditNoteActivity extends BaseActivity implements EditNoteView {
  private static final String EXTRA_NOTE_ID = "arg_note_id";

  @Inject EditNotePresenter presenter;

  @Bind(R.id.et_title) EditText etNoteTitle;
  @Bind(R.id.til_title) TextInputLayout tilNoteTitle;
  @Bind(R.id.et_description) EditText etNoteDescription;
  @Bind(R.id.til_description) TextInputLayout tilNoteDescription;
  @Bind(R.id.btn_save) Button btnSave;

  public static void start(Context context, long id) {
    Intent intent = new Intent(context, EditNoteActivity.class);
    intent.putExtra(EXTRA_NOTE_ID, id);
    context.startActivity(intent);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_edit_note);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
  }

  @Override protected void inject(Injector injector) {
    injector.inject(this);
  }

  @NonNull @Override public BasePresenter getPresenter() {
    return presenter;
  }

  @Override public void initView() {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Observable<Boolean> titleObservable = RxTextView.textChanges(etNoteTitle)
        .skip(1)
        .map(inputText -> (inputText.length() > 0))
        .distinctUntilChanged();

    titleObservable.subscribe(isValid -> {
      handleErrorOnTextFields(tilNoteTitle, isValid, getString(R.string.error_field_required));
    });

    Observable<Boolean> descriptionObservable = RxTextView.textChanges(etNoteDescription)
        .skip(1)
        .map(inputText -> (inputText.length() > 0))
        .distinctUntilChanged();

    descriptionObservable.subscribe(isValid -> {
      handleErrorOnTextFields(tilNoteDescription, isValid,
          getString(R.string.error_field_required));
    });

    Observable.combineLatest(titleObservable, descriptionObservable,
        (titleValid, descriptionValid) -> titleValid && descriptionValid)
        .distinctUntilChanged()
        .subscribe(valid -> {
          btnSave.setEnabled(valid);
          btnSave.setClickable(valid);
        });
  }

  private void handleErrorOnTextFields(TextInputLayout textInputLayout, Boolean isValid,
      String errorText) {
    if (!isValid) {
      textInputLayout.setError(errorText);
    } else {
      textInputLayout.setError(null);
    }
  }

  @OnClick(R.id.btn_save) public void onSaveButtonClick() {
    presenter.onSaveButtonClicked(getNoteId(), etNoteTitle.getText().toString(),
        etNoteDescription.getText().toString(), "TODO PATH OF IMAGE");
  }

  @Override public long getNoteId() {
    return getIntent().getExtras().getLong(EXTRA_NOTE_ID, -1);
  }

  @Override public void showNote(Note note) {
    etNoteTitle.setText(note.getTitle());
    etNoteDescription.setText(note.getNote());
    // TODO show image if available
  }

  @Override public void setScreenTitle(String title) {
    setTitle(title);
  }

  @Override public void killScreen() {
    finish();
  }

  @Override public void showErrorLoadingNote() {
    Snackbar.make(getRootView(), "Unable to load note.", Snackbar.LENGTH_LONG).show();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    if (getNoteId() != -1) {
      getMenuInflater().inflate(R.menu.menu_delete, menu);
      return true;
    } else {
      return super.onCreateOptionsMenu(menu);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_delete_note:
        presenter.onDeleteNoteClicked();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
