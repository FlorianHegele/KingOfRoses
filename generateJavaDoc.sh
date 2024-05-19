#!/bin/bash

# Répertoire source contenant les fichiers .java
SRC_DIR="src/main"

# Répertoire de sortie pour la Javadoc
DOC_DIR="docs/javadoc"

# Crée les répertoires de sortie s'ils n'existent pas
mkdir -p $DOC_DIR

# Génère la Javadoc
javadoc -d $DOC_DIR $(find $SRC_DIR -name "*.java")
