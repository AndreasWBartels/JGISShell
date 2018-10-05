// Copyright (c) 2014 by Andreas W. Bartels
package net.anwiba.script.java.example;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import net.anwiba.commons.injection.annotation.Injection;
import net.anwiba.commons.injection.annotation.Nullable;
import net.anwiba.commons.lang.exception.CanceledException;
import net.anwiba.commons.message.IMessageCollector;
import net.anwiba.commons.thread.cancel.ICanceler;
import net.anwiba.spatial.api.IFacade;
import net.anwiba.spatial.scripting.java.api.AbstractScript;

public class LoadShapeFile extends AbstractScript {

  @Nullable
  @Injection
  private final IMessageCollector monitor = null;

  @Nullable
  @Injection
  private final ICanceler canceler = null;

  @Injection
  private final IFacade facade = null;

  @Override
  public void run() throws CanceledException, InvocationTargetException {
    try {
      this.facade.view().add(
          "natural aereas", //$NON-NLS-1$
          this.facade.layerReference("$SYSTEM{jgisshell.workingpath}/data/osm/Karlsruhe/Karlsruhe-shp/shape/natural.shp" //$NON-NLS-1$
          ));
    } catch (final IOException exception) {
      throw new InvocationTargetException(exception);
    }
  }
}
