// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
package net.anwiba.script.groovy.sampledata

import java.awt.Color
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

import net.anwiba.gis.raster.tile.matrix.ITileImageIndex;
import net.anwiba.gis.raster.tile.matrix.ITileIndexIterable;
import net.anwiba.gis.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def region = facade.variable("region", "Karlsruhe")
def table = "landuse"
def name = "test"
def epsg3857 = coordinateReferenceSystem("PROJCS[\"WGS 84 / Pseudo-Mercator\", GEOGCS[\"WGS 84\", DATUM[\"WGS 84\", SPHEROID[\"WGS 84\", 6378137, 298.257223563, AUTHORITY[\"EPSG\",\"7030\"]], TOWGS84[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], AUTHORITY[\"EPSG\",\"6326\"]], PRIMEM[\"Greenwich\", 0, AUTHORITY[\"EPSG\",\"8901\"]], UNIT[\"degree\", 0.01745329251994328, AUTHORITY[\"EPSG\", \"9122\"]], AUTHORITY[\"EPSG\",\"4326\"]], PROJECTION[\"Mercator_1SP\"], PARAMETER[\"central_meridian\",0], PARAMETER[\"scale_factor\",1], PARAMETER[\"false_easting\",0], PARAMETER[\"false_northing\",0], UNIT[\"metre\", 1, AUTHORITY[\"EPSG\",\"9001\"]], AXIS[\"X\",EAST], AXIS[\"Y\",NORTH], AUTHORITY[\"EPSG\",\"3857\"]]")

def database = "sqlite:spatialite://\$SYSTEM{jgisshell.workingpath}data/osm/${region}/${region}.osm.sqlite"
def resource = "${database}?table=${table}&column=geometry"
def basefolder = "\$SYSTEM{jgisshell.workingpath}data/osm/${region}/tilecache/${name}"

def landuseStyle = facade.featureStyleBuilder(facade.multipolygon())
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("water_works")), facade.areaStyle(Color.BLACK, Color.BLUE.brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("basin")), facade.areaStyle(Color.BLACK, Color.BLUE.brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("reservoir")), facade.areaStyle(Color.BLACK, Color.BLUE.brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("commercial")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("railway")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("plaza")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("place")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("highway")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("traffic_island")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("garages")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("garagegr")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("flowerbed")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("nursery")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("plant_nursery")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("greenhouse_horti")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("wetland")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("winter_sports")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("fishfarm")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("brownfield")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("greenfield")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("farm")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("vineyard")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("farmyard")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("farmland")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("village_green")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("landfill")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("recreation_groun")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("cemetery")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("grass")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("winery")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("animal_keeping")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("area")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .defaultStyle(facade.areaStyle(Color.BLACK, Color.YELLOW))
    .build()

def reference = facade.layerReference(resource)
def map = facade.mapBuilder()
    .add(reference, landuseStyle)
    .add(facade.layerReference("${database}?table=buildings&column=geometry"), facade.areaStyle(Color.BLACK, Color.RED))
    .coordinateReferenceSystem(epsg3857)
    .build()

facade.view().map(map)

ITileIndexIterable iterable = facade
    .tileIndexIterableBuilder()
    .envelope(map.envelope())
    .minimumZoomLevel(10)
    .maximumZoomLevel(12)
    .build()

if (facade.exists(facade.resource(basefolder))) facade.delete(facade.resource(basefolder))




def closure = {ITileImageIndex index ->

  def zoomLevel = index.getZoomLevel()
  def columnNumber = index.getColumnNumber()
  def rowNumber = index.getRowNumber()
  def envelope = iterable.getEnvelope(index)

  println "${envelope}  - ${zoomLevel} ${columnNumber} ${rowNumber}"

  def image = facade.imageBuilder()
      .set(map)
      .width(iterable.getTileWidth())
      .height(iterable.getTileHeight())
      .height(iterable.getDpi())
      .envelope(envelope)
      .build()

  def folder = facade.resource("${basefolder}/${zoomLevel}/${columnNumber}")
  if (!facade.exists(folder)) facade.createFolder(folder)

  facade.write(image,facade.resource("${basefolder}/${zoomLevel}/${columnNumber}/${rowNumber}.png"))

}

def threadPool = Executors.newFixedThreadPool(4)
try {
  List<Future> futures = iterable.collect{index ->
    threadPool.submit({-> closure index } as Callable);
  }
  futures.each{it.get()}
}finally {
  threadPool.shutdown()
}
// osm:file:/home/andreas/work/JGISShell/development/trunk/workspace/gis/jgisshell/Gis_Swing_MapViewer/data/osm/Karlsruhe/tilecache/landuse/{zoomLevel}/{column}/{row}.png?minimumZoomLevel=10&maximumZoomLevel=16
facade.view()
    .add("${name}", facade.layerReference("osm:${basefolder}/{zoomLevel}/{column}/{row}.png?minimumZoomLevel=10&maximumZoomLevel=15"))
