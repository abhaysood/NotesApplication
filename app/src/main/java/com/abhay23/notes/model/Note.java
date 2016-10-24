package com.abhay23.notes.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import com.abhay23.notes.db.Db;
import java.util.Date;
import rx.functions.Func1;

public final class Note implements Parcelable {

  public static final String ID = "_id";
  public static final String TITLE = "title";
  public static final String NOTE = "note";
  public static final String IMAGE_PATH = "image_path";
  public static final String CREATED_AT = "created_at";

  private long id;
  private String title;
  private String note;
  private String imagePath;
  private Date createdAt;

  public Note(long id, String title, String note, String imagePath, Date createdAt) {
    this.id = id;
    this.createdAt = createdAt;
    this.title = title;
    this.note = note;
    this.imagePath = imagePath;
  }

  public static final Func1<Cursor, Note> MAPPER = cursor -> {
    long id1 = Db.getLong(cursor, ID);
    String title1 = Db.getString(cursor, TITLE);
    String note1 = Db.getString(cursor, NOTE);
    long createdAt1 = Db.getLong(cursor, CREATED_AT);
    String imagePath1 = Db.getString(cursor, IMAGE_PATH);
    return new Note(id1, title1, note1, imagePath1, new Date(createdAt1));
  };


  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getNote() {
    return note;
  }

  public String getImagePath() {
    return imagePath;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  @Override public String toString() {
    return "Note{" +
        "title='" + title + '\'' +
        ", note='" + note + '\'' +
        ", imagePath='" + imagePath + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Note)) return false;

    Note note1 = (Note) o;

    if (id != note1.id) return false;
    if (title != null ? !title.equals(note1.title) : note1.title != null) return false;
    if (note != null ? !note.equals(note1.note) : note1.note != null) return false;
    if (imagePath != null ? !imagePath.equals(note1.imagePath) : note1.imagePath != null) {
      return false;
    }
    return createdAt != null ? createdAt.equals(note1.createdAt) : note1.createdAt == null;
  }

  @Override public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (note != null ? note.hashCode() : 0);
    result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
    result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
    return result;  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(this.id);
    dest.writeString(this.title);
    dest.writeString(this.note);
    dest.writeString(this.imagePath);
    dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
  }

  protected Note(Parcel in) {
    this.id = in.readLong();
    this.title = in.readString();
    this.note = in.readString();
    this.imagePath = in.readString();
    long tmpCreatedAt = in.readLong();
    this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
  }

  public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
    @Override public Note createFromParcel(Parcel source) {
      return new Note(source);
    }

    @Override public Note[] newArray(int size) {
      return new Note[size];
    }
  };

  public static final class Builder {
    private final ContentValues values = new ContentValues();

    public Builder() {
      values.put(ID, System.currentTimeMillis());
    }

    public Builder(Note note) {
      values.put(ID, System.currentTimeMillis());
      values.put(TITLE, note.title);
      values.put(NOTE, note.note);
      values.put(CREATED_AT, note.createdAt.getTime());
      values.put(IMAGE_PATH, note.imagePath);
    }

    public Builder title(String title) {
      values.put(TITLE, title);
      return this;
    }

    public Builder note(String note) {
      values.put(NOTE, note);
      return this;
    }

    public Builder createdAt(long createdAt) {
      values.put(CREATED_AT, createdAt);
      return this;
    }

    public Builder imagePath(String imagePath) {
      values.put(IMAGE_PATH, imagePath);
      return this;
    }

    public ContentValues build() {
      return values;
    }
  }
}
