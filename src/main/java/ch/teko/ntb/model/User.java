package ch.teko.ntb.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * Created by antic-software-ing on 04.09.2017.
 */

public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  private int id;
  @Size(min = 2, max = 255)
  @Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
  private String email;
  @Size(min = 6, max = 255)
  private String password;


  public User() {
  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
