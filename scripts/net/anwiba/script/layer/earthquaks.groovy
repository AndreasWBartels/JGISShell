package net.anwiba.script.layer

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import groovy.json.JsonSlurper
import net.anwiba.commons.utilities.io.IClosableIoIterator
import net.anwiba.commons.utilities.time.ZonedDateTimeUtilities
import net.anwiba.spatial.coordinate.Coordinate

// http://www.fdsn.org/webservices/FDSN-WS-Specifications-1.0.pdf
// https://earthquake.usgs.gov/fdsnws/event/1/
// https://earthquake.usgs.gov/data/data.php#eq

def formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

def date = {timestamp ->
  return new Date(timestamp)
}

def logic = {value ->
  return value > 0
}

def values = {feature ->

  def values = new Object[28]
  def properties = feature.properties
  def geometry = feature.geometry
  values[0] = feature.id
  values[1] = properties.ids
  values[2] = properties.code
  values[3] = properties.place
  values[4] = properties.title
  values[5] = properties.types
  values[6] = properties.type
  values[7] = properties.mag
  values[8] = properties.magType
  values[9] = properties.sig
  values[10] = properties.nst
  values[11] = properties.dmin
  values[12] = properties.rms
  values[13] = properties.gap
  values[14] = properties.alert
  values[15] = logic(properties.tsunami)
  values[16] = properties.felt
  values[17] = properties.cdi
  values[18] = properties.mmi
  values[19] = properties.net
  values[20] = properties.sources
  values[21] = properties.status
  values[22] = properties.url
  values[23] = properties.detail
  values[24] = date(properties.time)
  values[25] = date(properties.updated)
  values[26] = properties.tz
  if (geometry) {
    def coordinates = geometry.coordinates
    values[27] = geometryFactory.createPoint(new Coordinate( coordinates[0], coordinates[1], coordinates[2], false))
  }
  return values
}

def timeRangeValue = {value, defaultValue ->
  if (value) {
    return ZonedDateTimeUtilities.toString(value)
  }
  return defaultValue.format(formatter)
}

def formatCoordinateValue = {value ->
  return String.format("%7.2f", value).replaceAll(" ", "")
}

def url = {envelope, from, until ->
  def starttime = timeRangeValue(from, LocalDate.now())
  def endtime = timeRangeValue(until, LocalDate.now().plusDays(1))
  if (!envelope) {
    return "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&jsonerror=true&starttime=${starttime}&endtime=${endtime}".toString()
  }
  def minimum = envelope.getMinimum()
  def minlatitude = formatCoordinateValue(minimum.getYValue())
  def minlongitude = formatCoordinateValue(minimum.getXValue())
  def maximum = envelope.getMaximum()
  def maxlatitude = formatCoordinateValue(maximum.getYValue())
  def maxlongitude = formatCoordinateValue(maximum.getXValue())
  return "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&jsonerror=true&starttime=${starttime}&endtime=${endtime}&minlatitude=${minlatitude}&minlongitude=${minlongitude}&maxlatitude=${maxlatitude}&maxlongitude=${maxlongitude}".toString()
}

def jsonSlurper = new JsonSlurper()
def earthquakesUrl = url(envelope, from, until)

def earthquakes = jsonSlurper.parse(new URL(earthquakesUrl))

def metadata = earthquakes.metadata
if (metadata.status < 200 || 299 < metadata.status) {
  return new IClosableIoIterator() {

        @Override
        public void close() throws IOException {
        }

        @Override
        public boolean hasNext() throws IOException {
          throw new IOException(metadata.error)
        }

        @Override
        public Object next() throws IOException {
          throw new IOException(metadata.error)
        }
      };

}

def entities = []

def features = earthquakes.features
if (features) {
  for (earthquake in features) {
    if (!earthquake) {
      continue
    }
    def entity = values(earthquake)
    if (!entity) {
      continue
    }
    entities.add(entity)
  }
}

def iterator = entities.iterator()
new IClosableIoIterator() {

      @Override
      public void close() throws IOException {
      }

      @Override
      public boolean hasNext() throws IOException {
        return iterator.hasNext();
      }

      @Override
      public Object next() throws Exception {
        return iterator.next();
      }
    };
