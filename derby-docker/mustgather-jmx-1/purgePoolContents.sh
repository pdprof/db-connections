#!/bin/bash
if [ -z $ACCESS_HOST ]; then
   echo 'Please set the "ACCESS_HOST" environment variable and try again.'
   exit 2
fi

if [ -z $PURGEPOOL_URL ]; then
   echo 'Please set the "PURGEPOOL_URL" environment variable and try again'
   exit 2
fi

date 
echo "***** purgePoolContents *****"
curl -s -k -u wsadmin:passw0rd -H "Content-Type: application/json" -d '@abort.json' https://${ACCESS_HOST}:9443${PURGEPOOL_URL}
