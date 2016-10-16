package net.anwiba.script.groovy
// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)

import net.anwiba.scripting.api.groovy.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def database = "sqlite:spatialite://\$SYSTEM{jgisshell.workingpath}/data/osm/Karlsruhe/Karlsruhe.osm.sqlite"
def table = "landuse"
def resource = "${database}?table=${table}&column=geometry"
def reference = facade.layerReference(resource)

def layerViewIdentifier = facade.map().add(table, reference)
facade.addVariable("layerViewName", table)
facade.addVariable("layerViewIdentifier", layerViewIdentifier)

def index = -1
facade.addVariable("layerSliderIndex", index)

def values = [
  "grass",
  "cemetery",
  "residential",
  "plaza",
  "greenfield",
  "industrial",
  "recreation_groun",
  "landfill",
  "retail",
  "railway",
  "commercial",
  "village_green",
  "meadow",
  "farmland",
  "allotments",
  "quarry",
  "construction",
  "farm",
  "education",
  "orchard",
  "brownfield",
  "basin",
  "farmyard",
  "vineyard",
  "military",
  "reservoir",
  "greenhouse_horti",
  "ex-military",
  "plant_nursery",
  "water_works",
  "timber_yard",
  "exercise_area",
  "winter_sports",
  "scrub",
  "basin_",
  "garages",
  "nursery",
  "place",
  "wetland",
  "road",
  "highway",
  "traffic_island",
  "test_area",
  "conservation",
  "garagegr",
  "flowerbed",
  "area",
  "animal_keeping",
  "scrubs",
  "pipelinearea",
  "depot",
  "leisure",
  "winery",
  "fishfarm",
  "institutional",
  "outdoor_seating",
  "yes",
  "churchyard",
  "religious",
  "urban_green"
]
facade.addVariable("layerSliderConditionValues", values)


