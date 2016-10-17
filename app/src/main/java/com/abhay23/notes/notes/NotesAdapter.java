package com.abhay23.notes.notes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.abhay23.notes.R;
import com.abhay23.notes.model.Note;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

  private final static String BASE_IMAGE_URL = "http://gojek-contacts-app.herokuapp.com";
  private List<Note> notes;

  private final OnItemClickListener onItemClickListener;

  public void updateNote(Note note) {

  }

  public void addNote(Note note) {
    notes.add(note);
    notifyItemInserted(notes.size() - 1);
  }

  public interface OnItemClickListener {
    void onClick(Note note, int position);
  }

  public NotesAdapter(@NonNull OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
    notes = new ArrayList<>();
  }

  @Override public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item, parent, false);
    return new NotesViewHolder(itemView);
  }

  @Override public void onBindViewHolder(NotesViewHolder holder, int position) {
    holder.bind(position);
    holder.itemView.setOnClickListener(
        v -> onItemClickListener.onClick(notes.get(position), position));
  }

  @Override public int getItemCount() {
    return notes.size();
  }

  public void setNotes(List<Note> notes) {
    this.notes = notes;
    notifyDataSetChanged();
  }

  public List<Note> getNotes() {
    return notes;
  }

  public class NotesViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.note_thumbnail) ImageView noteImageView;
    @Bind(R.id.note_title) TextView noteTitleTextView;
    @Bind(R.id.note_desc) TextView noteDescTextView;
    @Bind(R.id.note_created_at) TextView noteCreatedAtTextView;

    public NotesViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bind(int position) {
      Note note = notes.get(position);
      noteTitleTextView.setText(note.getTitle());
      noteDescTextView.setText(note.getNote());
      noteCreatedAtTextView.setText(getHumanReadableTime(note.getCreatedAt()));

      Glide.with(noteImageView.getContext())
          .load(note.getImagePath())
          .placeholder(R.drawable.placeholder)
          .centerCrop()
          .into(noteImageView);
    }
  }

  private String getHumanReadableTime(Date date) {
    return date.toString();
  }
}
