package net.anwiba.script.groovy.sampledata
// Copyright (c) 2015 by Andreas W. Bartels
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def region = facade.variable("region", "Karlsruhe")
def sourceSystem = facade.coordinateReferenceSystem("GEOGCS[\"WGS 84\", DATUM[\"World Geodetic System 1984\", SPHEROID[\"WGS 84\", 6378137.0, 298.257223563, AUTHORITY[\"EPSG\",\"7030\"]], AUTHORITY[\"EPSG\",\"6326\"]], PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], UNIT[\"degree\", 0.017453292519943295], AXIS[\"Geodetic longitude\", EAST], AXIS[\"Geodetic latitude\", NORTH], AUTHORITY[\"EPSG\",\"4326\"]]");
def targetSystem = facade.variable("targetSystem", facade.coordinateReferenceSystem("PROJCS[\"DHDN / 3-degree Gauss-Kruger zone 3\", GEOGCS[\"DHDN\", DATUM[\"Deutsches Hauptdreiecksnetz\", SPHEROID[\"Bessel 1841\", 6377397.155, 299.1528128, AUTHORITY[\"EPSG\",\"7004\"]], TOWGS84[598.1, 73.7, 418.2, 0.202, 0.045, -2.455, 6.7], AUTHORITY[\"EPSG\",\"6314\"]], PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], UNIT[\"degree\", 0.017453292519943295], AXIS[\"Geodetic longitude\", EAST], AXIS[\"Geodetic latitude\", NORTH], AUTHORITY[\"EPSG\",\"4314\"]], PROJECTION[\"Transverse_Mercator\"], PARAMETER[\"central_meridian\", 9.0], PARAMETER[\"latitude_of_origin\", 0.0], PARAMETER[\"scale_factor\", 1.0], PARAMETER[\"false_easting\", 3500000.0], PARAMETER[\"false_northing\", 0.0], UNIT[\"m\", 1.0], AXIS[\"Easting\", EAST], AXIS[\"Northing\", NORTH], AUTHORITY[\"EPSG\",\"31467\"]]"));

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
  monitor.setNote("Copy region 'region '${region}'' shapefile '" + layer.name() + "' in table  '${table}'")
  facade.layerCopier(sourceReference, facade.layerReference(targetLayerReferenceUrn))
      .sourceSystem(sourceSystem)
      .targetSystem(targetSystem)
      .copy(monitor, canceler);
}

if (layermanager) {
  layermanager.open(facade.dataStoreReference(targetResourceUrn))
}
