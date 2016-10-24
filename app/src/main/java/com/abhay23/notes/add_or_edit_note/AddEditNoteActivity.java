package com.abhay23.notes.add_or_edit_note;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
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
import com.abhay23.notes.util.GrayFilterTouchImageView;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.widget.RxTextView;
import javax.inject.Inject;
import rx.Observable;

public class AddEditNoteActivity extends BaseActivity implements AddEditNoteView {
  private static final String EXTRA_NOTE_ID = "arg_note_id";
  private static final int REQUEST_GALLERY = 101;
  private static final int REQUEST_GALLERY_PERMISSION = 123;

  @Inject AddEditNotePresenter presenter;

  @Bind(R.id.et_title) EditText etNoteTitle;
  @Bind(R.id.til_title) TextInputLayout tilNoteTitle;
  @Bind(R.id.et_description) EditText etNoteDescription;
  @Bind(R.id.til_description) TextInputLayout tilNoteDescription;
  @Bind(R.id.btn_save) Button btnSave;
  @Bind(R.id.note_image) GrayFilterTouchImageView noteImage;

  public static void start(Context context, long id) {
    Intent intent = new Intent(context, AddEditNoteActivity.class);
    intent.putExtra(EXTRA_NOTE_ID, id);
    context.startActivity(intent);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_note);
    ButterKnife.bind(this);
    presenter.onViewCreated();
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.onViewReady();
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
        etNoteDescription.getText().toString());
  }

  @Override public long getNoteId() {
    return getIntent().getExtras().getLong(EXTRA_NOTE_ID, -1);
  }

  @Override public void showNote(Note note) {
    etNoteTitle.setText(note.getTitle());
    etNoteDescription.setText(note.getNote());
  }

  @Override public void showImage(String path) {
    Glide.with(this).load(path).placeholder(R.drawable.placeholder).centerCrop().into(noteImage);
  }

  @Override public void setScreenTitle(@StringRes int title) {
    setTitle(title);
  }

  @Override public void killScreen() {
    finish();
  }

  @Override public void showErrorLoadingNote() {
    Snackbar.make(getRootView(), "Unable to load note.", Snackbar.LENGTH_LONG).show();
  }

  @OnClick(R.id.note_image) public void onImageClicked() {
    checkGalleryPermission();
  }

  private void checkGalleryPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
          != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
            REQUEST_GALLERY_PERMISSION);
        return;
      }
    }

    presenter.onImageClicked();
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == REQUEST_GALLERY_PERMISSION
        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      presenter.onImageClicked();
    }
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

  @Override public void openGallery() {
    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(i, REQUEST_GALLERY);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == REQUEST_GALLERY) {
        onGalleryImageResult(data);
        return;
      }
    }
  }

  private void onGalleryImageResult(Intent data) {
    Uri selectedImage = data.getData();
    String[] filePathColumn = { MediaStore.Images.Media.DATA };
    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
    if (cursor != null) {
      cursor.moveToFirst();
      int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
      String picturePath = cursor.getString(columnIndex);
      cursor.close();
      showImage(picturePath);
      presenter.saveImagePath(picturePath);
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    presenter.saveState(outState);
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    presenter.restoreState(savedInstanceState);
  }
}
