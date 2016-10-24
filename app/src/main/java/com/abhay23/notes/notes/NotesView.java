package com.abhay23.notes.notes;

import android.support.annotation.NonNull;
import com.abhay23.notes.model.Note;
import java.util.List;

interface NotesView {
  void showNotes(@NonNull List<Note> notes);

  void onNoteUpdated(@NonNull Note note);

  void showNoNotesError();

  void openAddNewNoteScreen();
}
