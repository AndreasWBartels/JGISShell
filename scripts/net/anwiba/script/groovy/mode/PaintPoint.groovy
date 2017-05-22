// Copyright (c) 2016 by Andreas W. Bartels (bartels@anwiba.de)
package net.anwiba.script.groovy.mode
import net.anwiba.spatial.coordinate.ICoordinate
import net.anwiba.spatial.scripting.ScriptBinding
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def point = facade.point(coordinateReferenceSystem,coordinate);
//println buttonNumber
//println clickCount
//println isAltDown
//println isAltGraphDown
//println isControlDown
//println isMetaDown
//println isShiftDown

geometryReceiver.setGeometry(point)