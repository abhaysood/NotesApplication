package com.abhay23.notes.notes;

import android.support.annotation.NonNull;
import com.abhay23.notes.model.Note;
import java.util.List;

interface NotesView {
  void initView();

  void showNotes(@NonNull List<Note> notes);
}
