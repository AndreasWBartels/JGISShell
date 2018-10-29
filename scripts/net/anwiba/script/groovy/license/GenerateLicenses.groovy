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

import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript

@groovy.transform.BaseScript JGISShellGroovyScript facadeScript

def getResolved = {values, string ->
  if (!string) {
    return null
  }
  StringBuilder builder = new StringBuilder()
  for (def entry in values) {
    builder.append('' + entry.getKey() + '="' + entry.getValue() + '"\n')
  }
  builder.append('"' + string + '"')
  def script = builder.toString()
  def result = Eval.me(script)
  return result
}

def getResolvedList = {values, list ->
  def result = []
  for (def value in list) {
    result.add(getResolved(values, value))
  }
  result
}

def getAttributeName = { attributeNames,language,attribute ->
  if (language) {
    return  attributeNames.get(language).get(attribute)
  }
  return attributeNames.get("default").get(attribute)
}

def getName = { attributeNames,language, separator,attributes ->
  def names = []
  for (def attribute in attributes) {
    names.add( getAttributeName(attributeNames, language, attribute))
  }
  if (separator) {
    return   names.join(separator)
  }
  return names.join("-")
}

def getVersionedAliases = {version,identifier,aliasesByVerion ->
  def aliasesByIdentifier = aliasesByVerion.get(version)
  if (aliasesByIdentifier) {
    def aliases = aliasesByIdentifier.get(identifier)
    if (aliases) {
      return aliases
    }
  }
  return []
}

def execute = {writer, converter, attributeNames,identifiers,templates,attributeCombinationsByIdentifier,languages,separators,names,acronyms,references,versionsByIdentifier,aliasesByIdentivier,aliasesByVerion ->
  for (def identifier in identifiers) {
    def template =  templates.get(identifier)
    def aliases = aliasesByIdentivier.get(identifier)
    def attributeCombinations =attributeCombinationsByIdentifier.get(identifier)
    if (template) {
      if (!attributeCombinations) {
        println "$identifier has no attribute combinations"
        continue
      }
      for (def attributes in attributeCombinations) {
        def attribute = attributes.join("-")
        def attributeName = getName( attributeNames, languages.get(identifier), separators.get(identifier), attributes)
        def versions = versionsByIdentifier.get(identifier)
        if (versions) {
          for (def version in versions) {
            def values = [:]
            values.put("attribute", attribute)
            values.put("version", version)
            values.put("name", attributeName)
            def resolvedIdentifier = getResolved(values, template)
            def name = getResolved(values, names.get(identifier))
            def reference = getResolved(values, references.get(identifier))
            def acronym = getResolved(values, acronyms.get(identifier))
            def versionedAliases = getVersionedAliases(version,identifier,aliasesByVerion)
            def resolvedAliases = getResolvedList(values, aliases + versionedAliases )
            writer.write(converter(resolvedIdentifier,name,version,acronym,reference,attributes,resolvedAliases))
          }
        } else {
          def values = [:]
          values.put("attribute", attribute);
          values.put("name", attributeName)
          def resolvedIdentifier = getResolved(values, template)
          def name = getResolved(values, names.get(identifier))
          def reference = getResolved(values, references.get(identifier))
          def acronym = getResolved(values, acronyms.get(identifier))
          def resolvedAliases = getResolvedList(values, aliases)
          writer.write(converter(resolvedIdentifier,name,null,acronym,reference,attributes,resolvedAliases))
        }
      }
    } else {
      def versions = versionsByIdentifier.get(identifier)
      def name = names.get(identifier)
      def acronym = acronyms.get(identifier)
      def reference = references.get(identifier)
      for (def attributes in attributeCombinations) {
        if (!attributes) {
          println "$identifier has no attribute combinations"
          continue
        }
        if (versions) {
          for (def version in versions) {
            def versionedAliases = getVersionedAliases(version,identifier,aliasesByVerion)
            writer.write(converter(identifier,name,version,acronym,reference,attributes,aliases + versionedAliases))
          }
        } else {
          def version = null
          writer.write(converter(identifier,name,version,acronym,reference,attributes,aliases))
        }
      }
    }
    writer.flush();
  }
}

def convertToXml = {identifier,name,version,acronym,reference,attributes,aliases ->

  StringBuilder versionBuilder = new StringBuilder()
  if (version) {
    versionBuilder.append("        <version>${version}</version>\n")
  }
  def versionBlock = versionBuilder.toString()

  StringBuilder acronymBuilder = new StringBuilder()
  if (acronym) {
    acronymBuilder.append("        <acronym>${acronym}</acronym>\n")
  }
  def acronymBlock = acronymBuilder.toString()

  StringBuilder referenceBuilder = new StringBuilder()
  if (reference) {
    referenceBuilder.append("        <reference>${reference}</reference>\n")
  }
  def referenceBlock = referenceBuilder.toString()

  StringBuilder aliasesBuilder = new StringBuilder()
  aliasesBuilder.append("        <aliases>\n")
  aliasesBuilder.append("          <alias>${identifier.toUpperCase()}</alias>\n")
  if (aliases) {
    for (def alias in aliases) {
      aliasesBuilder.append("          <alias>${alias}</alias>\n")
    }
  }
  aliasesBuilder.append("        </aliases>\n")
  aliasesBlock = aliasesBuilder.toString()

  StringBuilder attributesBuilder = new StringBuilder()
  attributesBuilder.append("        <attributes>\n")
  if (attributes) {
    for (def attribute in attributes) {
      attributesBuilder.append("          <attribute>${attribute}</attribute>\n")
    }
  }
  attributesBuilder.append("        </attributes>\n")
  attributesBlock = attributesBuilder.toString()

  StringBuilder licenseBuilder = new StringBuilder()
  licenseBuilder.append("      <license>\n")
  licenseBuilder.append("        <identifier>${identifier}</identifier>\n")
  licenseBuilder.append("        <name>${name}</name>\n")
  licenseBuilder.append(acronymBlock)
  licenseBuilder.append(versionBlock)
  licenseBuilder.append(referenceBlock)
  licenseBuilder.append(attributesBlock)
  licenseBuilder.append(aliasesBlock)
  licenseBuilder.append("      </license>\n")
  licenseBuilder.toString()
}

//https://www.dcat-ap.de/def/licenses/1_0.html
//https://github.com/fraunhoferfokus/ogd-metadata/blob/master/lizenzen/deutschland.json
//https://www.europeandataportal.eu/en/content/show-license

def attributeNames =
    [
      "default":
      [
        "open":"Open",
        "closed":"Not Open",
        "forsale":"available for purchase",
        "commercial":"Commercial",
        "unpublished":"unpublished",
        "zero":"Zero",
        "cl":"Copyleft",
        "by":"Attribution",
        "npn":"NonPublicNetwork",
        "nc":"NonCommercial",
        "nd":"NoDerivatives",
        "pd":"Public Domain",
        "sa":"ShareAlike",
        "sc":"State Changes"
      ],
      "de":
      [
        "open":"freie Lizenz",
        "closed":"geschlossene Lizenz",
        "forsale":"Verfügbar zu Kauf",
        "commercial":"Kommerziell",
        "unpublished":"nicht Veröffentlicht",
        "zero":"Zero",
        "by":"Namensnennung",
        "cl":"Copyleft",
        "npn":"nicht öffentliche Netzwerke",
        "nc":"Nicht kommerziell",
        "nd":"Keine Bearbeitungen",
        "pd":"Public Domain",
        "sa":"Weitergabe unter gleichen Bedingungen",
        "sc":"Änderungsvermerk"
      ]
    ]

def languages =
    ["dl-de":"de"]

def separators =
    ["dl-de":" - "]

//    geonutz-be-2013-10-01 Nutzungsbestimmungen für die Bereitstellung von Geodaten des Landes Berlin http://www.stadtentwicklung.berlin.de/geoinformation/download/nutzIII.pdf
//    geonutzv-de-2013-03-19 Nutzungsbestimmungen für die Bereitstellung von Geodaten des Bundes http://www.geodatenzentrum.de/docpdf/geonutzv.pdf
//    geolizenz-v1.2-1a GeoLizenz V1-2 Ia-kommerziell-Weiterverarbeitung-oeffentliche_Netzwerke
//    geolizenz-v1.2-1b GeoLizenz V1-2 Ib-nicht-kommerziell-Weiterverarbeitung-oeffentliche_Netzwerke
//    geolizenz-v1.2-2a GeoLizenz V1-2 IIa-kommerziell-keine Weiterverarbeitung-oeffentliche_Netzwerke
//    geolizenz-v1.2-2b GeoLizenz V1-2 IIb-nicht-kommerziell-keine Weiterverarbeitung-oeffentliche_Netzwerke
//    geolizenz-v1.2-3a GeoLizenz V1-2 IIIa-kommerziell-Weiterverarbeitung-nicht-oeffentliche_Netzwerke
//    geolizenz-v1.2-3b GeoLizenz V1-2 IIIb-nicht-kommerziell-Weiterverarbeitung-nicht-oeffentliche_Netzwerke
//    geolizenz-v1.2-4a GeoLizenz V1-2 IVa-kommerziell-keine Weiterverarbeitung-nicht-oeffentliche_Netzwerke
//    geolizenz-v1.2-4b GeoLizenz V1-2 IVb-nicht-kommerziell-keine Weiterverarbeitung-nicht-oeffentliche_Netzwerke
//    geolizenz-v1.2.1-open GeoLizenz V1.2.1 Open-räumlich und zeitlich unbeschränkte Einräumung aller Nutzungsrechte

def identifiers =
    [
      "dl-de",
      "official-work",
      "geonutz-be-2013-10-01",
      "geonutzv-de-2013-03-19",
      "cc",
      "cca",
      "odbl",
      "dbcl",
      "other-attributes",
      "other",
      "notspecified"
    ]


def references =
    [
      "odbl":"https://opendatacommons.org/licenses/odbl/1.0/",
      "dbcl":"https://opendatacommons.org/licenses/dbcl/1.0/",
      "dl-de":"https://www.govdata.de/dl-de/\${attribute}-\${[\"1.0\":\"1-0\", \"2.0\":\"2-0\"].get(version)}",
      "official-work":"http://www.gesetze-im-internet.de/urhg/__5.html",
      "geonutz-be-2013-10-01":"http://www.stadtentwicklung.berlin.de/geoinformation/download/nutzIII.pdf",
      "geonutzv-de-2013-03-19":"http://www.geodatenzentrum.de/docpdf/geonutzv.pdf",
      "cc":"http://creativecommons.org/licenses/\${attribute}/\${version}/",
      "cca":"http://creativecommons.org/licenses/\${attribute}/\${version}/"
    ]

def names =
    [
      "odbl":"Open Database License (ODbL) v1.0",
      "dbcl":"Database Contents License (DbCL) v1.0",
      "dl-de":"Datenlizenz Deutschland \${name} Version \${version}",
      "official-work":"Amtliches Werk, lizenzfrei nach §5 Abs. 1 UrhG",
      "geonutz-be-2013-10-01":"Nutzungsbestimmungen für die Bereitstellung von Geodaten des Landes Berlin",
      "geonutzv-de-2013-03-19":"Nutzungsbestimmungen für die Bereitstellung von Geodaten des Bundes",
      "cc":"Creative Commons \${name} \${version} \${[\"1.0\":\"Generic\", \"2.0\":\"Generic\", \"2.5\":\"Generic\", \"3.0\":\"Unported\", \"4.0\":\"International\"].get(version)}",
      "cca":"Creative Commons \${name} \${version} Generic",
      "other-attributes":"Other (\${name})",
      "notspecified":"License not specified"
    ]

def acronyms =
    [
      "odbl":"ODbL",
      "dbcl":"DbCL",
      "dl-de":"DL-DE \${attribute.toUpperCase()} \${version}",
      "cc":"CC-\${attribute.toUpperCase()} \${version}",
      "cca":"CC-\${attribute.toUpperCase()} \${version}",
      "other-attributes":"OTHER-\${attribute.toUpperCase()}",
      "notspecified":"-"
    ]

def attributeCombinationsByIdentifier =
    [
      "odbl":[["by", "sa"]],
      "dbcl":[["by", "sa"]],      
      "dl-de":[["zero"], ["by"], ["by", "nc"]],
      "official-work":[["by", "nd"]],
      "geonutz-be-2013-10-01":[["by"]],
      "geonutzv-de-2013-03-19":[["by"]],
      "cc":[
        ["zero"],
        ["by"],
        ["by", "sa"],
        ["by", "nc"],
        ["by", "nc", "nd"],
        ["by", "nc", "sa"],
        ["by", "nd"],
        ["by", "sa"]
      ],
      "cca":[["nc"], ["nc", "sa"], ["nd"], ["sa"]],
      "other-attributes":[
        ["open"],
        ["closed"],
        ["forsale"],
        ["commercial"],
        ["unpublished"],
        ["zero"],
        ["by"],
        ["pd"],
        ["nc"],
        ["nd"],
        ["sa"]
      ],
      "notspecified":[["closed"]]]

def templates =
    [
      "dl-de":"dl-de-\${attribute}-\${version}",
      "cc":"cc-\${attribute}-\${version}",
      "cca":"cc-\${attribute}-\${version}",
      "other-attributes":"other-\${attribute}"
    ]

def aliases =
    [
      "odbl":["ODbL"],
      "dbcl":["DbCL"],
      "dl-de":[
        "dl\${attribute.replaceAll(\"-\", \"\")}de/\${version.replaceAll(\"[.]\", \"_\")}",
        "http://dcat-ap.de/def/licenses/dl-\${attribute}-de/\${version}"
      ],
      "official-work":[
        "officialWork",
        "http://dcat-ap.de/def/licenses/official-work",
      ],
      "cc":[
        "cc\${attribute.replaceAll(\"-\", \"\")}/\${version.replaceAll(\"[.]\", \"_\")}",
        "http://dcat-ap.de/def/licenses/cc-\${attribute}/\${version}",
        "http://dcat-ap.de/def/licenses/cc-\${attribute}-de/\${version}"
      ],
      "cca":[
        "cc\${attribute.replaceAll(\"-\", \"\")}/\${version.replaceAll(\"[.]\", \"_\")}",
        "http://dcat-ap.de/def/licenses/dl-\${attribute}/\${version}",
        "http://dcat-ap.de/def/licenses/dl-\${attribute}-de/\${version}"
      ],
      "other-attributes":[
        "other\${attribute}",
        "http://dcat-ap.de/def/licenses/other-\${attribute}"
      ],
      "notspecified":["other", "OTHER"]]

def aliasesByVerion =
    [
      "1.0":[
        "cc":[
          "cc-\${attribute}",
          "cc\${attribute.replaceAll(\"-\", \"\")}",
          "http://dcat-ap.de/def/licenses/cc-\${attribute}",
          "http://dcat-ap.de/def/licenses/cc-\${attribute}-de"
        ],
        "cca":[
          "cc-\${attribute}",
          "CC-\${attribute.toUpperCase()}",
          "cc\${attribute.replaceAll(\"-\", \"\")}",
          "http://dcat-ap.de/def/licenses/cc-\${attribute}",
          "http://dcat-ap.de/def/licenses/cc-\${attribute}-de"
        ],
        "dl-de":[
          "dl-\${attribute}-de",
          "DL-\${attribute.toUpperCase()}-DE",
          "http://dcat-ap.de/def/licenses/dl-\${attribute}-de"
        ]
      ]
    ]

def versionsByIdentifier =
    [
      "dl-de":["1.0", "2.0"],
      "cc":["1.0", "2.0", "2.5", "3.0", "4.0"],
      "dbcl":["1.0"],
      "odbl":["1.0"],
      "cca":["1.0"]]

def folder = resource("\$SYSTEM{jgisshell.workingpath}/data/ckan")
if (!exists(folder)) {
  createFolder(folder)
}

createFile(resource("\$SYSTEM{jgisshell.workingpath}/data/ckan/licenses.xml")).withWriter('utf-8') {  writer ->
  execute(writer, convertToXml, attributeNames,identifiers,templates,attributeCombinationsByIdentifier,languages,separators,names,acronyms,references,versionsByIdentifier,aliases,aliasesByVerion )
}
