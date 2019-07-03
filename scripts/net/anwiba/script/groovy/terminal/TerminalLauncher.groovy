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
package net.anwiba.script.groovy.terminal

import javax.swing.JOptionPane

import groovy.swing.SwingBuilder
import net.anwiba.jgisshell.scripting.groovy.api.JGISShellGroovyScript
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
