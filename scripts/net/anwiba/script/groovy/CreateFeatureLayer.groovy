package net.anwiba.script.groovy
// Copyright (c) 2015 by Andreas W. Bartels
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facadeScript
def layerBuilder = facade.featureLayerBuilder();
layerBuilder.values(null, facade.geometry("POINT (10 10)")); //$NON-NLS-1$
layerBuilder.values(null, facade.geometry("LINESTRING ( 10 10, 20 20, 30 40)")); //$NON-NLS-1$
layerBuilder.values(null, facade.geometry("POLYGON ((10 10, 10 20, 20 20, 20 15, 10 10))")); //$NON-NLS-1$
layerBuilder.values(null, facade.geometry("MULTIPOINT (5.0 5.0, 15.0 8.0)")); //$NON-NLS-1$
layerBuilder.values(null, facade.geometry("MULTILINESTRING ((50.0 50.0, 50.0 150.0, 150.0 150.0, 150.0 50.0, 75.0 75.0, 75.0 125.0, 125.0 125.0, 125.0 75.0), (50.0 50.0, 50.0 150.0, 150.0 150.0, 150.0 50.0, 75.0 75.0, 75.0 125.0, 125.0 125.0, 125.0 75.0))")); //$NON-NLS-1$
layerBuilder.values(null, facade.geometry("MULTIPOLYGON (((50.0 50.0, 50.0 150.0, 150.0 150.0, 150.0 50.0, 50.0 50.0), (75.0 75.0, 125.0 75.0, 125.0 125.0, 75.0 125.0, 75.0 75.0)), ((175.0 50.0, 175.0 150.0, 200.0 150.0, 200.0 50.0, 175.0 50.0)))")); //$NON-NLS-1$
layerBuilder.values(null, facade.geometry("GEOMETRYCOLLECTION (POINT (5.0 5.0), LINESTRING (5.0 5.0, 15.0 8.0), POLYGON ((50.0 50.0, 50.0 150.0, 150.0 150.0, 150.0 50.0, 50.0 50.0), (75.0 75.0, 125.0 75.0, 125.0 125.0, 75.0 125.0, 75.0 75.0)))")); //$NON-NLS-1$
def layer = layerBuilder.build();
facade.view()
    .add(layer);
