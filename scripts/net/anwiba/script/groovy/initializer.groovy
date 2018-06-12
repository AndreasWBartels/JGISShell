// Copyright (c) 2015 by Andreas W. Bartels
package net.anwiba.script.groovy
import java.util.concurrent.TimeUnit

import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def epsg3857 = facade.coordinateReferenceSystem("EPSG",3857);
def targetSystem =  facade.coordinateReferenceSystem("EPSG",25832);

def region = "Kassel"
facade.addVariable("region", region)
facade.addVariable("targetSystem", targetSystem);

facade.view().coordinateReferenceSystem(epsg3857)

if (layermanager) {
  def dataFolder = "\$SYSTEM{jgisshell.workingpath}/data"
  if (exists(resource(dataFolder))) {
    layermanager.open(facade.dataStoreReference(dataFolder))
  }
  def targetFileName = "\$SYSTEM{jgisshell.workingpath}/data/osm/${region}/${region}.osm.sqlite"
  if (exists(resource(targetFileName))) {
    layermanager.open(facade.dataStoreReference("sqlite:spatialite://${targetFileName}"))
  }
}

facade.processLauncher()
    .description("backup")
    .delay(1)
    .isPeriodic(true)
    .timeUnit(TimeUnit.MINUTES)
    .closure( { def monitor, def canceler ->
      def resource = facade.resource("\$SYSTEM{jgisshell.workingpath}/data/backup.map")
      def map = facade.view().map()
      if (!map.isEmpty()) facade.write(map, resource)
    })
    .launch()
