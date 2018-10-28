/*
 * #%L
 * jgisshell scripting
 * %%
 * Copyright (C) 2007 - 2018 Andreas W. Bartels
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package net.anwiba.script.java;

import java.lang.reflect.InvocationTargetException;

import net.anwiba.commons.annotation.Injection;
import net.anwiba.commons.annotation.Nullable;
import net.anwiba.commons.lang.exception.CanceledException;
import net.anwiba.commons.message.IMessageCollector;
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
  public void run() throws CanceledException, InvocationTargetException {
    // nothing to do
  }

}
