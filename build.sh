#!/bin/bash
if [ "$1" == "fresh" ]
then
    echo "Deleting old .class and .jar files"
    rm -rf bmpt/
    rm BMPT.jar
fi

javac -d . Pixel.java &&
javac -d . Bitmap.java &&
javac -d . BMPT.java &&
jar cvfm BMPT.jar Manifest.txt bmpt/Bitmap.class bmpt/BMPT.class bmpt/Pixel.class joptsimple/* META-INF/*

