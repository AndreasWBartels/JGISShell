package net.anwiba.script.groovy.sampledata
// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
import net.anwiba.gis.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def region = facade.variable("region", "Karlsruhe")
def source = resource("http://download.bbbike.org/osm/bbbike/${region}/${region}.osm.shp.zip")
def folder = resource("\$SYSTEM{jgisshell.workingpath}/data/osm/${region}")
def target = resource("\$SYSTEM{jgisshell.workingpath}/data/osm/${region}/${region}.osm.shp.zip")

try {
  if (!exists(folder)) createFolder(target)
  if (exists(target)) delete(target)
  monitor.setNote("Download Data")
  copy(source, target)
  monitor.setNote("Extract Shapefiles")
  extract(target, folder)
} catch (Exception e) {
  error("copy osm data faild", e)
}
