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
package net.anwiba.script.java.example;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import net.anwiba.commons.annotation.Injection;
import net.anwiba.commons.annotation.Nullable;
import net.anwiba.commons.lang.exception.CanceledException;
import net.anwiba.commons.message.IMessageCollector;
import net.anwiba.commons.thread.cancel.ICanceler;
import net.anwiba.spatial.api.IFacade;
import net.anwiba.spatial.api.layer.feature.IFeatureLayer;
import net.anwiba.spatial.api.layer.feature.IFeatureLayerBuilder;
import net.anwiba.spatial.geometry.exception.GeometryConversionException;
import net.anwiba.spatial.scripting.java.api.AbstractScript;

public class Example extends AbstractScript {

  @Nullable
  @Injection
  private final IMessageCollector monitor = null;

  @Nullable
  @Injection
  private final ICanceler canceler = null;

  @Injection
  private final IFacade facade = null;

  @Injection
  private final Iterable<IFacade> facades = new ArrayList<>();

  @Override
  public void run() throws CanceledException, InvocationTargetException {
    try {
      final IFeatureLayerBuilder featureLayerBuilder = this.facade.featureLayerBuilder();
      featureLayerBuilder.values(null, this.facade.geometry("POINT (10 10)")); //$NON-NLS-1$
      featureLayerBuilder.values(null, this.facade.geometry("LINESTRING ( 10 10, 20 20, 30 40)")); //$NON-NLS-1$
      featureLayerBuilder.values(null, this.facade.geometry("POLYGON ((10 10, 10 20, 20 20, 20 15, 10 10))")); //$NON-NLS-1$
      featureLayerBuilder.values(null, this.facade.geometry("MULTIPOINT (5.0 5.0, 15.0 8.0)")); //$NON-NLS-1$
      featureLayerBuilder.values(
          null,
          this.facade.geometry(
              "MULTILINESTRING ((50.0 50.0, 50.0 150.0, 150.0 150.0, 150.0 50.0, 75.0 75.0, 75.0 125.0, 125.0 125.0, 125.0 75.0), (50.0 50.0, 50.0 150.0, 150.0 150.0, 150.0 50.0, 75.0 75.0, 75.0 125.0, 125.0 125.0, 125.0 75.0))")); //$NON-NLS-1$
      featureLayerBuilder.values(
          null,
          this.facade.geometry(
              "MULTIPOLYGON (((50.0 50.0, 50.0 150.0, 150.0 150.0, 150.0 50.0, 50.0 50.0), (75.0 75.0, 125.0 75.0, 125.0 125.0, 75.0 125.0, 75.0 75.0)), ((175.0 50.0, 175.0 150.0, 200.0 150.0, 200.0 50.0, 175.0 50.0)))")); //$NON-NLS-1$
      featureLayerBuilder.values(
          null,
          this.facade.geometry(
              "GEOMETRYCOLLECTION (POINT (5.0 5.0), LINESTRING (5.0 5.0, 15.0 8.0), POLYGON ((50.0 50.0, 50.0 150.0, 150.0 150.0, 150.0 50.0, 50.0 50.0), (75.0 75.0, 125.0 75.0, 125.0 125.0, 75.0 125.0, 75.0 75.0)))")); //$NON-NLS-1$
      final IFeatureLayer featureLayer = featureLayerBuilder.build();
      this.facade.view().add(featureLayer);

    } catch (final GeometryConversionException exception) {
      throw new InvocationTargetException(exception);
    }
  }
}
