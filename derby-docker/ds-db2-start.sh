#!/bin/bash
docker run -d --name db2-connections -p 9443:9443 -p 9080:9080 -v ~/pdprof:/pdprof db2-connections
