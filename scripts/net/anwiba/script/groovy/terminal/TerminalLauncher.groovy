// Copyright (c) 2015 by Andreas W. Bartels
package net.anwiba.script.groovy.terminal

import javax.swing.JOptionPane

import groovy.swing.SwingBuilder
import net.anwiba.spatial.scripting.groovy.api.JGISShellGroovyScript
@groovy.transform.BaseScript JGISShellGroovyScript facade

//def terminal = "/usr/bin/Konsole"
def terminal = "/usr/bin/mate-terminal"
//def terminal = "/usr/bin/gnome-terminal"
//def terminal = "/usr/bin/xterm"
//def terminal = "C:\\Windows\\System32\\cmd.exe"
if (new File(terminal).exists()) {
  def folder = path.join(File.separator)
  def commandPattern = "${terminal} --working-directory=\"${folder}\""
  //def commandPattern = "${terminal} /k \"cd ${folder}\""
  facade.programLauncher()
      .command(commandPattern)
      .launch()
} else {
  desktop.open(facade.resource("\$SYSTEM{jgisshell.workingpath}/scripts/net/anwiba/script/groovy/terminal/TerminalLauncher.groovy"))
  new SwingBuilder().optionPane().showMessageDialog(parentComponent,
      "Couldn't find the terminal program.\nPlease, edit the script 'TerminalLauncher'.",
      "Error",
      JOptionPane.INFORMATION_MESSAGE)
}
