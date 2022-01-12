# JGISShell
see [DOAG Geodata Days 2017 slides](https://github.com/AndreasWBartels/JGISShell/wiki/Presentations)
---
## Java GIS Viewer and Spatial Layer Datastore Manager

JGSShell is a Java based spatial data viewer and layer datastore manager. It consist of 4 parts, a viewer, a spatial-layer manager, a scripting API (groovy and Java) and a URL concept.

JGISShell was created 10 years ago as a private education and evaluation project to explore spatial data and technologies. Now it riched a state that it could be usefull for other. Maybe to learn, explore spatial data or manage spatial data. 

## Geodata Viewer

![Viewer](https://github.com/AndreasWBartels/JGISShell/wiki/images/WindAndRadioWaves-LUBW.png)  
`map source: © 2016 Landesanstalt für Umwelt, Messungen und Naturschutz Baden-Württemberg and Landesamt für Geoinformation und Landentwicklung Baden-Württemberg`

The viewer supports the following formats:

### Feature based geodata
WFS, Shapefiles, GeoJSON, PostGIS, SpatiaLite, GeoPackage, H2 Database. Oracle Locator/Spatial, MSSQL/SQLServer, SAP Hana, ESRI MDB based Geodatabases, ESRI Arc GIS Rest Feature Services and a own GML equal XML format.

### Image based geodata
WMS, WMTS, PostGIS Raster, Worldfiles, mbtiles, OSM, MAPBOX Tile Layers, GeoPackage, ESRI Arc GIS Rest Map and Image Services

### Grid based geodata
ESRI ASCII Grid, Saga Grid and XYZ Grid files

## Layer Manager
The manager is like a spatial data file system manager for exploring and managing geospatial data.

![Viewer](https://github.com/AndreasWBartels/JGISShell/wiki/images/layer-manager.png)  

## Scripting
The scripting API can be used to extend the Viewer and the Manager or to process geospatial data.

```groovy
import net.anwiba.scripting.groovy.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

map().
  add("Bundesl\u00e4nder",
    layerReference(
      "\$SYSTEM{jgisshell.workingpath}/data/bundeslaender.shp"
    )
  );
``` 

## Data access URL schema
The URL based schema is designed to access different kinds of datastores or spatial layers.

## Open Data search

![CKAN](https://github.com/AndreasWBartels/JGISShell/wiki/images/dialog-ckan-search.png)
`ckan search dialog using european data portal https://www.europeandataportal.eu/`

## Author
JGISShell was created by [Andreas W. Bartels](https://github.com/AndreasWBartels).

## License

JGISShell (JGISShell.jar) has a reduced [BSD](https://www.freebsd.org/copyright/freebsd-license.html) license. Which regulates the use of the binary code.
See [license.txt](https://github.com/AndreasWBartels/JGISShell/blob/master/license.txt) for more information.

The source-code, insofar as it is published, is located in this project [libraries](https://github.com/AndreasWBartels/libraries) and has the LGPL V2.1 license.

The source code published in this project, in the scripts folder, has the GPL V3 license.


## Installation

see [WIKI - JGISShell-Installation](https://github.com/AndreasWBartels/JGISShell/wiki/JGISShell-Installation)

For more download this project and open doc/manual/index.html.
