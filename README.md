# JGISShell
Java GIS Viewer and Layer Datastore Manager

JGISShell consist of 4 parts. 

## Geodata Viewer

![Viewer](https://raw.githubusercontent.com/AndreasWBartels/JGISShell/7c0dcd1ea2a6cce4814cbc335e668e9147d2fbc8/doc/images/WindAndRadioWaves-LUBW.png)  
`map source: © 2016 Landesanstalt für Umwelt, Messungen und Naturschutz Baden-Württemberg`  

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
The third part is the possibility to extend the viewer with java and groovy.

## Data access URL schema
The fourth part is a URL based schema to access different kinds of datastores and layers.

## JGISShell Installation
* First install [Java 8 Runtime Enviroment (JRE)](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
* Download and extract [JGISShell](https://github.com/AndreasWBartels/JGISShell/archive/master.zip)
* Enable execution right for JGISShell.jar
* Launch JGISShell with click or double click on JGISShell.jar
* If you want, register JGISShell. Without registration JGISShell is only a Geodata Viewer.

### Enable Oracle Locator/Spatial support
To enable Oracle Locator/Spatial support, you need the following JAR-Files:
* orai18n.jar
* ojdbc6.jar
* sdoapi.jar

Ask your Database Administrators for the libraries. If you are a developer you can download the libraries Oracle Technology Network.
The sdoapi.jar is part of the "Oracle Big Data Spatial and Graph - Vector API".

Copy
* ojdbc6.jar to JGISShell/lib/ojdbc6-12.1.0.2.jar
* orai18n.jar to JGISShell/lib/orai18n-12.1.0.2.jar
* sdoapi.jar to JGISShell/lib/sdoapi-12.1.0.1b.jar

### Enable SAP HANA support
To enable SAP Hana support, you need the following JAR-File:
* ngdbc.jar

Ask your Database Administrators for the library, or you can download the 'SAP HANA Client Software Packages' from SAP Store.

Copy ngdbc.jar to JGISShell/lib/ngdbc-1.94.0.jar

### Enable ESRI SDE support
To enable ESRI SDE support, you need the following JAR-Files:
* jpe_sdk.jar
* jsde_sdk.jar
* jsde_sdkres.jar

Ask your ESRI Geodatabase Administrators for the libraries. The libraries are contributed with the 'Java Development kit'.

Copy
* jpe_sdk.jar to JGISShell/lib/jpe_sdk-9.3.2.jar  
* jsde_sdk.jar to JGISShell/lib/jsde_sdk-9.3.2.jar  
* jsde_sdkres.jar to JGISShell/lib/jsde_sdkres-9.3.2.jar  

Note: This libraries are depricated, but they still works for the most Geodatabase layers. For more information [see](http://edndoc.esri.com/arcsde/9.3/api/japi/japi.htm)

## Author
JGISShell was created by [Andreas W. Bartels](https://github.com/AndreasWBartels).

## License

The license is a reduced [BSD](https://www.freebsd.org/copyright/freebsd-license.html) like license.
See license.txt for more information.

