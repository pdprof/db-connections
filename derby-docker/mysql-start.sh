#!/bin/bash
docker run -itd --name mysqldb --privileged=true -p 3306:3306 mysqldb
