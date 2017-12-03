package net.anwiba.script.groovy.sampledata
// Copyright (c) 2015 by Andreas W. Bartels
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def region = facade.variable("region", "Karlsruhe")
show(resource("http://download.bbbike.org/osm/bbbike/${region}/"))
