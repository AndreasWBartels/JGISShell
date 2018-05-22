package net.anwiba.script.groovy.sampledata
// Copyright (c) 2015 by Andreas W. Bartels
import java.awt.Color

import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def load = { view, coordinateReferenceSystem, database, tables, scaleRanges, styles ->
  view.coordinateReferenceSystem(coordinateReferenceSystem);
  for (def table : tables) {
    def resource = "${database}?table=${table}&column=geometry"
    def reference = facade.layerReference(resource)
    def id = view.add(table, reference)
    view.scaleRange(id, scaleRanges.next())
    view.style(id, styles.next())
  }
}

def region = facade.variable("region", "Karlsruhe")
def targetSystem = facade.variable("targetSystem", "PROJCS[\"DHDN / 3-degree Gauss-Kruger zone 3\", GEOGCS[\"DHDN\", DATUM[\"Deutsches Hauptdreiecksnetz\", SPHEROID[\"Bessel 1841\", 6377397.155, 299.1528128, AUTHORITY[\"EPSG\",\"7004\"]], TOWGS84[598.1, 73.7, 418.2, 0.202, 0.045, -2.455, 6.7], AUTHORITY[\"EPSG\",\"6314\"]], PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], UNIT[\"degree\", 0.017453292519943295], AXIS[\"Geodetic longitude\", EAST], AXIS[\"Geodetic latitude\", NORTH], AUTHORITY[\"EPSG\",\"4314\"]], PROJECTION[\"Transverse_Mercator\"], PARAMETER[\"central_meridian\", 9.0], PARAMETER[\"latitude_of_origin\", 0.0], PARAMETER[\"scale_factor\", 1.0], PARAMETER[\"false_easting\", 3500000.0], PARAMETER[\"false_northing\", 0.0], UNIT[\"m\", 1.0], AXIS[\"Easting\", EAST], AXIS[\"Northing\", NORTH], AUTHORITY[\"EPSG\",\"31467\"]]")
def database = "sqlite:spatialite://\$SYSTEM{jgisshell.workingpath}/data/osm/${region}/${region}.osm.sqlite"

def landuseStyle = facade.featureStyleBuilder(facade.multipolygon())
    .addConditionalStyle("water works",facade.equal(facade.reference("type"), facade.value("water_works")), facade.areaStyle(Color.BLACK, Color.BLUE.brighter()))
    .addConditionalStyle("basin", facade.equal(facade.reference("type"), facade.value("basin")), facade.areaStyle(Color.BLACK, Color.BLUE.brighter()))
    .addConditionalStyle("reservoir", facade.equal(facade.reference("type"), facade.value("reservoir")), facade.areaStyle(Color.BLACK, Color.BLUE.brighter()))
    .addConditionalStyle("commercial", facade.equal(facade.reference("type"), facade.value("commercial")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle("railway", facade.equal(facade.reference("type"), facade.value("railway")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle("plaza", facade.equal(facade.reference("type"), facade.value("plaza")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle("place", facade.equal(facade.reference("type"), facade.value("place")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle("highway", facade.equal(facade.reference("type"), facade.value("highway")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle("traffic island", facade.equal(facade.reference("type"), facade.value("traffic_island")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle("garages", facade.equal(facade.reference("type"), facade.value("garages")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle("garagegr", facade.equal(facade.reference("type"), facade.value("garagegr")), facade.areaStyle(Color.LIGHT_GRAY, Color.LIGHT_GRAY))
    .addConditionalStyle("flowerbed", facade.equal(facade.reference("type"), facade.value("flowerbed")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("nursery", facade.equal(facade.reference("type"), facade.value("nursery")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("plant nursery", facade.equal(facade.reference("type"), facade.value("plant_nursery")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("greenhouse horticulture", facade.equal(facade.reference("type"), facade.value("greenhouse_horti")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("wetland", facade.equal(facade.reference("type"), facade.value("wetland")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("winter sports", facade.equal(facade.reference("type"), facade.value("winter_sports")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("fishfarm", facade.equal(facade.reference("type"), facade.value("fishfarm")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("brownfield", facade.equal(facade.reference("type"), facade.value("brownfield")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("greenfield", facade.equal(facade.reference("type"), facade.value("greenfield")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("farm", facade.equal(facade.reference("type"), facade.value("farm")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("vineyard", facade.equal(facade.reference("type"), facade.value("vineyard")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("farmyard", facade.equal(facade.reference("type"), facade.value("farmyard")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("farmland", facade.equal(facade.reference("type"), facade.value("farmland")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("village green", facade.equal(facade.reference("type"), facade.value("village_green")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("landfill", facade.equal(facade.reference("type"), facade.value("landfill")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("recreation ground", facade.equal(facade.reference("type"), facade.value("recreation_groun")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("cemetery", facade.equal(facade.reference("type"), facade.value("cemetery")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("grass", facade.equal(facade.reference("type"), facade.value("grass")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("winery", facade.equal(facade.reference("type"), facade.value("winery")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("animal keeping", facade.equal(facade.reference("type"), facade.value("animal_keeping")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .addConditionalStyle("area", facade.equal(facade.reference("type"), facade.value("area")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter().brighter()))
    .defaultStyle(facade.areaStyle(Color.BLACK, Color.YELLOW))
    .build()

def naturalStyle = facade.featureStyleBuilder(facade.multipolygon())
    .addConditionalStyle("water", facade.equal(facade.reference("type"), facade.value("water")), facade.areaStyle(Color.BLACK, Color.BLUE))
    .addConditionalStyle("riverbank", facade.equal(facade.reference("type"), facade.value("riverbank")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter()))
    .addConditionalStyle("forest",  facade.equal(facade.reference("type"), facade.value("forest")), facade.areaStyle(Color.BLACK, Color.GREEN.darker()))
    .defaultStyle(facade.areaStyle(Color.BLACK, Color.GREEN))
    .build()

def waterwaysStyle = facade.featureStyleBuilder(facade.multilinestring())
    .addConditionalStyle("ditch", facade.or(facade.equal(facade.reference("type"), facade.value("ditch")),
    facade.equal(facade.reference("type"), facade.value("ditc"))),
    facade.lineStyle(Color.BLUE,1))
    .addConditionalStyle("basin", facade.or(facade.equal(facade.reference("type"), facade.value("basin")),
    facade.equal(facade.reference("type"), facade.value("bassin"))),
    facade.lineStyle(Color.BLUE,2))
    .addConditionalStyle("canal", facade.equal(facade.reference("type"), facade.value("river")), facade.lineStyle(Color.BLUE,2))
    .addConditionalStyle("drain", facade.equal(facade.reference("type"), facade.value("drain")), facade.lineStyle(Color.BLUE,2))
    .addConditionalStyle("stream", facade.equal(facade.reference("type"), facade.value("stream")), facade.lineStyle(Color.BLUE,3))
    .addConditionalStyle("river", facade.equal(facade.reference("type"), facade.value("river")), facade.lineStyle(Color.BLUE,4))
    .addConditionalStyle("dam", facade.equal(facade.reference("type"), facade.value("dam")), facade.lineStyle(Color.ORANGE,2))
    .addConditionalStyle("lock", facade.or(facade.equal(facade.reference("type"), facade.value("lock_gate")),
    facade.equal(facade.reference("type"), facade.value("lock"))),
    facade.lineStyle(Color.BLACK,1))
    .addConditionalStyle("weir", facade.equal(facade.reference("type"), facade.value("weir")), facade.lineStyle(Color.BLACK,2))
    .defaultStyle(facade.lineStyle(Color.BLUE,0.5))
    .build()

def locator = facade.locator()
locator.clear()
load(locator
    , facade.coordinateReferenceSystem("EPSG",3857)
    , database
    , ["natural_1", "waterways"] //
    , [facade.scaleRange(), facade.scaleRange()].iterator() //
    , [naturalStyle, facade.lineStyle(Color.BLUE,0.5)].iterator()
    )

locator.envelope(locator.worldBox())
locator.add(0,"Streets", facade.layerReference("osm:http://{identifier}.tiles.wmflabs.org/osm/{zoomLevel}/{column}/{row}.png?identifiers=a,b,c"))

def view = facade.view()
view.clear()
view.scale(1/10000)
load(view
    , facade.coordinateReferenceSystem("EPSG",3857)
    , database
    , ["landuse", "natural_1", "waterways", "buildings", "railways", "roads"] //
    , [
      facade.scaleRange(),
      facade.scaleRange(),
      facade.scaleRange(),
      facade.scaleRange(1d/25000d,1d/500d),
      facade.scaleRange(),
      facade.scaleRange()
    ].iterator() //
    , [
      landuseStyle,
      naturalStyle,
      waterwaysStyle,
      facade.areaStyle(Color.BLACK, Color.RED),
      facade.lineStyle(Color.BLACK,2),
      facade.lineStyle(Color.BLACK,2)
    ].iterator()
    )
view.center(view.worldBox().getCenterCoordinate())
view.add(0,"Streets", facade.layerReference("osm:http://{identifier}.tiles.wmflabs.org/osm/{zoomLevel}/{column}/{row}.png?identifiers=a,b,c"))
