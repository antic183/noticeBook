package ch.teko.ntb.dto;

import ch.teko.ntb.model.Notice;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antic-software-ing on 04.09.2017.
 */

@XmlRootElement
public class oUser {
  private int id;
  private String email;
  private String jwtToken;

  public oUser() {
  }

  public oUser(int id, String email) {
    this();
    this.id = id;
    this.email = email;
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

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }

}
