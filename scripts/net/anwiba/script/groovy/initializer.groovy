// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
package net.anwiba.script.groovy
import java.util.concurrent.TimeUnit

import net.anwiba.gis.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

// facade.addVariable("region", "Paderborn")
// facade.addVariable("targetSystem", facade.coordinateReferenceSystem("PROJCS[\"DHDN / 3-degree Gauss-Kruger zone 3\", GEOGCS[\"DHDN\", DATUM[\"Deutsches Hauptdreiecksnetz\", SPHEROID[\"Bessel 1841\", 6377397.155, 299.1528128, AUTHORITY[\"EPSG\",\"7004\"]], TOWGS84[598.1,   73.7, 418.2,  0.202,  0.045, -2.455,   6.7], AUTHORITY[\"EPSG\",\"6314\"]], PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], UNIT[\"degree\", 0.017453292519943295], AXIS[\"Geodetic longitude\", EAST], AXIS[\"Geodetic latitude\", NORTH], AUTHORITY[\"EPSG\",\"4314\"]], PROJECTION[\"Transverse_Mercator\"], PARAMETER[\"central_meridian\", 9.0], PARAMETER[\"latitude_of_origin\", 0.0], PARAMETER[\"scale_factor\", 1.0], PARAMETER[\"false_easting\", 3500000.0], PARAMETER[\"false_northing\", 0.0], UNIT[\"m\", 1.0], AXIS[\"Easting\", EAST], AXIS[\"Northing\", NORTH], AUTHORITY[\"EPSG\",\"31467\"]]"));

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
