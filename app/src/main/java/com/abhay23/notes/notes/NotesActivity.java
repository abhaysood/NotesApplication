package com.abhay23.notes.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.abhay23.notes.BaseActivity;
import com.abhay23.notes.BasePresenter;
import com.abhay23.notes.R;
import com.abhay23.notes.add_or_edit_note.EditNoteActivity;
import com.abhay23.notes.di.Injector;
import com.abhay23.notes.model.Note;
import java.util.List;
import javax.inject.Inject;

public class NotesActivity extends BaseActivity
    implements NotesView, NotesAdapter.OnItemClickListener {

  @Inject NotesPresenter presenter;
  @Inject NotesAdapter adapter;

  @Bind(R.id.notes_recycler_view) RecyclerView notesRecyclerView;
  @Bind(R.id.no_notes_error_view) View noNotesErrorView;

  @Override public void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_notes);
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
    notesRecyclerView.setAdapter(adapter);
    notesRecyclerView.setHasFixedSize(true);
    notesRecyclerView.setLayoutManager(
        new LinearLayoutManager(NotesActivity.this, LinearLayoutManager.VERTICAL, false));
  }

  @Override public void showNotes(@NonNull List<Note> notes) {
    notesRecyclerView.setVisibility(View.VISIBLE);
    noNotesErrorView.setVisibility(View.GONE);
    adapter.setNotes(notes);
  }

  @Override public void onNoteUpdated(@NonNull Note note) {
    notesRecyclerView.setVisibility(View.VISIBLE);
    noNotesErrorView.setVisibility(View.GONE);
    // This should not be happening here. Use more detailed update listeners in the model
    if (adapter.getNotes().contains(note)) {
      adapter.updateNote(note);
    } else {
      adapter.addNote(note);
    }
  }

  @Override public void showNoNotesError() {
    noNotesErrorView.setVisibility(View.VISIBLE);
    notesRecyclerView.setVisibility(View.GONE);
  }

  @Override public void onClick(Note note, int position) {
    EditNoteActivity.start(this, note.getId());
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_add_note:
        presenter.onAddNewNoteClick();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void openAddNewNoteScreen() {
    EditNoteActivity.start(this, -1);
  }
}
