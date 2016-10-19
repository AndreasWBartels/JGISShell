// Copyright (c) 2016 by Andreas W. Bartels (bartels@anwiba.de)
package net.anwiba.script.groovy
import net.anwiba.scripting.api.groovy.JGISShellGroovyScript;
@groovy.transform.BaseScript JGISShellGroovyScript facade

def point = facade.point(coordinateReferenceSystem,coordinate);
geometryReceiver.setGeometry(point)