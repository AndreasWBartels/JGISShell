// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
package net.anwiba.script.groovy.sampledata

import java.awt.Color

import net.anwiba.gis.layer.IImageResolution;
import net.anwiba.gis.layer.ImageResolution;
import net.anwiba.gis.raster.tile.matrix.ITileImageIndex;
import net.anwiba.gis.raster.tile.matrix.ITileNumberCalculator;
import net.anwiba.gis.raster.tile.matrix.PseudoMercatorTileNumberCalculator;
import net.anwiba.gis.raster.tile.matrix.TileImageIndex;
import net.anwiba.gis.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def region = facade.variable("region", "Karlsruhe")
def table = "landuse"
def name = "foo"
def epsg3857 = coordinateReferenceSystem("PROJCS[\"WGS 84 / Pseudo-Mercator\", GEOGCS[\"WGS 84\", DATUM[\"WGS 84\", SPHEROID[\"WGS 84\", 6378137, 298.257223563, AUTHORITY[\"EPSG\",\"7030\"]], TOWGS84[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], AUTHORITY[\"EPSG\",\"6326\"]], PRIMEM[\"Greenwich\", 0, AUTHORITY[\"EPSG\",\"8901\"]], UNIT[\"degree\", 0.01745329251994328, AUTHORITY[\"EPSG\", \"9122\"]], AUTHORITY[\"EPSG\",\"4326\"]], PROJECTION[\"Mercator_1SP\"], PARAMETER[\"central_meridian\",0], PARAMETER[\"scale_factor\",1], PARAMETER[\"false_easting\",0], PARAMETER[\"false_northing\",0], UNIT[\"metre\", 1, AUTHORITY[\"EPSG\",\"9001\"]], AXIS[\"X\",EAST], AXIS[\"Y\",NORTH], AUTHORITY[\"EPSG\",\"3857\"]]")
def satelliteLayerReference = layerReference("mapbox:https://api.mapbox.com?owner=mapbox&layer=streets-satellite&token=sk.eyJ1IjoiYmFydGVscyIsImEiOiJjaW1uOW1heTcwMDFrd3hrcWo4c2JmbTZtIn0.TgKad05borKR9UQhFsMWfw")

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

if (facade.exists(facade.resource(basefolder))) facade.delete(facade.resource(basefolder))

IImageResolution imageResolution =  new ImageResolution(96,256,256);
ITileImageIndex index = new TileImageIndex(16,34279,22485);
ITileNumberCalculator tileNumberCalculator = new PseudoMercatorTileNumberCalculator(imageResolution)
def zoomLevel = index.getZoomLevel()
def columnNumber = index.getColumnNumber()
def rowNumber = index.getRowNumber()
def envelope = tileNumberCalculator.getEnvelope(index)

println "${envelope}  - ${zoomLevel} ${columnNumber} ${rowNumber}"
def map = facade.mapBuilder()
    .add(reference, landuseStyle)
    .add(facade.layerReference("${database}?table=buildings&column=geometry"), facade.areaStyle(Color.BLACK, Color.RED))
    .coordinateReferenceSystem(epsg3857)
    .envelope(envelope)
    .build()

def image = facade.imageBuilder()
    .set(map)
    .dpi(imageResolution.getDpi())
    .width(imageResolution.getWidth())
    .height(imageResolution.getHeight())
    .envelope(envelope)
    .build()

def folder = facade.resource("${basefolder}/${zoomLevel}/${columnNumber}")
if (!facade.exists(folder)) facade.createFolder(folder)
facade.write(image,facade.resource("${basefolder}/${zoomLevel}/${columnNumber}/${rowNumber}.png"))
facade.view().map(map)
facade.view().add("satellite", satelliteLayerReference)
facade.view().add("tile", facade.layerReference("${basefolder}/${zoomLevel}/${columnNumber}/${rowNumber}.pgw"));