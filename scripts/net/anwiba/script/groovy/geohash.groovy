package net.anwiba.script.groovy
// Copyright (c) 2015 by Andreas W. Bartels
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facadeScript
def epsg31467 = facade.coordinateReferenceSystem("PROJCS[\"DHDN / 3-degree Gauss-Kruger zone 3\", GEOGCS[\"DHDN\", DATUM[\"Deutsches Hauptdreiecksnetz\", SPHEROID[\"Bessel 1841\", 6377397.155, 299.1528128, AUTHORITY[\"EPSG\",\"7004\"]], TOWGS84[598.1, 73.7, 418.2, 0.202, 0.045, -2.455, 6.7], AUTHORITY[\"EPSG\",\"6314\"]], PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], UNIT[\"degree\", 0.017453292519943295], AXIS[\"Geodetic longitude\", EAST], AXIS[\"Geodetic latitude\", NORTH], AUTHORITY[\"EPSG\",\"4314\"]], PROJECTION[\"Transverse_Mercator\"], PARAMETER[\"central_meridian\", 9.0], PARAMETER[\"latitude_of_origin\", 0.0], PARAMETER[\"scale_factor\", 1.0], PARAMETER[\"false_easting\", 3500000.0], PARAMETER[\"false_northing\", 0.0], UNIT[\"m\", 1.0], AXIS[\"Easting\", EAST], AXIS[\"Northing\", NORTH], AUTHORITY[\"EPSG\",\"31467\"]]");
def hash = facade.geohash(facade.point(epsg31467,3448808.78, 5418135.71),9);

println(hash);
def point = facade.geohash(hash)
println(facade.wkt(point));

println(facade.wkt(facade.transform(point ,epsg31467)));
