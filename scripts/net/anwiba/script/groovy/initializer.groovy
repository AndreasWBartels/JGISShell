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
import java.util.concurrent.TimeUnit

import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def epsg3857 = facade.coordinateReferenceSystem("EPSG",3857);
def targetSystem =  facade.coordinateReferenceSystem("EPSG",3857);

def region = "Kassel"
facade.addVariable("region", region)
facade.addVariable("targetSystem", targetSystem);

facade.addVariable("dba", "SYS")
facade.addVariable("password", "GEO")
facade.addVariable("database", "localhost:1521/GEODATA.localdomain")

facade.view().coordinateReferenceSystem(epsg3857)

if (layermanager) {
  def dataFolder = "\$SYSTEM{jgisshell.workingpath}data"
  if (exists(resource(dataFolder))) {
    layermanager.open(facade.dataStoreReference(dataFolder))
  }
  def targetFileName = "\$SYSTEM{jgisshell.workingpath}data/osm/${region}/${region}.osm.sqlite"
  if (exists(resource(targetFileName))) {
    layermanager.open(facade.dataStoreReference("sqlite:spatialite://${targetFileName}"))
  }
}

def targetFileName = "\$SYSTEM{jgisshell.workingpath}data/osm/${region}/${region}.osm.sqlite"

facade.processLauncher()
    .description("backup")
    .delay(1)
    .isPeriodic(true)
    .timeUnit(TimeUnit.MINUTES)
    .closure( { def monitor, def canceler ->
      def resource = facade.resource("\$SYSTEM{jgisshell.workingpath}data/backup.map")
      def map = facade.view().map()
      if (!map.isEmpty()) facade.write(map, resource)
    })
    .launch()