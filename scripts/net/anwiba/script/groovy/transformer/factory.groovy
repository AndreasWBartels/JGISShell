package net.anwiba.script.groovy.transformer

import net.anwiba.spatial.coordinate.transform.IProjectionCoordinateTransformer

return [
  toProjected: { c -> c },
  toSpherical: { c -> c }
] as IProjectionCoordinateTransformer