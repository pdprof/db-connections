#!/bin/bash
if [ -z $ACCESS_HOST ]; then
   echo 'Please set the "ACCESS_HOST" environment variable and try again.'
   exit 2
fi
date 
echo "***** Get MBeans *****"
curl -s -k -u wsadmin:passw0rd https://${ACCESS_HOST}:9443/IBMJMXConnectorREST/mbeans | jq .

