#!/bin/bash
docker run -itd --name mydb2 --privileged=true -p 50000:50000 -v ./db2data:/database:z mydb2
