#!/bin/bash

MAIN_CLASS="KoRConsole"
MODULES="--module-path /usr/lib/jvm/javafx-sdk-21.0.2/lib --add-modules javafx.controls,javafx.fxml"
OUT_DIR="./out/"
SOURCE_DIR="./src/main/"
TEST_DIR="./src/test/"
TEST_OUT_DIR="${OUT_DIR}test/"
JUNIT_JAR="/usr/share/java/junit-platform-console-standalone-1.10.2.jar"

compileApp() {
    echo "#### ---Compiling KoRConsole--- ####"
    javac $MODULES -d $OUT_DIR --source-path $SOURCE_DIR $(find $SOURCE_DIR -name "*.java")
    if [ $? -ne 0 ]; then
        echo "Compilation failed"
        exit 1
    fi
}

compileTests() {
    echo "#### ---Compiling Tests--- ####"
    mkdir -p $TEST_OUT_DIR
    javac $MODULES -d $TEST_OUT_DIR --source-path "$SOURCE_DIR:$TEST_DIR" -cp $JUNIT_JAR $(find $TEST_DIR -name "*.java")
    if [ $? -ne 0 ]; then
        echo "Test compilation failed"
        exit 1
    fi
}

runTests() {
    echo "#### ---Running Tests--- ####"
    java -jar $JUNIT_JAR --class-path "$OUT_DIR:$TEST_OUT_DIR" --scan-class-path
    if [ $? -ne 0 ]; then
        echo "Tests failed"
        exit 1
    fi
}

runApp() {
    echo "#### ---Running KoRConsole--- ####"
    java $MODULES -cp "$OUT_DIR" $MAIN_CLASS
}

# Main logic based on the argument
case "$1" in
    test)
        compileApp
        compileTests
        runTests
        ;;
    run)
        compileApp
        runApp
        ;;
    *)
        compileApp
        compileTests
        runTests
        runApp
        ;;
esac

