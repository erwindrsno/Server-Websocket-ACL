#!/bin/bash

# Build project and copy the designated file to the target directory
mvn clean install package assembly:single && cp "./T06xxyyy.zip" "./target/"

# The below command only works for files copy
# Use rsync for robust file copying in Linux
# rsync -av "./T06xxyyy.zip" "./target/"
