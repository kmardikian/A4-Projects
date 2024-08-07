/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fedexrate;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import fedexshipping.FxAddrValClient;
import fedexshipping.FxAppParms;
import fedexshipping.GetAuth;
import fedexshipping.Utilities;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;
import model.FXParms;
import model.RateInfo;
import models.FedexResponse;
import models.FedexShipmentRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Khatchik
 */
public class FedexRate {

    private final FedexShipmentRequest rateParm;
    private final FXParms fxParms;
    private final Logger logger;
    private final GetAuth fxAuth;
    private final FxAppParms shprAuthParms;
    JSONObject jsonRateReq = new JSONObject();
    JSONObject jsonResponse = new JSONObject();

    public FedexRate(GetAuth fxAuth, FxAppParms shprAuthParms, FedexShipmentRequest rateParm, FXParms fxParms, Logger logger) {
        this.rateParm = rateParm;
        this.fxParms = fxParms;
        this.logger = logger;
        this.fxAuth = fxAuth;
        this.shprAuthParms = shprAuthParms;
    }

    public RateInfo rtvRate() {
        //JSONObject jobj = new JSONObject();
        jsonRateReq.put("accountNumber", bldAcctNo());
        jsonRateReq.put("requestedShipment", bldReqShp());
        return callFedexRate(jsonRateReq);

    }

    private JSONObject bldAcctNo() {
        JSONObject jobj = new JSONObject();
        jobj.put("value", fxParms.getFxShprNo());

        return jobj;
    }

    private JSONObject bldReqShp() {
        JSONObject jobj = new JSONObject();
        jobj.put("shipper", bldShpr());
        jobj.put("recipient", bldRecipient());
        jobj.put("serviceType", rateParm.getSrvTyp());
        jobj.put("rateRequestType", bldRateReqTyp());
        jobj.put("pickupType", rateParm.getPickupType());
        jobj.put("requestedPackageLineItems", bldPkgLines());

        return jobj;
    }

    private JSONArray bldRateReqTyp() {
        JSONArray jarr = new JSONArray();
        jarr.add("LIST");

        return jarr;
    }

    private JSONObject bldShpr() {
        JSONObject jobj = new JSONObject();
        jobj.put("address", bldShprAddr());

        return jobj;
    }

    private JSONObject bldShprAddr() {
        JSONObject jobj = new JSONObject();
        jobj.put("streetLines", bldShprStrLine());
        jobj.put("city", rateParm.getShipperCity());
        jobj.put("stateOrProvinceCode", rateParm.getShipperStat());
        jobj.put("postalCode", rateParm.getShipperZip());
        jobj.put("countryCode", rateParm.getShipperCountry());
        //jobj.put("residential", "false");

        return jobj;
    }

    private JSONArray bldShprStrLine() {
        JSONArray jArray = new JSONArray();

        for (String addr : rateParm.getShipperAdr()) {
            if (!addr.trim().isEmpty()) {
                jArray.add(addr.trim());
            }
        }

        return jArray;
    }

    private JSONObject bldRecipient() {
        JSONObject jobj = new JSONObject();
        jobj.put("address", bldRecAddr());

        return jobj;
    }

    private JSONObject bldRecAddr() {
        JSONObject jobj = new JSONObject();
        jobj.put("streetLines", bldRecStrLine());
        jobj.put("city", rateParm.getRecCity());
        jobj.put("stateOrProvinceCode", rateParm.getRecStat());
        jobj.put("postalCode", rateParm.getRecZip());
        jobj.put("countryCode", rateParm.getRecCountry());

        FxAddrValClient fxAddrVal = new FxAddrValClient(fxAuth, shprAuthParms, logger);
        FedexResponse fxResponse = fxAddrVal.requestAddrVal(rateParm);
        if (fxResponse.isResidential()) {
            jobj.put("residential", "true");
            if (rateParm.getSrvTyp().equals("FEDEX_GROUND")) {
                rateParm.setSrvTyp("GROUND_HOME_DELIVERY");
            } else {
                jobj.put("residential", "true");
            }
        }

        return jobj;
    }

    private JSONArray bldRecStrLine() {
        JSONArray jArray = new JSONArray();
        for (String addr : rateParm.getRecAdr()) {
            if (!addr.trim().isEmpty()) {
                jArray.add(addr.trim());
            }
        }
        return jArray;
    }

    private JSONArray bldPkgLines() {
        JSONArray jArray = new JSONArray();
        JSONObject jobj = new JSONObject();
        jobj.put("weight", bldPkgWght());
        jArray.add(jobj);

        return jArray;
    }

    private JSONObject bldPkgWght() {
        JSONObject jobj = new JSONObject();
        jobj.put("units", rateParm.getPkgWeight().getUnits());
        jobj.put("value", rateParm.getPkgWeight().getValue());

        return jobj;
    }

    private RateInfo callFedexRate(JSONObject jsonRateReq) {

        //RateInfo rateInfo = new RateInfo();
        RateInfo rateInfo= crtRateInfo();

        Client client1 = Client.create();
        String url = fxParms.getFxServer().trim()
                + fxParms.getFxRatePath().trim();

        String input = jsonRateReq.toString();
        MultivaluedMap<String, String> hdrLst;
        String authToken = "Bearer " + fxAuth.getAccessToken();
        client1.addFilter(new GZIPContentEncodingFilter(false));

        WebResource webResource = client1.resource(url);
        ClientResponse httpResponse = webResource.accept("application/json")
                .header("content-type", "application/json")
                .header("X-locale", "en_US")
                .header("authorization", authToken)
                .post(ClientResponse.class, input);
        // String output = httpResponse.getEntity(String.class);
        String output = httpResponse.getEntity(String.class);
        JSONObject jsonRsp = new JSONObject();
        Map<String, Object> respProp;

        respProp = httpResponse.getProperties();

        if (httpResponse.getLength() > 0) {
            jsonRsp = (JSONObject) JSONSerializer.toJSON(output);
        }

        // String s1 = httpResponse.toString();
        InputStream is = httpResponse.getEntityInputStream();

        if (fxParms.isDebug()) {

            logger.log(Level.INFO, "request is {0}", input);
            logger.log(Level.INFO, "URL= {0}", url);
            logger.log(Level.INFO, "Token is {0}", authToken);
            logger.log(Level.INFO, "Response is {0}", output);
//            System.out.println("response length =" + httpResponse.getLength());
//            System.out.println("return type = " + httpResponse.getType());
//            System.out.println("IS =" + is.toString());
//            System.out.println("has entity? " + httpResponse.hasEntity());
//            System.out.println("Entity=" + httpResponse.toString());
//            System.out.println("status = " + httpResponse.getStatus() + "client status=" + httpResponse.getClientResponseStatus()
//                    + "Entity tag=" + httpResponse.getEntityTag());
//            System.out.println("response =" + output);
//            for (String key : respProp.keySet()) {
//                System.out.println(key + "=" + respProp.get(key));
//
//            }
        }

        rateInfo.setHttpStatus(String.valueOf(httpResponse.getStatus()));

        if (httpResponse.getStatus() != 200) {
            hdrLst = httpResponse.getHeaders();
            logger.log(Level.SEVERE, "Error retreiving fedex rate respons status ={0} request= {1} token = {2} "
                    + " url ={3} response={4}",
                    new Object[]{httpResponse.getStatus(), input, authToken, url, output});
//            for (String key : hdrLst.keySet()) {
//                System.out.println(key + "=" + hdrLst.getFirst(key));
//            }
            rateInfo.setMessage(Utilities.rtvRspMessage(jsonRsp));
            rateInfo.setHttpStatus(String.valueOf(httpResponse.getStatus()));
            rateInfo.setMessage(Utilities.rtvRspMessage(jsonRsp));
            return rateInfo;
        }

        //JSONObject jsonRsp = new JSONObject();
        //  System.out.println("rsponse=" + output);
        return bldRateInfo(jsonRsp, httpResponse.getStatus());

    }
    private RateInfo crtRateInfo() {
        RateInfo rateInfo = new RateInfo();
        rateInfo.setBasChg(" ");
        rateInfo.setBasCurr(" ");
        //rateInfo.setCurr(" ");
        rateInfo.setHttpStatus(" ");
        rateInfo.setMessage(" ");
        rateInfo.setSvcOptChg(" ");
        rateInfo.setSvcOptCurr("");
        rateInfo.setTotChg(" ");
        rateInfo.setTotCurr(" ");
        rateInfo.setTotRate(" ");
        rateInfo.setTrnChg(" ");
        rateInfo.setTrnCurr(" ");
        return rateInfo;
    }

    private RateInfo bldRateInfo(JSONObject response, Integer httpStatus) {
        //RateInfo rateInfo = new RateInfo();
        RateInfo rateInfo= crtRateInfo();
        rateInfo.setHttpStatus(httpStatus.toString());
        if (response.containsKey("output")) {
            JSONObject jOutput = response.getJSONObject("output");
            if (jOutput.containsKey("rateReplyDetails")) {
                JSONObject jRateReply = jOutput.getJSONArray("rateReplyDetails").getJSONObject(0);
                if (jRateReply.containsKey("ratedShipmentDetails")) {
                    JSONObject jRatedShpmtDet = jRateReply.getJSONArray("ratedShipmentDetails").getJSONObject(0);
                    if (jRatedShpmtDet.containsKey("currency")) {
                        String curr = jRatedShpmtDet.getString("currency");
                       // logger.log(Level.INFO, "setting currency " + curr);
                        rateInfo.setTrnCurr(curr);
                        rateInfo.setSvcOptCurr(curr);
                        rateInfo.setTotCurr(curr);
                        rateInfo.setBasCurr(curr);
                        // rateInfo.setCurr(curr);
                    }
                    if (jRatedShpmtDet.containsKey("totalBaseCharge")) {
                        // logger.log(Level.INFO, "setting total base Charge " + jRatedShpmtDet.getString("totalBaseCharge"));
                        rateInfo.setBasChg(jRatedShpmtDet.getString("totalBaseCharge"));
                    }

                    if (jRatedShpmtDet.containsKey("totalNetFedExCharge")) {
                       // logger.log(Level.INFO, "setting netFedex charte "+ jRatedShpmtDet.getString("totalNetFedExCharge"));
                        rateInfo.setTotChg(jRatedShpmtDet.getString("totalNetFedExCharge"));
                    }
                    if (jRatedShpmtDet.containsKey("shipmentRateDetail")) {
                        JSONObject jshpmtRateDet = jRatedShpmtDet.getJSONObject("shipmentRateDetail");
                        if (jshpmtRateDet.containsKey("totalSurcharges")) {
                         //    logger.log(Level.INFO, "setting total surcharges " +  jshpmtRateDet.getString("totalSurcharges"));
                            rateInfo.setSvcOptChg(jshpmtRateDet.getString("totalSurcharges"));
                        }
                    }
                }
            }
        }

        return rateInfo;
    }

}
