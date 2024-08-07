/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fedexshipping;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;
import models.FedexShipmentRequest;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Khatchik
 */
public class FedexCancelShipmentClient {
    private final FxAppParms appParms;
    private final Logger logger;
    private final GetAuth auth;
    private FedexShipmentRequest request;

    public FedexCancelShipmentClient(GetAuth auth, FxAppParms appParms, Logger logger) {
        this.appParms = appParms;
        this.logger = logger;
        this.auth = auth;
    }
    
    public void cancelShipment(FedexShipmentRequest request) {
        this.request = request;
        JSONObject jobj = new JSONObject();
        jobj.put("accountNumber", bldAcctNo());
        jobj.put("trackingNumber", request.getTrkId());
        
        String input = jobj.toString();
        System.out.println("request = " + input);
        callHttp(input);
        
    }
    
    private JSONObject bldAcctNo() {
        JSONObject jobj = new JSONObject();
        jobj.put("value", appParms.getAccountNumber());
        return jobj;
    }
    private boolean callHttp(String input) {
        boolean ret = false;
        Client client1 = Client.create();
        client1.addFilter(new GZIPContentEncodingFilter(false));
        String url = appParms.getServer().trim()
                + appParms.getCxlShpPath();
        MultivaluedMap<String, String> hdrLst;
        String authToken = "Bearer " + auth.getAccessToken();

        WebResource webResource = client1.resource(url);
        ClientResponse response = webResource.accept("application/json")
                .header("content-type", "application/json")
                .header("X-locale", "en_US")
                .header("authorization", authToken)
                .put(ClientResponse.class, input);
        // String output = response.getEntity(String.class);
        String output = response.getEntity(String.class);
        
        // String s1 = response.toString();
        InputStream is = response.getEntityInputStream();
       
       
        if (appParms.isDebug()) {
            logger.log(Level.ALL, "request is {0}", input);
            logger.log(Level.ALL, "URL= {0}", url);
            logger.log(Level.ALL, "Token is {0}", authToken);
            logger.log(Level.ALL, "Response is {0}", output);
            System.out.println("return type = " + response.getType());
            System.out.println("IS =" + is.toString());     
        }
  //      this.jsonRsp = (JSONObject) JSONSerializer.toJSON(output);

        // System.out.println(response.getEntity(String.class).toString());
//        System.out.println("response ="+ s1);
        if (response.getStatus() != 200) {
            hdrLst = response.getHeaders();
            logger.log(Level.SEVERE, "Error retreiving fedex label respons status ={0} request ={1}",
                    new Object[]{response.getStatus(), request.getPtkt()});
            System.out.println("Ship client HTTP status = " + response.getStatus());
            System.out.println("URL = " + url);
            System.out.println("auth Token =" + authToken);
            System.out.println("request =" + input);
            System.out.println("respone =" + output);
            for (String key : hdrLst.keySet()) {
                System.out.println(key + "=" + hdrLst.getFirst(key));

            }
        } else {
            ret = true;
           // this.jsonRsp = (JSONObject) JSONSerializer.toJSON(output);
        }

        return ret;
    }
}
