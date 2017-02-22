// Copyright (c) 2016 by Andreas W. Bartels (bartels@anwiba.de)
package net.anwiba.script.groovy
import net.anwiba.gis.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def point = facade.point(coordinateReferenceSystem,coordinate);
geometryReceiver.setGeometry(point)