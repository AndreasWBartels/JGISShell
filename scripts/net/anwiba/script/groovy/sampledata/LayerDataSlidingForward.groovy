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

import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

if (!facade.variable("layerViewIdentifier"))  {
  return
}

def next = { layerSliderConditionValues ->
  def layerSliderIndex = facade.variable("layerSliderIndex")
  def index = layerSliderIndex + 1 == layerSliderConditionValues.size() ? 0 : layerSliderIndex + 1
  facade.addVariable("layerSliderIndex", index)
  layerSliderConditionValues.get(index)
}

def layerViewIdentifier = facade.variable("layerViewIdentifier")
def layerName = facade.variable("layerViewName")
def layerSliderConditionValues = facade.variable("layerSliderConditionValues")
def value = next( layerSliderConditionValues)
facade.view().name(layerViewIdentifier, "${layerName} (${value})" )
facade.view().condition(layerViewIdentifier, facade.equal(facade.reference("type"), facade.value(value)))