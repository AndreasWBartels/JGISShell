package net.anwiba.script.groovy.sampledata
// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def region = facade.variable("region", "Karlsruhe")
def source = resource("http://download.bbbike.org/osm/bbbike/${region}/")
show(source)
