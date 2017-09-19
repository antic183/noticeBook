package ch.teko.ntb.business;

import ch.teko.ntb.business.util.TokenHandler;
import ch.teko.ntb.dal.interfaces.IAuthorizationDal;
import ch.teko.ntb.dto.UserDto;
import ch.teko.ntb.model.User;
import com.google.inject.Inject;

/**
 * Created by antic-software-ing on 08.09.2017.
 */
public class AuthorizationManager {

  @Inject
  private IAuthorizationDal dbAuthorization;

  /**
   * login return user-dto with jwt-token and his noticelist
   */
  public UserDto login(User user) throws Exception {
    // db login
    UserDto userDto = dbAuthorization.login(user);
    // create and set jwt token
    String jwtToken = TokenHandler.createToken(userDto);
    userDto.setJwtToken(jwtToken);

    return userDto;
  }

  /**
   * signup return user id
   */
  public int signup(User user) throws Exception {
    int userId = dbAuthorization.signup(user);
    return userId;
  }
}
