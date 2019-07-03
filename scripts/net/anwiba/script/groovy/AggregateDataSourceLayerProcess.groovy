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
package net.anwiba.script.groovy

import java.awt.Color;

import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

facade.processLauncher()
    .description("create datastore overview layer")
    .closure({ def monitor, def canceler ->
      def targetSystem = coordinateReferenceSystem("EPSG",4326)
      def builder = featureLayerBuilder()
      builder.name("Envelopes")
      builder.identifierAttribute("IDENTIFIER")
      builder.attribute("URL", stringType())
      builder.attribute("NAME", stringType())
      builder.attribute("ATTRIBUTES", stringType())
      builder.attribute("SRS", stringType())
      builder.attribute("GEOMETRY_TYPE", stringType())
      builder.geometryAttribute("GEOMETRY",facade.polygon(),2,false,targetSystem)

      def layerReferences = facade.layerReferences( dataStoreReference )

      for (def layerRef : layerReferences) {
        def layerUrl =  facade.toString(layerRef)
        try {
          println "load layer ${layerUrl}"
          def layer = read(layerRef)
          def layerName = layer.name()
          def attributeNames = ""
          for (def attributeName : layer.attributeNames()) {
            attributeNames = attributeNames + "  " + attributeName + ":" + layer.dataType(attributeName).name()
          }
          def srs = layer.coordinateReferenceSystem()
          def srsString = srs ? wkt(srs) : null
          def geometry = transform(geometry(srs, layer.envelope()),targetSystem)
          def geometryString = geometry ? wkt(geometry) : null
          builder.values(null, layerUrl, layerName, attributeNames, srsString, layer.geometryType().name(), geometry)
        } catch (Exception e) {
          println e
          error("AggregateFileSystemLayers" ,"loading layer ${layerUrl} faild" ,e)
        }
      }

      def featureLayer = builder.build()
      def style = facade.featureStyleBuilder(facade.polygon())
          .defaultStyle(facade.areaStyle(Color.BLACK))
          .build()
      def id = view().add(featureLayer)
      view().style(id, style)
    })
    .launch()