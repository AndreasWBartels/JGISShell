// Copyright (c) 2014 by Andreas W. Bartels (bartels@anwiba.net)
package net.anwiba.script.java.example;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import net.anwiba.commons.message.IMessageCollector;
import net.anwiba.commons.process.cancel.ICanceler;
import net.anwiba.commons.reflection.annotation.Injection;
import net.anwiba.commons.reflection.annotation.Nullable;
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
  public void run() throws InterruptedException, InvocationTargetException {
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
