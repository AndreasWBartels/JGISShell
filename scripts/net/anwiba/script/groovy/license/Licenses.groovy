package net.anwiba.script.groovy.license

import net.anwiba.spatial.ckan.query.ILicenseSearchResult
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript

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

createFile(resource("\$SYSTEM{jgisshell.workingpath}/data/ckan/licenses.csv")).withWriter('utf-8') {  writer ->
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
