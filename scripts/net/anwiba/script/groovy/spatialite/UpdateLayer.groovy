package net.anwiba.script.groovy.spatialite
// Copyright (c) 2015 by Andreas W. Bartels
import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def region = facade.variable("region", "Karlsruhe")
def targetFileName = "\$SYSTEM{jgisshell.workingpath}data/spatialite/database.sqlite"
def targetLayerResourceUrn = "sqlite:spatialite://${targetFileName}?table=layer&column=geometry";

def sourceReference = facade.layerReference("\$SYSTEM{jgisshell.workingpath}data/osm/${region}/${region}-shp/shape/places.shp")
def targetReference = facade.layerReference(targetLayerResourceUrn)

facade.layerUpdater(sourceReference
                  , targetReference
                  , facade.nameMappingsBuilder()
                          .add("osm_id", "identifier")
                          .add("name", "name")
                          .add("type", "type")
                          .add("population", "count")
                          .add("SHAPE", "geometry").build()
                  , facade.nameMapping("osm_id", "identifier"))
      .update()
