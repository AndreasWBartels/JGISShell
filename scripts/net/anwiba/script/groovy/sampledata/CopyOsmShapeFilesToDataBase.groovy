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
package net.anwiba.script.groovy.sampledata

import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def region = facade.variable("region", "Karlsruhe")
def sourceSystem = facade.coordinateReferenceSystem("EPSG",4326);
def targetSystem = facade.variable("targetSystem", facade.coordinateReferenceSystem("EPSG",25832));

def emtySpatiaLiteDatabaseFileName = "\$SYSTEM{jgisshell.workingpath}data/template/spatialite-empty-4.n.sqlite"
def sourceResourceUrn = "\$SYSTEM{jgisshell.workingpath}data/osm/${region}";
def targetFileName = "\$SYSTEM{jgisshell.workingpath}data/osm/${region}/${region}.osm.sqlite"
def targetResourceUrn = "sqlite:spatialite://${targetFileName}";


def targetResource = facade.resource(targetFileName)
if (facade.exists(targetResource)) {
  facade.delete(targetResource)
}

facade.copy(facade.resource(emtySpatiaLiteDatabaseFileName), targetResource)

def layerReferences = facade.layerReferences( facade.dataStoreReference(sourceResourceUrn))
for (def sourceReference : layerReferences) {
  if (!facade.isFeatureLayer(sourceReference)) {
    continue
  }
  def layer = facade.read(sourceReference)
  def table = layer.name()
  def targetLayerReferenceUrn = targetResourceUrn + "?table=${table}&column=geometry";
  if (monitor) {
    monitor.setNote("Copy region 'region '${region}'' shapefile '" + layer.name() + "' in table  '${table}'")
  }
  facade.layerCopier(sourceReference, facade.layerReference(targetLayerReferenceUrn))
      .sourceSystem(sourceSystem)
      .targetSystem(targetSystem)
      .copy();
}

if (layermanager) {
  layermanager.open(facade.dataStoreReference(targetResourceUrn))
}