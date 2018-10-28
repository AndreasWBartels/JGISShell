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
package net.anwiba.script.groovy.bwta

import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

// https://www.lgl-bw.de/lgl-internet/web/sites/default/de/07_Produkte_und_Dienstleistungen/Open_Data_Initiative/Galerien/Dokumente/AX_Gebiet_Bundesland.zip
// https://www.lgl-bw.de/lgl-internet/web/sites/default/de/05_Geoinformation/Galerien/Dokumente/BWTA2017.zip
// http://crs.bkg.bund.de/crseu/crs/descrtrans/BeTA/BETA2007.gsb
// https://www.lgl-bw.de/lgl-internet/web/sites/default/de/05_Geoinformation/Galerien/Dokumente/2016_10_27_ETRS89UTM_BW_stellt_um_Kolloquium_KIT.pdf

def download = {sourceUrl, folderPath, fileName ->

  def folder = resource(folderPath)
  def source = resource(sourceUrl)
  def target = resource("${folderPath}/${fileName}")

  try {
    if (!exists(folder)) {
      createFolder(folder)
    }
    if (!exists(target)) {
      copy(source, target)
    }
    if (exists(target)) {
      extract(target, folder)
    }
    return target
  } catch (Exception e) {
    error("download faild", e)
  }
}

def beta2007Resource = download("http://crs.bkg.bund.de/crseu/crs/descrtrans/BeTA/BETA2007.gsb",
    "\$SYSTEM{jgisshell.workingpath}/data/bwta/ntv2",
    "BETA2007.gsb")

def bwta2017Resource = download("https://www.lgl-bw.de/lgl-internet/web/sites/default/de/05_Geoinformation/Galerien/Dokumente/BWTA2017.zip",
    "\$SYSTEM{jgisshell.workingpath}/data/bwta/ntv2",
    "BWTA2017.zip")

if (exists(bwta2017Resource)) {
  extract(bwta2017Resource, resource("\$SYSTEM{jgisshell.workingpath}/data/bwta/ntv2"))
}

def bordersResource = download("https://www.lgl-bw.de/lgl-internet/web/sites/default/de/07_Produkte_und_Dienstleistungen/Open_Data_Initiative/Galerien/Dokumente/AX_Gebiet_Bundesland.zip",
    "\$SYSTEM{jgisshell.workingpath}/data/bwta/border",
    "AX_Gebiet_Bundesland.zip")

if (exists(bordersResource)) {
  extract(bordersResource, resource("\$SYSTEM{jgisshell.workingpath}/data/bwta/border"))
}


