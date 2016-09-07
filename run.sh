#!/bin/sh
JAVA_HOME=/usr/lib/jvm/java-8-oracle/bin

program=`basename "$0"`
folder=`dirname "$0"`
cd "$folder"
directory=`pwd`

echo "$program"
echo "$folder"
echo "$directory"

$JAVA_HOME/java -Xms64m -Xmx4096m -XX:PermSize=32m -XX:MaxPermSize=1024m -Dnet.anwiba.logging.configuration=log4j.xml -jar JGISShell.jar
