package net.anwiba.script.groovy
// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
import net.anwiba.scripting.api.groovy.JGISShellGroovyScript;
@groovy.transform.BaseScript JGISShellGroovyScript facade

def resource = facade.resource("\$SYSTEM{jgisshell.workingpath}/data/backup.map")
def map = facade.map().map()
if (!map.isEmpty()) facade.write(map, resource)