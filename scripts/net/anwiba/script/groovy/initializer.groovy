package net.anwiba.script.groovy
// Copyright (c) 2015 by Andreas W. Bartels (bartels@anwiba.de)
import java.util.concurrent.TimeUnit

import net.anwiba.scripting.api.groovy.JGISShellGroovyScript;
@groovy.transform.BaseScript JGISShellGroovyScript facade

facade.processLauncher()
    .description("backup")
    .delay(1)
    .isPeriodic(true)
    .timeUnit(TimeUnit.MINUTES)
    .closure( { def monitor, def canceler ->
      def resource = facade.resource("\$SYSTEM{jgisshell.workingpath}/data/backup.map")
      def map = facade.map().map()
      if (!map.isEmpty()) facade.write(map, resource)
    })
    .launch()