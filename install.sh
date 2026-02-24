#!/usr/bin/env bash
# Install required tools and publish the inform library locally.

# Install sbt (Scala Build Tool) via Coursier
cs install sbt

# Install scalafmt (Scala code formatter) version 3.7.3
cs install scalafmt:3.7.3

# Publish the inform library to the local Maven cache
sbt informlib/publish

# Download source artifacts for library dependencies
sbt informlib/updateClassifiers
