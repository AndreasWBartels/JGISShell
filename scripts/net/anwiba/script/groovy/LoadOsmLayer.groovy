package net.anwiba.script.groovy
// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
import net.anwiba.scripting.api.groovy.JGISShellGroovyScript;
@groovy.transform.BaseScript JGISShellGroovyScript facade

//facade.map()
//    .add("OSM", facade.layerReference("osm:http://osm.disy.net/osm/{zoomLevel}/{column}/{row}.png?minimumZoomLevel=1&minimumTile=1,1,1"));

//   facade.map().add("Satellite","osm:https://{identifier}.tiles.mapbox.com/v3/tatiana.l17anje0/{zoomLevel}/{column}/{row}.png?identifiers=a,b,c&name=Satellite"));
//   facade.map().add("Terrain","osm:https://{identifier}.tiles.mapbox.com/v3/aj.um7z9lus/{zoomLevel}/{column}/{row}.png?identifiers=a,b,c&name=Terrain"));
//   facade.map().add("Streets (Color)","osm:https://{identifier}.tiles.mapbox.com/v3/tatiana.l178h5i6/{zoomLevel}/{column}/{row}.png?identifiers=a,b,c&name=Streets (Color)"));
//   facade.map().add("Streets (BW)","osm:https://a.tiles.mapbox.com/v3/tatiana.l17goi5d/{zoomLevel}/{column}/{row}.png?identifiers=a,b,c&name=Streets (BW)"));

facade.map().add("Basic Map", facade.layerReference("osm:http://{identifier}.tile.openstreetmap.org/{zoomLevel}/{column}/{row}.png?identifiers=a,b,c"));
facade.map().add("hillshading", facade.layerReference("osm:http://a.tiles.wmflabs.org/hillshading/{zoomLevel}/{column}/{row}.png?minimumZoomLevel=2&minimumTile=2,2,1"));
facade.map().add("SAT", facade.layerReference("osm:http://otile1.mqcdn.com/tiles/1.0.0/sat/{zoomLevel}/{column}/{row}.jpg"));
facade.map().add("hikebike", facade.layerReference("osm:http://toolserver.org/tiles/hikebike/{zoomLevel}/{column}/{row}.png"));
//
// nordeuropa
//   facade.map().add("cycling", facade.layerReference("osm:http://tile.waymarkedtrails.org/cycling/{zoomLevel}/{column}/{row}.png?minimumZoomLevel=3&minimumTile=3,4,2&maximumTile=3,4,2"));
//   facade.map().add("Mapbox","osm:http://a.tiles.mapbox.com/v3/landplanner.map-azfnqdsx,landplanner.arches4,landplanner.map-v9fzb5f0"));
//   facade.map().add("CartoDB","osm:https://cartocdn-gucf.global.ssl.fastly.net/lubw-test/api/v1/map/54f5a5e28421b97446718fd8b9c98150:1450279758354.39"));
//   facade.map().add("CartoDB","osm:https://dnv9my2eseobd.cloudfront.net/v3/landplanner.map-sa4pz9ny"));
