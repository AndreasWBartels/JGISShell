set JAVA_HOME=%{JDKPath}
%JAVA_HOME%\bin\java -Xms64m -Xmx4096m -Dnet.anwiba.logging.configuration=%{log.config} -jar JGISShell.jar
