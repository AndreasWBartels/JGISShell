package net.anwiba.script.groovy.sampledata
// Copyright (c) 2015 by Andreas W. Bartels
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def region = facade.variable("region", "Karlsruhe")
def sourceSystem = facade.coordinateReferenceSystem("EPSG",4326);
def targetSystem = facade.variable("targetSystem", facade.coordinateReferenceSystem("EPSG",25832));

def emtySpatiaLiteDatabaseFileName = "\$SYSTEM{jgisshell.workingpath}/data/template/spatialite-empty-4.n.sqlite"
def sourceResourceUrn = "\$SYSTEM{jgisshell.workingpath}/data/osm/${region}";
def targetFileName = "\$SYSTEM{jgisshell.workingpath}/data/osm/${region}/${region}.osm.sqlite"
def targetResourceUrn = "sqlite:spatialite://${targetFileName}";


def targetResource = facade.resource(targetFileName)
if (facade.exists(targetResource)) {
  facade.delete(targetResource)
}

facade.copy(facade.resource(emtySpatiaLiteDatabaseFileName), targetResource)

def layerReferences = facade.iterable( facade.dataStoreReference(sourceResourceUrn))
for (def sourceReference : layerReferences) {
  if (!facade.isFeatureLayer(sourceReference)) {
    continue
  }
  def layer = facade.read(sourceReference)
  def table = layer.name()
  def targetLayerReferenceUrn = targetResourceUrn + "?table=${table}&column=geometry";
  if (monitor) {
    monitor.setNote("Copy region 'region '${region}'' shapefile '" + layer.name() + "' in table  '${table}'")
  }
  facade.layerCopier(sourceReference, facade.layerReference(targetLayerReferenceUrn))
      .sourceSystem(sourceSystem)
      .targetSystem(targetSystem)
      .copy();
}

if (layermanager) {
  layermanager.open(facade.dataStoreReference(targetResourceUrn))
}