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
package net.anwiba.script.groovy

import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
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
