package com.abhay23.notes.notes;

import com.abhay23.notes.model.Note;
import java.util.List;

interface NotesView {
  void initView();

  void showNotes(List<Note> notes);
}
