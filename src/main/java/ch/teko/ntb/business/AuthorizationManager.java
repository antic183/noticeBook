package ch.teko.ntb.business;

import ch.teko.ntb.business.util.TokenHandler;
import ch.teko.ntb.dal.interfaces.IAuthorizationDal;
import ch.teko.ntb.dal.interfaces.INoticeDal;
import ch.teko.ntb.dto.oUser;
import ch.teko.ntb.model.Notice;
import ch.teko.ntb.model.User;
import com.google.inject.Inject;

import java.util.List;

/**
 * Created by antic-software-ing on 08.09.2017.
 */
public class AuthorizationManager {

  @Inject
  private IAuthorizationDal dbAuthorization;

  /**
   * login return user-dto with jwt-token and his noticelist
   */
  public oUser login(User user) throws Exception {
    // db login
    oUser userDto = dbAuthorization.login(user);
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
