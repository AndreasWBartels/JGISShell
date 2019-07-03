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
package net.anwiba.script.groovy.license

import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
import net.anwiba.spatial.ckan.query.ILicenseSearchResult

@groovy.transform.BaseScript JGISShellGroovyScript facadeScript

def urls = [
  "https://www.europeandataportal.eu/data/api",
  "https://www.govdata.de/ckan/api",
  "https://catalog.data.gov/api",
  "http://data.deutschebahn.com/api",
  "http://data.europa.eu/euodp/data/api",
  "https://data.gov.au/api",
  "https://datahub.ckan.io/api",
  "https://demo.ckan.org/api",
  "http://offenedaten.de/api",
  "https://open.nrw/api",
  "https://transparenz.karlsruhe.de/api",
  "https://daten.rlp.de/api",
  "https://www.govdata.de/ckan/api"
]

createFile(resource("\$SYSTEM{jgisshell.workingpath}data/ckan/licenses.csv")).withWriter('utf-8') {  writer ->
  def head = "\"url\", \"id\", \"title\", \"reference\", \"isGenric\", \"isDomainContent\", \"isDomainData\", \"isOkdCompliant\", \"isDomainSoftware\", \"isOsiCompliant\""
  writer.writeLine head
  for (url in urls) {
    ILicenseSearchResult result = ckanSearchFacade.licenses(facade.resource(url))
    if (!result.isSuccessful()) {
      continue
    }
    for (license in result.getResults()) {
      def id = license.getId()
      def title = license.getTitle().toString()
      def reference = license.getUrl()
      def isGenric = license.is_generic()
      def isDomainContent = license.isDomain_content()
      def isDomainData = license.isDomain_data()
      def isOkdCompliant = license.is_okd_compliant()
      def isDomainSoftware = license.isDomain_software()
      def isOsiCompliant = license.is_osi_compliant()
      def line = "\"${url}\", \"${id}\", \"${title}\", \"${reference}\", ${isGenric}, ${isDomainContent}, ${isDomainData}, ${isOkdCompliant}, ${isDomainSoftware}, ${isOsiCompliant}"
      writer.writeLine line
    }
    def count = result.getCount();
  }
}