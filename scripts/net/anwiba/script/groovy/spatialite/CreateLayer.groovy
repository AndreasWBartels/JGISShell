package net.anwiba.script.groovy.spatialite
// Copyright (c) 2015 by Andreas W. Bartels
import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def targetFileName = "\$SYSTEM{jgisshell.workingpath}data/spatialite/database.sqlite"
def targetResourceUrn = "sqlite:spatialite://${targetFileName}";


def dataStore = facade.dataStoreReference(targetResourceUrn)

facade.layerCreator(dataStore)
    .name("layer")
    .identifierAttribute("identifier")
    .attribute("name", facade.stringType())
    .attribute("type", facade.stringType())
    .attribute("count", facade.integerType())
    .geometryAttribute("geometry"
    , facade.point()
    , facade.envelope(3525000.00, 5680000.00, 3540000.00, 5690000.00)
    , facade.coordinateReferenceSystem(dataStore, "epsg", 31467))
    .create()