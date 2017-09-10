package ch.teko.ntb.config;

import ch.teko.ntb.dal.interfaces.IAuthorizationDal;
import ch.teko.ntb.dal.interfaces.INoticeDal;
import ch.teko.ntb.dal.mysql.AuthorizationMySqlDal;
import ch.teko.ntb.dal.mysql.NoticeMySqlDal;
import com.google.inject.AbstractModule;

/**
 * Created by antic-software-ing on 09.09.2017.
 * Config for google guice dependency Injection
 */
public class DpiConfig extends AbstractModule {
  @Override
  protected void configure() {
    bind(IAuthorizationDal.class).to(AuthorizationMySqlDal.class).asEagerSingleton();
    bind(INoticeDal.class).to(NoticeMySqlDal.class).asEagerSingleton();
  }
}
