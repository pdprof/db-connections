FROM ibmcom/db2

# docker run -itd --name mydb2 --privileged=true -p 50000:50000 -e LICENSE=accept -e DB2INST1_PASSWORD=<choose an instance password> -e DBNAME=testdb -v <db storage dir>:/database ibmcom/db2
ENV LICENSE accept
ENV DB2INST1_PASSWORD passw0rd
ENV DBNAME pdprof 
