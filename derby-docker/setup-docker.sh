#!/bin/bash
#curl -O https://www.ibm.com/support/pages/system/files/support/swg/swgtech.nsf/0/d83af3cb5f0490d1852579d600618374/$FILE/trapit.002/trapit
#if [ ! -f trapit ]; then
#     echo "===================="
#     echo "Please download trapit from https://www.ibm.com/support/pages/node/709009 and put it on the same directory."
#     echo "====================" 
#     exit 1
#fi
if [ -z $ACCESS_HOST ]; then
   echo 'Please set the "ACCESS_HOST" environment variable and try again.'
   exit 2
fi

# Setup for derby
cp config/server.xml.derby config/server.xml
docker build -t db-connections .
rm config/server.xml

# Setup for db2
docker build -t mydb2 -f Dockerfile.db2 .
mkdir db2data
chown 1000:1000 db2data
./db2-start.sh
sleep 10
mkdir lib
docker cp mydb2:/opt/ibm/db2/V11.5/java/db2jcc4.jar lib/
sed s/localhost/$ACCESS_HOST/g config/server.xml.db2 > config/server.xml
docker build -t db2-connections -f Dockerfile.ds-db2 .
rm config/server.xml

# Setup fo mysql
docker build -t mysqldb -f Dockerfile.mysql .
mkdir mysqldata
chown 1000:1000 mysqldata
sleep 10
sed s/localhost/$ACCESS_HOST/g config/server.xml.mysql > config/server.xml
docker build -t mysql-connections -f Dockerfile.ds-mysql .
rm config/server.xml
