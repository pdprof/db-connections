#!/bin/bash
if [ -z $ACCESS_HOST ]; then
  echo 'Please set the "ACCESS_HOST" environment variable and try again.' 
  exit 2
fi

sed s/localhost/$ACCESS_HOST/g was90/installApp.py > was90/installApp.py.custom
chmod 755 was90/installApp.py.custom

docker build -t was90-db2 -f Dockerfile.was90-db2 .
