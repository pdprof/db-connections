# PDPro Application

## Requirements

- [Docker](https://www.docker.com/)

## Test on Docker

### Build docker image

```
setup-docker.sh
```

### Start docker for Derby database
```
mkdir ~/pdprof
docker run --rm -p 9443:9443 -p 9080:9080 -v ~/pdprof:/pdprof db-connections
```

Now you can access to http://localhost:9080/db.connections


### Start docker for Db2 database
```
./db2-start.sh
./ds-db2-start.sh
```

Now you can access to http://localhost:9080/db.connections


## Test on OpenShift

After you setup CRC described at [icp4a-helloworld](https://github.com/pdprof/icp4a-helloworld)

You can use following script. 
```
setup-openliberty.sh
```

Now you can access to http://db-connections-route-default.apps-crc.testing/top

Other test steps are same with docker.
