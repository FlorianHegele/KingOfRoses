#!/bin/bash

MAIN_CLASS="KoRConsole"
MODULES="--module-path /usr/lib/jvm/javafx-sdk-21.0.2/lib --add-modules javafx.controls,javafx.fxml"
OUT_DIR="./out/"
SOURCE_DIR="./src/"

echo "Compiling KoRConsole"
javac $MODULES -d $OUT_DIR --source-path $SOURCE_DIR $(find $SOURCE_DIR -name "*.java")

if [ $? -ne 0 ]; then
    echo "Compilation failed"
    exit 1
fi

echo "Running KoRConsole"
java $MODULES -cp $OUT_DIR $MAIN_CLASS
