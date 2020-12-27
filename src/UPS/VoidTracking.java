/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UPS;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.logging.Level;
import javax.ws.rs.core.MultivaluedMap;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import pickticketprint.AppParms;
import pickticketprint.UpsShprParms;

/**
 *
 * @author Khatchik
 */
public class VoidTracking {

    public static void voidTracking(UpsShprParms parms, String trk) {
       
        Client client = Client.create();
        WebResource webResource = client.resource(parms.getVoidUrl());
        MultivaluedMap<String, String> hdrLst;

        JSONObject jsonWrk = new JSONObject();
        JSONObject jsonWrk1 = new JSONObject();
        JSONObject jsonWrk2 = new JSONObject();
        JSONObject jsonReq = new JSONObject();
        JSONObject jsonAccessReq = new JSONObject();
        JSONObject jsonVoidTrk = new JSONObject();
        jsonWrk.put("Username", parms.getShUserId());
        jsonWrk.put("Password", parms.getShPassWord());
        jsonAccessReq.put("UsernameToken", jsonWrk);
        jsonWrk.clear();
        jsonWrk.put("AccessLicenseNumber", parms.getShLicense());
        jsonAccessReq.put("ServiceAccessToken", jsonWrk);
        jsonWrk.clear();
        jsonVoidTrk.put("UPSSecurity", jsonAccessReq);

        jsonWrk.put("CustomerContext", "A4");
        jsonReq.put("TransactionReference", jsonWrk);

        jsonWrk.put("ShipmentIdentificationNumber", trk);
        jsonWrk2.put("Request", jsonReq);
        jsonWrk2.put("VoidShipment", jsonWrk);
        jsonVoidTrk.put("VoidShipmentRequest", jsonWrk2);

        String input = jsonVoidTrk.toString();

        ClientResponse response = webResource.accept("application/json")
                .header("Content-Type", "application/json;charset=UTF-8")
                .post(ClientResponse.class, input);

        if (response.getStatus() != 200) {
            System.out.println("http Failed status = " + response.getStatus());
            hdrLst = response.getHeaders();
            for (String key : hdrLst.keySet()) {
                System.out.println(key + "=" + hdrLst.getFirst(key));
            }
            AppParms.getLogger().log(Level.INFO, "Failed : HTTP error code :" + +response.getStatus());
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        String output = response.getEntity(String.class);
        //System.out.println("output:" + output);
        JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
        JSONObject jsonVoidRsp = new JSONObject();
        if (jsonOutput.containsKey("VoidShipmentResponse")) {
            jsonVoidRsp = jsonOutput.getJSONObject("VoidShipmentResponse");
        } else {
            jsonVoidRsp = jsonOutput;
           //System.out.println("Request = " + input);
           // System.out.println("response = " + output);
           // System.out.println("URL=" + parms.getVoidUrl());
        }

        //output = jsonVoidRsp.toString();
        //return jsonVoidRsp;
    }

}
