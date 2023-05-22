#!/bin/bash
docker run -d --name mysql-connections --network pdprof-network -p 9443:9443 -p 9080:9080 mysql-connections
