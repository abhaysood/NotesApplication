package com.abhay23.notes.model;

public class Note {
  private String title;
  private String note;
  private String imagePath;
  long createdAt;

  public Note() {
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

  public long getCreatedAt() {
    return createdAt;
  }
}
