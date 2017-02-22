// Copyright (c) 2014 by Andreas W. Bartels (bartels@anwiba.net)
package net.anwiba.script.java.example;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import net.anwiba.commons.message.IMessageCollector;
import net.anwiba.commons.process.cancel.ICanceler;
import net.anwiba.commons.reflection.annotation.Injection;
import net.anwiba.commons.reflection.annotation.Nullable;
import net.anwiba.gis.api.IFacade;
import net.anwiba.gis.scripting.java.api.IScript;

public class LoadShapeFile implements IScript {

  @Nullable
  @Injection
  private final IMessageCollector monitor = null;

  @Nullable
  @Injection
  private final ICanceler canceler = null;

  @Injection
  private final IFacade facade = null;

  @Override
  public void excecute() throws InterruptedException, InvocationTargetException {
    try {
      this.facade.view().add("natural aereas", //$NON-NLS-1$
          this.facade.layerReference("$SYSTEM{jgisshell.workingpath}/data/osm/Karlsruhe/Karlsruhe-shp/shape/natural.shp" //$NON-NLS-1$
              ));
    } catch (final IOException exception) {
      throw new InvocationTargetException(exception);
    }
  }
}
