package ch.teko.ntb.endpoints;

/**
 * Created by antic-software-ing on 08.09.2017.
 */

import ch.teko.ntb.config.DpiConfig;
import com.google.inject.Guice;
import com.google.inject.Injector;

abstract class AbstractInjector {

  protected static Injector injector = null;

  public AbstractInjector() {
    if (injector == null) {
      injector = Guice.createInjector(new DpiConfig());
    }
  }
}