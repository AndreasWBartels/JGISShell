/*
 * #%L
 * jgisshell scripting
 * %%
 * Copyright (C) 2007 - 2018 Andreas W. Bartels
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package net.anwiba.script.groovy.bwta

import java.awt.Color

import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def epsg4314 = facade.coordinateReferenceSystem("EPSG",4314);
def epsg4258 = facade.coordinateReferenceSystem("EPSG",4258);
def epsg31467 = facade.coordinateReferenceSystem("EPSG",31467);
def epsg25832 = facade.coordinateReferenceSystem("EPSG",25832);

def extractGeometry = { file ->
  def iterator = entities(layerReference(file));
  def geometry = null
  for (def feature : iterator) {
    geometry = feature.geometry()
    if (geometry == null) {
      continue;
    }
    break
  }
  iterator.close()
  geometry
}

def createLayer = { name, description, envelope, geometry, transform ->

  def geometryOperator = operator(geometry)
      .transform(epsg31467)
      .buffer(1000)

  def epsg3147toEpsg4314Transformer = transformer(epsg31467 ,epsg4314);
  def epsg4314toEpsg4258Bwta2017Transformer = transformer(resource("\$SYSTEM{jgisshell.workingpath}data/bwta/ntv2/BWTA2017.gsb"), forward())
  def epsg4258toEpsg25832Transformer = transformer(epsg4258 ,epsg25832);

  def builder = featureLayerBuilder()
  builder.name(name)
  builder.identifierAttribute("IDENTIFIER")
  builder.attribute("DATUMSHIFT", integerType())
  builder.attribute("POINT", integerType())
  builder.attribute("DISTANCE", doubleType())
  builder.attribute("X", doubleType())
  builder.attribute("Y", doubleType())
  builder.attribute("NAME", stringType())
  builder.attribute("DESCRIPTION", stringType())
  builder.geometryAttribute("GEOMETRY",facade.point(),2,false,epsg25832)

  int point_counter = 0;
  int exeption_counter = 0;
  long duration = 0;

  def stepSize = 1000

  def maximum = 0d
  def minimum = Double.MAX_VALUE
  def average = 0d


  def start = System.nanoTime();
  for (def x = (envelope.getMinimum().getXValue() + (stepSize/2)); x < envelope.getMaximum().getXValue(); x+=stepSize) {
    for (def y = (envelope.getMinimum().getYValue() + (stepSize/2)); y < envelope.getMaximum().getYValue(); y+=stepSize) {
      def epsg31467Coordinate = facade.coordinate(x, y)

      if (!geometryOperator.intersects(epsg31467Coordinate)) {
        continue;
      }

      try {

        def epsg4314Coordinate = epsg3147toEpsg4314Transformer.transform(epsg31467Coordinate)
        def epsg4258Coordinate = transform(epsg4314Coordinate)
        def epsg25832Coordinate = epsg4258toEpsg25832Transformer.transform(epsg4258Coordinate)

        def epsg4258wtaCoordinate = epsg4314toEpsg4258Bwta2017Transformer.transform(epsg4314Coordinate)
        def epsg25832BwtaCoordinate = epsg4258toEpsg25832Transformer.transform(epsg4258wtaCoordinate)

        def difference = facade.length(epsg25832BwtaCoordinate, epsg25832Coordinate)
        builder.values(null, 0, point_counter, difference, epsg31467Coordinate.getXValue(), epsg31467Coordinate.getYValue(), name, description, facade.point(epsg25832, epsg25832Coordinate))

        minimum = Math.min(minimum, difference)
        maximum = Math.max(maximum, difference)
        average = (average + difference)

        point_counter++
      } catch (Exception e) {
        exeption_counter++;
      }
    }
  }
  epsg4314toEpsg4258Bwta2017Transformer.close()
  duration = duration + (System.nanoTime() - start);

  println "number of points: " + point_counter
  println "number of exceptions: " + exeption_counter
  println "duration: " + duration

  println "minimum: " + minimum
  println "average: " + (average / point_counter)
  println "maximum: " + maximum

  builder.build();
}

def createMap = { layer, coordinateReferenceSystem, startValue, endValue, stepSize ->
  def distances = [:]
  def iterator;
  try {
    iterator = entities(layer);
    for (def feature : iterator) {
      def distance = feature.value("DISTANCE").round(2)
      def key = ((distance / stepSize).round(0) * stepSize).round(2)
      if (!distances.containsKey(key)) {
        distances.put(key, 0)
      }
      distances.put(key, distances.get(key) + 1)
    }
  } finally {
    iterator.close()
  }

  createFile(resource("\$SYSTEM{jgisshell.workingpath}data/bwta/${layer.name()}-${startValue}-${endValue}-${stepSize}.cvs")).withWriter('utf-8') {  writer ->
    writer.writeLine "\"distance\", \"count\";"
    for (d in distances) {
      writer.writeLine "${d.getKey()}, ${d.getValue()};"
    }
  }

  def gridstyle = gridStyle(
      startValue.doubleValue(),
      endValue.doubleValue(),
      Color.WHITE,
      new Color(255,128,0),
      Color.RED,
      ((endValue-startValue) / stepSize).intValue() )

  def mapBuilder = facade.mapBuilder()
  mapBuilder.coordinateReferenceSystem(coordinateReferenceSystem)
  mapBuilder.add("${layer.name()}", layerReference("\$SYSTEM{jgisshell.workingpath}data/bwta/${layer.name()}.xyz"), gridstyle);
  mapBuilder.add("Grenzen", layerReference("\$SYSTEM{jgisshell.workingpath}data/bwta/border/AX_Gebiet_Bundesland.shp"), areaStyle(Color.BLACK))
  def map = mapBuilder.build()
  view().map(map)

  def image = imageBuilder().fit().height(750).width(600).set(map).build()
  facade.write(image,resource("\$SYSTEM{jgisshell.workingpath}data/bwta/${layer.name()}-${startValue}-${endValue}-${stepSize}.png"));

  facade.write(map, resource("\$SYSTEM{jgisshell.workingpath}data/bwta/${layer.name()}-${startValue}-${endValue}-${stepSize}.map"))
}

def createXYZFile = { layer, coordinateReferenceSystem ->

  createFile(resource("\$SYSTEM{jgisshell.workingpath}data/bwta/${layer.name()}.xyz")).withWriter('utf-8') {  writer ->
    def iterator;
    try {
      iterator = entities(layer);
      for (def feature : iterator) {
        def distance = feature.value("DISTANCE");
        def x = feature.value("X");
        def y = feature.value("Y");
        writer.writeLine "${x} ${y} ${distance}"
      }
    } finally {
      iterator.close()
    }
  }
  createFile(resource("\$SYSTEM{jgisshell.workingpath}data/bwta/${layer.name()}.prj")).withWriter('utf-8') {  writer ->
    writer.writeLine wkt(coordinateReferenceSystem)
  }
}

def name = "DHDN-SOUTH"
def description = "helmert transformation"

def envelope = envelope(3387500, 5265000, 3612000, 5520000)
def geometry = extractGeometry("\$SYSTEM{jgisshell.workingpath}data/bwta/border/AX_Gebiet_Bundesland.shp")

def toGeocentricTransformer = facade.toGeocentricTransformer(epsg4314);
def geocentricTransformer = transformer(597.1,   71.4, 412.1,  0.894,  0.068, -1.563,  7.58)
def fromGeocentricTransformer = facade.fromGeocentricTransformer(epsg4258);

def layer = createLayer(name, description, envelope, geometry, {coordinate ->
  def geocentricInCordinate = toGeocentricTransformer.transform(coordinate);
  def geocentricOutCordinate = geocentricTransformer.transform(geocentricInCordinate)
  fromGeocentricTransformer.transform(geocentricOutCordinate)
}
);

facade.copy(layer, facade.layerReference("\$SYSTEM{jgisshell.workingpath}data/bwta/distances-${layer.name()}.shp"));

createXYZFile(layer, epsg31467)

createMap(layer, epsg31467, 0.0, 2.0, 0.05)