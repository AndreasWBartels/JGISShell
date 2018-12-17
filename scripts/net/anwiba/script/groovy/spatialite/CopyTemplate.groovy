package net.anwiba.script.groovy.spatialite
// Copyright (c) 2015 by Andreas W. Bartels
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def emtySpatiaLiteDatabaseFileName = "\$SYSTEM{jgisshell.workingpath}/data/template/spatialite-empty-4.n.sqlite"
def targetFileName = "\$SYSTEM{jgisshell.workingpath}/data/spatialite/database.sqlite"
def targetResourceUrn = "sqlite:spatialite://${targetFileName}";


def targetResource = facade.resource(targetFileName)
if (facade.exists(targetResource)) {
  facade.delete(targetResource)
}
facade.copy(facade.resource(emtySpatiaLiteDatabaseFileName), targetResource)
