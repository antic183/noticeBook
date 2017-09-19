package ch.teko.ntb.dto;

import java.io.Serializable;

/**
 * Created by antic-software-ing on 04.09.2017.
 */

public class UserDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private int id;
  private String email;
  private String jwtToken;

  public UserDto() {
  }

  public UserDto(int id, String email) {
    this();
    this.id = id;
    this.email = email;
  }


  public int getId() {
    return id;
  }

//  public void setId(int id) {
//    this.id = id;
//  }

  public String getEmail() {
    return email;
  }

//  public void setEmail(String email) {
//    this.email = email;
//  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }

}
