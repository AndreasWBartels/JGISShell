package net.anwiba.script.groovy.sampledata
// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
import net.anwiba.scripting.api.groovy.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def source = resource("http://download.geofabrik.de/europe/germany/baden-wuerttemberg/karlsruhe-regbez.html")
show(source)
