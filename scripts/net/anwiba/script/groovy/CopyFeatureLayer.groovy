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
package net.anwiba.script.groovy

import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript script

def name = "Bundesländer";
def sourceReference = layerReference("\$SYSTEM{jgisshell.workingpath}data/bundeslaender.shp");
def targetReference = layerReference("\$SYSTEM{java.io.tmpdir}" + File.separator + "bundeslaender.shp");

if (!exists(sourceReference)) {
  warn("Couldn't find source reference '" + toString(sourceReference) + "'");
  return;
}
if (exists(targetReference)) {
  if (!supportsDeleteLayer(targetReference)) {
    warn("Couldn't delete existing target reference '" + toString(targetReference) + "'");
    return;
  }
  delete(targetReference)
}
copy(sourceReference, targetReference);
view().add(name,targetReference);

