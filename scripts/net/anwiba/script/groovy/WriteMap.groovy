package net.anwiba.script.groovy
// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def resource = facade.resource("\$SYSTEM{jgisshell.workingpath}/data/backup.map")
def map = facade.view().map()
if (!map.isEmpty()) facade.write(map, resource)