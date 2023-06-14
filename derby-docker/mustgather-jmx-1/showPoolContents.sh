#!/bin/bash
if [ -z $ACCESS_HOST ]; then
   echo 'Please set the "ACCESS_HOST" environment variable and try again.'
   exit 2
fi

if [ -z $SHOWPOOL_URL ]; then
   echo 'Please set the "SHOWPOOL_URL" environment variable and try again'
   exit 2
fi

date 
echo "***** showPoolContents *****"
curl -s -k -u wsadmin:passw0rd -H "Content-Type: application/json" -d "{}" https://${ACCESS_HOST}:9443${SHOWPOOL_URL} | jq -r '.value'

