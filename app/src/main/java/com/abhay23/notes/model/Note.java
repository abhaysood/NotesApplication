package com.abhay23.notes.model;

import java.util.Date;

public class Note {
  private String title;
  private String note;
  private String imagePath;
  Date createdAt;

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

  public Date getCreatedAt() {
    return createdAt;
  }
}
