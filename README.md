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
Shapefiles, GeoJSON, PostGIS, SpatiaLite, GeoPackage, Oracle Locator/Spatial, SAP Hana, ESRI MDB based Geodatabases, ESRI Arc GIS Rest Feature Services, ESRI SDE and a own GML equal XML format.

### Image based geodata
Worldfiles, mbtiles, OSM, MAPBOX Tile Layers, GeoPackage, ESRI Arc GIS Rest Map and Image Services
published
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

## Author
JGISShell was created by [Andreas W. Bartels](https://github.com/AndreasWBartels).

## License

The license is a reduced [BSD](https://www.freebsd.org/copyright/freebsd-license.html) license. Which regulates the use of the binary code.
See [license.txt](https://github.com/AndreasWBartels/JGISShell/blob/master/license.txt) for more information.

The source-code, insofar as it is published, is located in this project [libraries](https://github.com/AndreasWBartels/libraries)

## Installation

see [WIKI - JGISShell-Installation](https://github.com/AndreasWBartels/JGISShell/wiki/JGISShell-Installation)

For more see [WIKI](https://github.com/AndreasWBartels/JGISShell/wiki)
 
