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
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;
import models.FedexResponse;
import models.FedexShipmentRequest;
import models.NotificationSeverityType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Khatchik
 */
public class FxAddrValClient {

    private final GetAuth auth;
    private FedexShipmentRequest request;
    private final FxAppParms appParms;
    private final Logger logger;
    private JSONObject jsonRsp;
    private FedexResponse response = new FedexResponse();

    public FxAddrValClient(GetAuth auth, FxAppParms appParms, Logger logger) {
        this.auth = auth;
        this.appParms = appParms;
        this.logger = logger;
    }

    public FedexResponse requestAddrVal(FedexShipmentRequest request) {
        this.request = request;
        //FedexResponse response = new FedexResponse();
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("addressesToValidate", bldAddrToVal());

        String input = jsonRequest.toString();
        //System.out.println("request = " + input);

        if (callHttp(input)) {
            prcFxResponse();
        }

        return this.response;
    }

    private JSONArray bldAddrToVal() {
        JSONArray jArry = new JSONArray();
        JSONObject jobj = new JSONObject();
        jobj.put("address", bldAddress());
        jArry.add(jobj);

        return jArry;
    }

    private JSONObject bldAddress() {
        JSONObject jobj = new JSONObject();

        jobj.put("streetLines", bldAdrLines());
        jobj.put("city", request.getRecCity());
        jobj.put("stateOrProvinceCode", request.getRecStat());
        jobj.put("postalCode", request.getRecZip());
        jobj.put("countryCode", request.getRecCountry());

        return jobj;
    }

    private JSONArray bldAdrLines() {
        JSONArray jarr = new JSONArray();
        request.getRecAdr().forEach(addr -> {
            if (!addr.trim().isEmpty()) {
                jarr.add(addr);
            }
        });
        return jarr;
    }

    private boolean callHttp(String input) {
        boolean ret = false;
        Client client1 = Client.create();
        client1.addFilter(new GZIPContentEncodingFilter(false));
        String url = appParms.getServer().trim()
                + appParms.getAddrValPath();
        MultivaluedMap<String, String> hdrLst;
        String authToken = "Bearer " + auth.getAccessToken();

        WebResource webResource = client1.resource(url);
        ClientResponse httpResponse = webResource.accept("application/json")
                .header("content-type", "application/json")
                .header("X-locale", "en_US")
                .header("authorization", authToken)
                .post(ClientResponse.class, input);
        // String output = httpResponse.getEntity(String.class);
        String output = httpResponse.getEntity(String.class);
        // String s1 = httpResponse.toString();
        //InputStream is = httpResponse.getEntityInputStream();
        if (appParms.isDebug()) {
            logger.log(Level.ALL, "request is {0}", input);
            logger.log(Level.ALL, "URL= {0}", url);
            logger.log(Level.ALL, "Token is {0}", authToken);
            logger.log(Level.ALL, "Response is {0}", output);
            System.out.println("Response =" + output);
        }
        
         if (httpResponse.getLength() > 0) {
            jsonRsp = (JSONObject) JSONSerializer.toJSON(output);
        }
        // this.jsonRsp = (JSONObject) JSONSerializer.toJSON(output);

        this.response.setResponceStatus(httpResponse.getStatus());
        if (jsonRsp.containsKey("error_description")) {
            this.response.setMessage(jsonRsp.getString("error_description"));
        } else {
            this.response.setMessage(httpResponse.getClientResponseStatus().toString());
        }
        // System.out.println(httpResponse.getEntity(String.class).toString());
//        System.out.println("httpResponse ="+ s1);
        if (httpResponse.getStatus() != 200) {
            hdrLst = httpResponse.getHeaders();
            DateTimeFormatter formatterYmd = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");

            logger.log(Level.SEVERE, "Error fedex Address Validation respons status = {0} Ptkt = {1} request= {2} token = {3} "
                    + "url = {4} response= {5} time auth expired {6} ",
                    new Object[]{httpResponse.getStatus()
                            , request.getPtkt()
                            , input
                            , authToken
                            , url
                            , output
                            , formatterYmd.format(auth.getExpTime())});

            this.response.setAddrValAlertType(NotificationSeverityType._FAILURE);
//            System.out.println("Ship client HTTP status = " + httpResponse.getStatus());
//            System.out.println("URL = " + url);
//            System.out.println("auth Token =" + authToken);
//            System.out.println("request =" + input);
//            System.out.println("respone =" + output);
            for (String key : hdrLst.keySet()) {
                System.out.println(key + "=" + hdrLst.getFirst(key));

            }
            this.response.setMsgSeverity(NotificationSeverityType._FAILURE);
            this.response.setMessage(Utilities.rtvRspMessage(jsonRsp));
        } else {
            ret = true;
            this.response.setMsgSeverity(NotificationSeverityType._SUCCESS);
            this.response.setAddrValAlertType("SUCCESS");
        }

        return ret;
    }

    private void prcFxResponse() {
        //JSONObject jobj = new JSONObject();

        //if (jsonRsp.containsKey("Response")) {
        //    jobj = jsonRsp.getJSONObject("Response");
        if (jsonRsp.containsKey("output")) {
            prcOutputs(jsonRsp);
        }
        //}
    }

    private void prcOutputs(JSONObject output) {
        JSONObject jobj = new JSONObject();
        jobj = output.getJSONObject("output");
        if (jobj.containsKey("alerts")) {
            prcAlerts(jobj);
        }
        if (jobj.containsKey("resolvedAddresses")) {
            prcResolvedAddr(jobj);
        }
    }

    private void prcAlerts(JSONObject alerts) {
        JSONArray jarr = new JSONArray();
        JSONObject jobj = new JSONObject();
        jarr = alerts.getJSONArray("alerts");

        //for (int i = 0; i< jarr.size(); i ++ ) {
        jobj = jarr.getJSONObject(0);
        if (jobj.containsKey("code")) {
            response.setAddrValAlertCode(jobj.getString("code"));
        }

        if (jobj.containsKey("message")) {
            response.setAddrValAlertMsg(jobj.getString("message"));
        }
        if (jobj.containsKey("alertType")) {
            response.setAddrValAlertType(jobj.getString("alertType"));
        }

    }

    private void prcResolvedAddr(JSONObject resolvedAddr) {
        JSONArray jArr = new JSONArray();
        JSONObject jObj;
        // = new JSONObject();   

        jArr.add(resolvedAddr.get("resolvedAddresses"));
        if (jArr instanceof JSONArray) {
            JSONArray jarr2 = jArr.getJSONArray(0);
            jObj = jarr2.getJSONObject(0);
        } else {
            jObj = jArr.getJSONObject(0);
        }

        if (jObj.containsKey("classification")) {
            response.setAddrValClassification(jObj.getString("classification"));
        }
    }
}
