package net.anwiba.script.groovy.bwta

import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

def epsg4314 = facade.coordinateReferenceSystem("EPSG",4314);
def epsg4258 = facade.coordinateReferenceSystem("EPSG",4258);
def epsg31467 = facade.coordinateReferenceSystem("EPSG",31467);
def epsg25832 = facade.coordinateReferenceSystem("EPSG",25832);


def epsg3147toEpsg4314Transformer = transformer(epsg31467 ,epsg4314);
def epsg4314toEpsg4258Bwta2017Transformer = transformer(resource("\$SYSTEM{jgisshell.workingpath}/data/ntv2/bw/BWTA2017.gsb"), forward())
def epsg4258toEpsg25832Transformer = transformer(epsg4258 ,epsg25832);

def builder = featureLayerBuilder()
builder.name("BWTA2017")
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

def envelope = envelope(3387500, 5265000, 3612000, 5520000)
def stepSize = 1000

def start = System.currentTimeMillis();
for (def x = (envelope.getMinimum().getXValue() + (stepSize/2)); x < envelope.getMaximum().getXValue(); x+=stepSize) {
  for (def y = (envelope.getMinimum().getYValue() + (stepSize/2)); y < envelope.getMaximum().getYValue(); y+=stepSize) {
    def epsg31467Coordinate = facade.coordinate(x, y)
    try {

      def epsg4314Coordinate = epsg3147toEpsg4314Transformer.transform(epsg31467Coordinate)
      def epsg4258wtaCoordinate = epsg4314toEpsg4258Bwta2017Transformer.transform(epsg4314Coordinate)
      def epsg25832BwtaCoordinate = epsg4258toEpsg25832Transformer.transform(epsg4258wtaCoordinate)

      builder.values(null, 0, point_counter, 0.0, epsg31467Coordinate.getXValue(), epsg31467Coordinate.getYValue(), "bwta2017", "NTv2 Gridshift", facade.point(epsg25832, epsg25832BwtaCoordinate))
      point_counter++
    } catch (Exception e) {
      exeption_counter++;
    }
  }
}
duration = duration + (System.currentTimeMillis() - start);

println "number of points: " + point_counter
println "number of exceptions: " + exeption_counter
println "duration: " + duration

def layer = builder.build();
def targetReference = facade.layerReference("\$SYSTEM{jgisshell.workingpath}/data/bwta/${layer.name()}.shp");
facade.copy(layer, targetReference);