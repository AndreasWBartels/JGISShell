# JGISShell
see [DOAG Geodata Days 2017 slides](https://github.com/AndreasWBartels/JGISShell/wiki/Presentations)
---
Java GIS Viewer and Layer Datastore Manager

JGSShell is a Java based spatial data viewer and layer datastore manager. It consist of 4 parts, a viewer, a layer manager, a scripting API (groovy and Java) and a URL concept to access layer and datastores.

I started JGISShell 10 years ago as an private education and evaluation project for spatial data and technologies. Now it has riched a state that it could be used by other. Maybe for educaction, explore spatial data or manage spatial data. 

## Geodata Viewer

![Viewer](https://github.com/AndreasWBartels/JGISShell/wiki/images/WindAndRadioWaves-LUBW.png)  
`map source: © 2016 Landesanstalt für Umwelt, Messungen und Naturschutz Baden-Württemberg and Landesamt für Geoinformation und Landentwicklung Baden-Württemberg`

The first part is a viewer for spatial data who supports the following formats:

### Feature based geodata
Shapefiles, GeoJSON, PostGIS, SpatiaLite, GeoPackage, Oracle Locator/Spatial, SAP Hana, ESRI MDB based Geodatabases, ESRI Arc GIS Rest Feature Services, ESRI SDE and a own GML equal XML format.

### Image based geodata
Worldfiles, mbtiles, OSM, MAPBOX Tile Layers, GeoPackage, ESRI Arc GIS Rest Map and Image Services

### Grid based geodata
ESRI ASCII Grid, Saga Grid and XYZ Grid files

## Layer Manager
The second part is a file system manager like dialog to explore and manage geodata.

![Viewer](https://github.com/AndreasWBartels/JGISShell/wiki/images/layer-manager.png)  

## Scripting
The third part is the possibility to extend the viewer with java and groovy.

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
The fourth part is a URL based schema to access different kinds of datastores and layers.

## Author
JGISShell was created by [Andreas W. Bartels](https://github.com/AndreasWBartels).

## License

The license is a reduced [BSD](https://www.freebsd.org/copyright/freebsd-license.html) like binary license.
See [license.txt](https://github.com/AndreasWBartels/JGISShell/blob/master/license.txt) for more information.

## Installation

see [WIKI - JGISShell-Installation](https://github.com/AndreasWBartels/JGISShell/wiki/JGISShell-Installation)

For more see [WIKI](https://github.com/AndreasWBartels/JGISShell/wiki)

