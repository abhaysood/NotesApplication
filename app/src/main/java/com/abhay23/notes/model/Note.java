package com.abhay23.notes.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class Note implements Parcelable {

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
}
