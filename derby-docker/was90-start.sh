#!/bin/bash
docker run -d -p 9060:9060 -p 9043:9043 -p 9080:9080 -p 9443:9443 --name was90-db2 was90-db2
