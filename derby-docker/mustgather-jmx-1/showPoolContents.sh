#!/bin/bash
if [ -z $ACCESS_HOST ]; then
   echo 'Please set the "ACCESS_HOST" environment variable and try again.'
   exit 2
fi
date 
echo "***** showPoolContents *****"
curl -s -k -u wsadmin:passw0rd -H "Content-Type: application/json" -d "{}" https://${ACCESS_HOST}:9443/IBMJMXConnectorREST/mbeans/WebSphere%3AjndiName%3Djdbc%2FderbyEmbedded%2Cname%3DdataSource%5BPdprofDataSource%5D%2FconnectionManager%5Bdefault-0%5D%2Ctype%3Dcom.ibm.ws.jca.cm.mbean.ConnectionManagerMBean/operations/showPoolContents | jq -r '.value'

