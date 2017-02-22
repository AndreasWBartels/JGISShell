package net.anwiba.script.groovy.sampledata
// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)

import net.anwiba.gis.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

if (!facade.variable("layerViewIdentifier"))  { return }

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