// Copyright (c) 2014 by Andreas W. Bartels (bartels@anwiba.net)
package net.anwiba.script.java;

import java.lang.reflect.InvocationTargetException;

import net.anwiba.commons.message.IMessageCollector;
import net.anwiba.commons.reflection.annotation.Injection;
import net.anwiba.commons.reflection.annotation.Nullable;
import net.anwiba.commons.thread.cancel.ICanceler;
import net.anwiba.spatial.api.IFacade;
import net.anwiba.spatial.scripting.java.api.AbstractScript;

public class DoNothing extends AbstractScript {

  @Nullable
  @Injection
  private final IMessageCollector monitor = null;

  @Nullable
  @Injection
  private final ICanceler canceler = null;

  @Injection
  private final IFacade facade = null;

  @Override
  public void run() throws InterruptedException, InvocationTargetException {
    // nothing to do
  }

}
