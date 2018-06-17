package net.anwiba.script.groovy.ckan

import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def url = resource.getUrl()
def sourceResource = facade.resource(url)
def temporaryResource = facade.temporaryResource("foo", ".txt")

facade.copy(sourceResource, temporaryResource)

desktop.open( temporaryResource )
