
# this script assumes that this file was already created
# with sbt-assembly
JAR_FILE=FPToDoList-assembly-0.1.jar

echo "copying JAR file ..."
cp ../target/scala-2.12/${JAR_FILE} .

echo "running native-image ..."
# create a native image from the jar file and name
# the resulting executable `todo`
native-image --no-server -jar ${JAR_FILE} todo

