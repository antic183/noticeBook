package ch.teko.ntb.model;

import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * Created by antic-software-ing on 04.09.2017.
 */

public class Note implements Serializable {
  private static final long serialVersionUID = 1L;

  private int id;
  @Size(min = 1, max = 255)
  private String title;
  @Size(min = 1, max = 255)
  private String text;
  private String createdAtUtc;

  public Note() {
  }

  public Note(String title, String text) {
    this.title = title;
    this.text = text;
  }

  public Note(int id, String title, String text) {
    this(title, text);
    this.id = id;
  }

  public Note(int id, String title, String text, String createdAt) {
    this(title, text);
    this.id = id;
    this.createdAtUtc = createdAt;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getCreatedAtUtc() {
    return createdAtUtc;
  }
}
