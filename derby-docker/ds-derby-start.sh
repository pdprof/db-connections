#!/bin/bash
docker run -d --name derby-connections -p 9443:9443 -p 9080:9080 db-connections
