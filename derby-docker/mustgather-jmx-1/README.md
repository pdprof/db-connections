# MustGather Hands-On JMX case 

## Start db-connections container on docker or openshift

Follow steps described at parent folder's README 


## Access to the servlet to test db operation select/insert/update/dalete

Access to following URL by your browser

- http://localhost:9080/db.connections/db

![db](db.png)

Please use buttons to test database operation you wanted

You can check that behavior with websphere trace.


## Create scripts to access database by multithreads

Access to following URL by your browser

- http://localhost:9080/db.connections/script

![script](script.png)

Please use populate button to get scripts with your expected options

Script example
```
seq 1 30 | xargs -P 30 --replace time curl -o response.{}.txt http://localhost:9080/db.connections/db?timeout=180000
```

## Check Connection Pool Usage with JMX Rest Connector


You can use following URL to check REST API.

- https://localhost:9443/IBMJMXConnectorREST/api

Note: User is wsadmin. Password is passw0rd.


Get mbeans list and find ConnectionManagerMBean.

- https://localhost:9443/IBMJMXConnectorREST/mbeans

```
{"objectName":"WebSphere:type=com.ibm.ws.jca.cm.mbean.ConnectionManagerMBean,jndiName=jdbc/derbyEmbedded,name=dataSource[DefaultDetaSource]/connectionManager[default-0]","className":"com.ibm.ws.jca.cm.mbean.ConnectionManagerMBean","URL":"/IBMJMXConnectorREST/mbeans/WebSphere%3AjndiName%3Djdbc%2FderbyEmbedded%2Cname%3DdataSource%5BDefaultDetaSource%5D%2FconnectionManager%5Bdefault-0%5D%2Ctype%3Dcom.ibm.ws.jca.cm.mbean.ConnectionManagerMBean"}
```

Get MBean information.

- https://localhost:9443/IBMJMXConnectorREST/mbeans/WebSphere%3AjndiName%3Djdbc%2FderbyEmbedded%2Cname%3DdataSource%5BDefaultDetaSource%5D%2FconnectionManager%5Bdefault-0%5D%2Ctype%3Dcom.ibm.ws.jca.cm.mbean.ConnectionManagerMBean

```
{"className":"com.ibm.ws.jca.cm.mbean.ConnectionManagerMBean","description":"MBean for a Connection Manager","descriptor":{"names":[],"values":[]},"attributes":[],"attributes_URL":"/IBMJMXConnectorREST/mbeans/WebSphere%3AjndiName%3Djdbc%2FderbyEmbedded%2Cname%3DdataSource%5BDefaultDetaSource%5D%2FconnectionManager%5Bdefault-0%5D%2Ctype%3Dcom.ibm.ws.jca.cm.mbean.ConnectionManagerMBean/attributes","constructors":[],"notifications":[],"operations":[{"name":"purgePoolContents","description":"Purges the contents of the Connection Manager.","descriptor":{"names":[],"values":[]},"impact":"1","returnType":"java.lang.Void","signature":[{"name":"arg0","type":"java.lang.String","description":"Use 'abort' to abort all connections in the pool using Connection.abort(). Use 'immediate' to purge the pool contents immediately. Any other string value will purge the pool contents normally.","descriptor":{"names":[],"values":[]}}],"URL":"/IBMJMXConnectorREST/mbeans/WebSphere%3AjndiName%3Djdbc%2FderbyEmbedded%2Cname%3DdataSource%5BDefaultDetaSource%5D%2FconnectionManager%5Bdefault-0%5D%2Ctype%3Dcom.ibm.ws.jca.cm.mbean.ConnectionManagerMBean/operations/purgePoolContents"},{"name":"showPoolContents","description":"Displays the current contents of the Connection Manager in a human readable format.","descriptor":{"names":[],"values":[]},"impact":"0","returnType":"java.lang.String","signature":[],"URL":"/IBMJMXConnectorREST/mbeans/WebSphere%3AjndiName%3Djdbc%2FderbyEmbedded%2Cname%3DdataSource%5BDefaultDetaSource%5D%2FconnectionManager%5Bdefault-0%5D%2Ctype%3Dcom.ibm.ws.jca.cm.mbean.ConnectionManagerMBean/operations/showPoolContents"}]}
```

Try to get showPoolContents (You need to use curl command to post data)

```
curl -k -u wsadmin:passw0rd  -H "Content-Type: application/json" -d "{}" https://localhost:9443/IBMJMXConnectorREST/mbeans/WebSphere%3AjndiName%3Djdbc%2FderbyEmbedded%2Cname%3DdataSource%5BDefaultDetaSource%5D%2FconnectionManager%5Bdefault-0%5D%2Ctype%3Dcom.ibm.ws.jca.cm.mbean.ConnectionManagerMBean/operations/showPoolContents
```

Try to do purgePoolContents


```
 curl -k -u wsadmin:passw0rd  -H "Content-Type: application/json" -d '{"arg0":
"immediate"}' https://localhost:9443/IBMJMXConnectorREST/mbeans/WebSphere%3AjndiName%3Djdbc%2FderbyEmbedded%2Cname%3Ddat
aSource%5BDefaultDetaSource%5D%2FconnectionManager%5Bdefault-0%5D%2Ctype%3Dcom.ibm.ws.jca.cm.mbean.ConnectionManagerMBea
n/operations/purgePoolContents
{"value":null,"type":null}
```


## Check json response for all requests.

You can use jq command to format json. If you do not have jq in your environment, try sed 's/,/,\n/g' to look into each field.
