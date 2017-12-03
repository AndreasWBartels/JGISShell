package net.anwiba.script.groovy
// Copyright (c) 2015 by Andreas W. Bartels
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript script

def name = "Bundesländer";
def sourceReference = layerReference("\$SYSTEM{jgisshell.workingpath}/data/bundeslaender.shp");
def targetReference = layerReference("\$SYSTEM{java.io.tmpdir}" + File.separator + "bundeslaender.shp");

if (!exists(sourceReference)) {
  warn("Couldn't find source reference '" + toString(sourceReference) + "'");
  return;
}
if (exists(targetReference)) {
  if (!isDeletable(targetReference)) {
    warn("Couldn't delete existing target reference '" + toString(targetReference) + "'");
    return;
  }
  delete(targetReference)
}
copy(sourceReference, targetReference);
view().add(name,targetReference);

