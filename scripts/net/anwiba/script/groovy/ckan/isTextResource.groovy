package net.anwiba.script.groovy.ckan

import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def url = resource.getUrl()
def format = resource.getFormat()

def supportedDocument = format && "txt".equals(format.trim().toLowerCase())
def supportedExtentsion = url && url.toLowerCase().endsWith(".txt")

supportedDocument && supportedExtentsion