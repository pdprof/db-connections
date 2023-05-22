#!/bin/bash
docker run -itd --name mysqldb --network pdprof-network --privileged=true -p 3306:3306 mysqldb
