/*
 * #%L
 * jgisshell scripting
 * %%
 * Copyright (C) 2007 - 2018 Andreas W. Bartels
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package net.anwiba.script.groovy.sampledata

import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def region = facade.variable("region", "Karlsruhe")
def source = resource("http://download.bbbike.org/osm/bbbike/${region}/${region}.osm.shp.zip")
def folder = resource("\$SYSTEM{jgisshell.workingpath}/data/osm/${region}")
def target = resource("\$SYSTEM{jgisshell.workingpath}/data/osm/${region}/${region}.osm.shp.zip")

try {
  if (!exists(folder)) {
    createFolder(folder)
  }
  //  if (exists(target)) {
  //    delete(target)
  //  }

  if (!exists(target)) {
    if (monitor) {
      monitor.setNote("Download data for region '${region}'")
    }
    resourceConstraint(source, "odbl", "OpenStreetMap");
    copy(source, target)
  }
  if (exists(target)) {
    if (monitor) {
      monitor.setNote("Extract shapefiles of region '${region}'")
    }
    extract(target, folder)
  }
} catch (Exception e) {
  error("copy osm data faild", e)
}
