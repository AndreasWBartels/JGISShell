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
def epsg25832 = facade.coordinateReferenceSystem("EPSG",25832);

def toGeocentricTransformer = facade.toGeocentricTransformer(epsg4314);
def fromGeocentricTransformer = facade.fromGeocentricTransformer(epsg4258);

def epsg4258toEpsg25832Transformer = transformer(epsg4258 ,epsg25832);

def transformGeometry = { toEpsg4258Transformer, geometry  ->
  def epsg4258Geometry = toEpsg4258Transformer.transform(geometry)
  epsg4258toEpsg25832Transformer.transform(epsg4258Geometry)
}

def transformGeocentric = { geocentricTransformer, geometry  ->
  def geocentricInGeometry = toGeocentricTransformer.transform(geometry);
  def geocentricOutGeometry = geocentricTransformer.transform(geocentricInGeometry)
  def epsg4258Geometry = fromGeocentricTransformer.transform(geocentricOutGeometry)
  epsg4258toEpsg25832Transformer.transform(epsg4258Geometry)
}


def beta2017ToEpsg4258Transformer = transformer(resource("\$SYSTEM{jgisshell.workingpath}data/bwta/ntv2/BETA2007.gsb"), forward())
def epsg4326toEpsg4314Bwta2017Transformer = transformer(resource("\$SYSTEM{jgisshell.workingpath}data/bwta/ntv2/BWTA2017.gsb"), backward())


def builder = featureLayerBuilder()
builder.name("geometry")
builder.identifierAttribute("IDENTIFIER")
builder.attribute("DESCRIPTION", stringType())
builder.geometryAttribute("GEOMETRY",facade.polygon(),2,false,epsg25832)

// disy
// def epsg4326Geometry = facade.geometry("POLYGON ((8.415586 49.0049084, 8.4155121 49.004763, 8.4156804 49.0047251, 8.4157331 49.0047133, 8.4157837 49.0047019, 8.4159546 49.0046635, 8.4164958 49.0045418, 8.4165111 49.0045741, 8.4165662 49.00469, 8.4160309 49.0048094, 8.4158598 49.0048476, 8.4158091 49.0048589, 8.415756 49.0048708, 8.415586 49.0049084))");
// church St Maria - Haltingen
def epsg4326Geometry = facade.geometry("POLYGON ((7.6159218 47.611263, 7.6159538 47.6111476, 7.6162609 47.6111862, 7.6162674 47.6111593, 7.6163477 47.6111689, 7.616335 47.6112211, 7.616357 47.6112401, 7.6163506 47.6112725, 7.6163154 47.6112912, 7.6162427 47.6112839, 7.6162384 47.6113038, 7.6159218 47.611263))");
def epsg4314Geometry = epsg4326toEpsg4314Bwta2017Transformer.transform(epsg4326Geometry);

builder.values(null, "BTWA2017", epsg4258toEpsg25832Transformer.transform(epsg4326Geometry))

builder.values(null, "BETA2007", transformGeometry(beta2017ToEpsg4258Transformer, epsg4314Geometry))
builder.values(null, "DHDN-2001", transformGeocentric(transformer(598.1,   73.7, 418.2,  0.202,  0.045, -2.455,   6.7), epsg4314Geometry))
builder.values(null, "DHDN-Shouth", transformGeocentric(transformer(597.1,   71.4, 412.1,  0.894,  0.068, -1.563,  7.58), epsg4314Geometry))

def style = facade.featureStyleBuilder(facade.polygon())
    .addConditionalStyle(facade.equal(facade.reference("DESCRIPTION"), facade.value("BTWA2017")), facade.areaStyle(Color.BLACK))
    .addConditionalStyle(facade.equal(facade.reference("DESCRIPTION"), facade.value("BETA2007")), facade.areaStyle(Color.GREEN))
    .addConditionalStyle(facade.equal(facade.reference("DESCRIPTION"), facade.value("DHDN-2001")), facade.areaStyle(Color.RED))
    .addConditionalStyle(facade.equal(facade.reference("DESCRIPTION"), facade.value("DHDN-Shouth")), facade.areaStyle(Color.BLUE))
    .build()


def layer = builder.build();

def id = view().add(layer)
view().style(id, style)


facade.copy(layer, facade.layerReference("\$SYSTEM{jgisshell.workingpath}data/bwta/geometry.shp"));

epsg4326toEpsg4314Bwta2017Transformer.close()
beta2017ToEpsg4258Transformer.close()