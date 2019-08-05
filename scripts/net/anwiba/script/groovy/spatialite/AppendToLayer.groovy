/*
 * #%L
 * jgisshell scripting
 * %%
 * Copyright (C) 2007 - 2019 Andreas W. Bartels
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
package net.anwiba.script.groovy.spatialite
// Copyright (c) 2015 by Andreas W. Bartels
import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def region = facade.variable("region", "Karlsruhe")
def targetFileName = "\$SYSTEM{jgisshell.workingpath}data/spatialite/database.sqlite"
def targetLayerResourceUrn = "sqlite:spatialite://${targetFileName}?table=layer&column=geometry";

def sourceReference = facade.layerReference("\$SYSTEM{jgisshell.workingpath}data/osm/${region}/${region}-shp/shape/places.shp")
def targetReference = facade.layerReference(targetLayerResourceUrn)

facade.layerAppender(sourceReference
    , targetReference
    , facade.nameMappingsBuilder()
    .add("osm_id", "identifier")
    .add("name", "name")
    .add("type", "type")
    .add("population", "count")
    .add("SHAPE", "geometry").build())
    .append()
