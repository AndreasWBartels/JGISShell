package net.anwiba.script.groovy
// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
import java.awt.Color

import net.anwiba.scripting.api.groovy.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def load = { map, database, tables, styles ->
  map.clear();
  for (def table : tables) {
    def resource = "${database}?table=${table}&column=geometry"
    def reference = facade.layerReference(resource)
    def id = map.add(table, reference)
    map.style(id, styles.next())
  }
}

def database = "sqlite:spatialite://\$SYSTEM{jgisshell.workingpath}/data/osm/Karlsruhe/Karlsruhe.osm.sqlite"

def landuseStyle = facade.featureStyleBuilder(facade.multipolygon())
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("water_works")), facade.areaStyle(Color.YELLOW.brighter(), Color.YELLOW.brighter()))
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

def naturalStyle = facade.featureStyleBuilder(facade.multipolygon())
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("water")), facade.areaStyle(Color.BLACK, Color.BLUE))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("riverbank")), facade.areaStyle(Color.BLACK, Color.GREEN.brighter()))
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("forest")), facade.areaStyle(Color.BLACK, Color.GREEN.darker()))
    .defaultStyle(facade.areaStyle(Color.BLACK, Color.GREEN))
    .build()

def waterwaysStyle = facade.featureStyleBuilder(facade.multilinestring())
    .addConditionalStyle(facade.equal(facade.reference("type"), facade.value("river")), facade.lineStyle(Color.BLUE,2))
    .defaultStyle(facade.lineStyle(Color.BLUE,0.5))
    .build()

load(facade.locator()
    , database
    , ["natural_1", "waterways"], [naturalStyle, waterwaysStyle].iterator()
    )
def map = facade.map()
load(map
    , database
    , ["landuse", "natural_1", "waterways", "buildings", "railways", "roads"] //
    , [
      landuseStyle,
      naturalStyle,
      waterwaysStyle,
      facade.areaStyle(Color.BLACK, Color.RED),
      facade.lineStyle(Color.BLACK,2),
      facade.lineStyle(Color.BLACK,2)
    ].iterator()
    )
map.scale(1/10000);
