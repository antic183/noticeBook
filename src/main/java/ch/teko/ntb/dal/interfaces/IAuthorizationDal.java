package ch.teko.ntb.dal.interfaces;

import ch.teko.ntb.dto.oUser;
import ch.teko.ntb.model.User;

/**
 * Created by antic-software-ing on 08.09.2017.
 */
public interface IAuthorizationDal {
  public oUser login(User user) throws Exception;
  public int signup(User user) throws Exception;
}
