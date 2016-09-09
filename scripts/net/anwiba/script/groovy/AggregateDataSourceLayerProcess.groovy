package net.anwiba.script.groovy
import net.anwiba.scripting.api.groovy.JGISShellGroovyScript;
@groovy.transform.BaseScript JGISShellGroovyScript facade

facade.processLauncher()
    .description("create datastore overview layer")
    .closure({ def monitor, def canceler ->
      def epsg3785 = coordinateReferenceSystem("PROJCS[\"WGS 84 / Pseudo-Mercator\",GEOGCS[\"Popular Visualisation CRS\",DATUM[\"Popular_Visualisation_Datum\",SPHEROID[\"Popular Visualisation Sphere\",6378137,0,AUTHORITY[\"EPSG\",\"7059\"]],TOWGS84[0,0,0,0,0,0,0],AUTHORITY[\"EPSG\",\"6055\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.01745329251994328,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4055\"]],UNIT[\"metre\",1,AUTHORITY[\"EPSG\",\"9001\"]],PROJECTION[\"Mercator_1SP\"],PARAMETER[\"central_meridian\",0],PARAMETER[\"scale_factor\",1],PARAMETER[\"false_easting\",0],PARAMETER[\"false_northing\",0],AUTHORITY[\"EPSG\",\"3785\"],AXIS[\"X\",EAST],AXIS[\"Y\",NORTH]]")
      def epsg31467 = coordinateReferenceSystem("PROJCS[\"DHDN / 3-degree Gauss-Kruger zone 3\", GEOGCS[\"DHDN\", DATUM[\"Deutsches Hauptdreiecksnetz\", SPHEROID[\"Bessel 1841\", 6377397.155, 299.1528128, AUTHORITY[\"EPSG\",\"7004\"]], TOWGS84[612.4, 77.0, 440.2, -0.054, 0.057, -2.797, 2.55], AUTHORITY[\"EPSG\",\"6314\"]], PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], UNIT[\"degree\", 0.017453292519943295], AXIS[\"Geodetic longitude\", EAST], AXIS[\"Geodetic latitude\", NORTH], AUTHORITY[\"EPSG\",\"4314\"]], PROJECTION[\"Transverse_Mercator\"], PARAMETER[\"central_meridian\", 9.0], PARAMETER[\"latitude_of_origin\", 0.0], PARAMETER[\"scale_factor\", 1.0], PARAMETER[\"false_easting\", 3500000.0], PARAMETER[\"false_northing\", 0.0], UNIT[\"m\", 1.0], AXIS[\"Easting\", EAST], AXIS[\"Northing\", NORTH], AUTHORITY[\"EPSG\",\"31467\"]]")

      def builder = featureLayerBuilder()
      builder.name("Envelopes")
      builder.identifierAttribute("IDENTIFIER")
      builder.attribute("URL", stringType())
      builder.attribute("NAME", stringType())
      builder.attribute("ATTRIBUTES", stringType())
      builder.attribute("SRS", stringType())
      builder.attribute("GEOMETRY_TYPE", stringType())
      builder.geometryAttribute("GEOMETRY",facade.polygon(),2,false,epsg3785)

      def layerReferences = facade.iterable( dataStoreReference )

      for (def layerRef : layerReferences) {
        def layerUrl =  toString(layerRef)
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
          def geometry = transform(geometry(srs, layer.envelope()),epsg3785)
          def geometryString = geometry ? wkt(geometry) : null
          builder.values(null, layerUrl, layerName, attributeNames, srsString, layer.geometryType().name(), geometry)
        } catch (Exception e) {
          println e
          error("AggregateFileSystemLayers" ,"loading layer ${layerUrl} faild" ,e)
        }
      }

      def featureLayer = builder.build()
      map().add(featureLayer)
    })
    .launch()