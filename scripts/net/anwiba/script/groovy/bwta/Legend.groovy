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
package net.anwiba.script.groovy.bwta

import java.awt.Color

import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facadeScript

def layerBuilder = facade.featureLayerBuilder();
layerBuilder.identifierAttribute("identifier")
layerBuilder.geometryAttribute("geometry", polygon(), 2, false, null)
layerBuilder.attribute("value", integerType())

def styleBuilder = facade.featureStyleBuilder(facade.polygon())

for (i in 0..39) {

  def x1 = 10 + 1 * i;
  def y1 = 10;
  def x2 = 10 + 1 * i + 1;
  def y2 = 18;

  layerBuilder.values(null, facade.polygon(null, envelope(x1, y1, x2, y2) ), i); //$NON-NLS-1$
  if (i < 40) {
    def Color color = new Color(255 ,255 - (int)((128 / 40) * i), 255 - (int) ((255 / 40) * i));
    styleBuilder.addConditionalStyle( i.toString() , facade.equal(facade.reference("value"), facade.value(i)), facade.areaStyle(color, color))
  }
}
def layer = layerBuilder.build();
def id = facade.view().add(layer)
facade.view().style(id, styleBuilder.defaultStyle(facade.areaStyle(Color.RED, Color.RED)).build())