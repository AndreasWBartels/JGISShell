package net.anwiba.script.groovy.sampledata
// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
import net.anwiba.scripting.api.groovy.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def source = resource("http://download.geofabrik.de/europe/germany/baden-wuerttemberg-latest-free.shp.zip")
def folder = resource("\$SYSTEM{jgisshell.workingpath}/data/osm")
def target = resource("\$SYSTEM{jgisshell.workingpath}/data/osm/baden-wuerttemberg-latest-free.shp.zip")

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
