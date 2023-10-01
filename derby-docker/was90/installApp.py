print "Create J2C Authentication Data ..."
AdminTask.createAuthDataEntry('[-alias db2inst1Auth -user db2inst1 -password passw0rd -description ]')
print "Create Db2 JDBC Driver ..."
AdminTask.createJDBCProvider('[-scope Node=DefaultNode01 -databaseType DB2 -providerType "DB2 Using IBM JCC Driver" -implementationType "Connection pool data source" -name "DB2JCC" -description "One-phase commit DB2 JCC provider that supports JDBC 4.0 using the IBM Data Server Driver for JDBC and SQLJ. IBM Data Server Driver is the next generation of the DB2 Universal JCC driver. Data sources created under this provider support only 1-phase commit processing except in the case where JDBC driver type 2 is used under WebSphere Application Server for Z/OS. On WebSphere Application Server for Z/OS, JDBC driver type 2 uses RRS and supports 2-phase commit processing. This provider is configurable in version 7.0 and later nodes." -classpath [/tmp/db2jcc4.jar ] -nativePath [${DB2_JCC_DRIVER_NATIVEPATH} ] ]')
print "Create DataSource PdprofDataSource - jdbc/derbyEmbedded" 
db2jcc = AdminConfig.getid( '/Cell:DefaultCell01/Node:DefaultNode01/JDBCProvider:DB2JCC/')
AdminTask.createDatasource(db2jcc, '[-name PdprofDataSource -jndiName jdbc/derbyEmbedded -dataStoreHelperClassName com.ibm.websphere.rsadapter.DB2UniversalDataStoreHelper -containerManagedPersistence true -componentManagedAuthenticationAlias DefaultNode01/db2inst1Auth -configureResourceProperties [[databaseName java.lang.String PDPROF] [driverType java.lang.Integer 4] [serverName java.lang.String localhost] [portNumber java.lang.Integer 50000]]]')
ds = AdminConfig.getid('/DataSource:PdprofDataSource/')
AdminConfig.create('MappingModule', ds, '[[authDataAlias DefaultNode01/db2inst1Auth] [mappingConfigAlias DefaultPrincipalMapping]]')
cf = AdminConfig.getid( '/CMPConnectorFactory:PdprofDataSource_CF/')
AdminConfig.modify(cf, '[[name "PdprofDataSource_CF"] [authDataAlias "DefaultNode01/db2inst1Auth"] [xaRecoveryAuthAlias ""]]')
AdminConfig.create('MappingModule', cf, '[[authDataAlias DefaultNode01/db2inst1Auth] [mappingConfigAlias DefaultPrincipalMapping]]')
print "Install db.connections.war ..."
AdminApp.install('/work/config/db.connections.war', '[ -nopreCompileJSPs -distributeApp -nouseMetaDataFromBinary -nodeployejb -appname db_connections -createMBeansForResources -noreloadEnabled -nodeployws -validateinstall warn -noprocessEmbeddedConfig -filepermission .*\.dll=755#.*\.so=755#.*\.a=755#.*\.sl=755 -noallowDispatchRemoteInclude -noallowServiceRemoteInclude -asyncRequestDispatchType DISABLED -nouseAutoLink -noenableClientModule -clientMode isolated -novalidateSchema -contextroot /db.connections -MapResRefToEJB [[ db.connections.war "" db.connections.war,WEB-INF/web.xml jdbc/derbyEmbedded javax.sql.DataSource jdbc/derbyEmbedded "" "" "" ]] -MapModulesToServers [[ db.connections.war db.connections.war,WEB-INF/web.xml WebSphere:cell=DefaultCell01,node=DefaultNode01,server=server1 ]] -MapWebModToVH [[ db.connections.war db.connections.war,WEB-INF/web.xml default_host ]] -CtxRootForWebMod [[ db.connections.war db.connections.war,WEB-INF/web.xml /db.connections ]]]' )
AdminConfig.save()

