package ch.teko.ntb.model;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by antic-software-ing on 04.09.2017.
 */

@XmlRootElement
public class Notice {

  private int id;
  @Size(min = 1, max = 255)
  private String title;
  @Size(min = 1, max = 255)
  private String text;

  public Notice() {
  }

  public Notice(String title, String text) {
    this.title = title;
    this.text = text;
  }

  public Notice(int id, String title, String text) {
    this(title, text);
    this.id = id;
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
}
