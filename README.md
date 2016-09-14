# JGISShell
Java GIS Viewer and Layer Datastore Manager

JGISShell consist of 4 parts. 

## Geodata Viewer

![Viewer](https://raw.githubusercontent.com/AndreasWBartels/JGISShell/7c0dcd1ea2a6cce4814cbc335e668e9147d2fbc8/doc/images/WindAndRadioWaves-LUBW.png)
The first part is a viewer for spatial data who supports the following formats:

### Feature based geodata
Shapefiles, GeoJSON, PostGIS, SpatiaLite, GeoPackage, Oracle Locator/Spatial, SAP Hana, ESRI MDB based Geodatabases, ESRI Arc GIS Rest Feature Services, ESRI SDE and a own GML equal XML format.

### Image based geodata
Worldfiles, mbtiles, OSM, MAPBOX Tile Layers, ESRI Arc GIS Rest Map and Image Services

### Grid based geodata
ESRI ASCII Grid, Saga Grid and XYZ Grid files

## Layer Manager
The second part is a navigator tree to explore and manage geodata datastores.

## Scripting
The third part is possibility to extend the viewer with java and groovy.

## Data access URL schema
The fourth part is a URL based schema to access different kinds of datastores and layers.

## Author
JGISShell was created by [Andreas W. Bartels](https://github.com/AndreasWBartels).

## License
See license.txt for license information

