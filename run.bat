set JAVA_HOME=%{JDKPath}
%JAVA_HOME%\bin\java -mx1024m -Dnet.anwiba.logging.configuration=%{log.config} -jar JGISShell.jar