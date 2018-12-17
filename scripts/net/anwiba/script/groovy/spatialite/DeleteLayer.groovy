package net.anwiba.script.groovy.spatialite
// Copyright (c) 2015 by Andreas W. Bartels
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def targetFileName = "\$SYSTEM{jgisshell.workingpath}/data/spatialite/database.sqlite"
def targetLayerResourceUrn = "sqlite:spatialite://${targetFileName}?table=layer&column=geometry";

def reference = facade.layerReference(targetLayerResourceUrn)

facade.delete(reference)
