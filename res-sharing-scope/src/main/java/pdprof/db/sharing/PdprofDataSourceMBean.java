package pdprof.db.sharing;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PdprofDataSourceMBean {
	
	public static String showPoolContents(String host, int port, String authorization) {
		String baseUrl = "https://" + host + ":" + port; 
        String path = "/IBMJMXConnectorREST/mbeans/WebSphere%3AjndiName%3Djdbc%2FderbyEmbedded%2Cname%3DdataSource%5BPdprofDataSource%5D%2FconnectionManager%5Bdefault-0%5D%2Ctype%3Dcom.ibm.ws.jca.cm.mbean.ConnectionManagerMBean/operations/showPoolContents";
        Client client = ClientBuilder.newClient().property("com.ibm.ws.jaxrs.client.disableCNCheck", "true");
        WebTarget target = client.target(baseUrl).path(path);
        String inputData = "{}";
        Response responseData = target.request(MediaType.APPLICATION_JSON).header("Authorization", authorization)
                                  .post(Entity.entity(inputData, MediaType.APPLICATION_JSON));
        Object value = new String("No data");
        if (responseData.getStatus() == 200) {
        	Map<String, Object> resultMap = responseData.readEntity(new GenericType<Map<String, Object>>() {});
            value = resultMap.get("value");
        }
        return value.toString();
	}


}
