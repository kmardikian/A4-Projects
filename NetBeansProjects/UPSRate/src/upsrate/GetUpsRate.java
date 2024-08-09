/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upsrate;

import model.Package;
import model.RateParm;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.Base64;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;
import model.RateInfo;
import model.UPSParms;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Khatchik
 */
public class GetUpsRate {

    private final RateParm rateParm;
    private final UPSParms upsParms;
    private final As400UPSAuth auth;
    private final Logger logger;
    JSONObject jsonRateReq = new JSONObject();
    JSONObject jsonResponse = new JSONObject();

    public GetUpsRate(UPSParms upsParms,
            As400UPSAuth auth,
            RateParm rateParm,
            Logger logger) {
        this.upsParms = upsParms;
        this.rateParm = rateParm;
        this.auth = auth;
        this.logger = logger;
        // rtvUPSRate();
    }

    public RateInfo rtvUPSRate() {
        //JSONObject jobj = new JSONObject();
        jsonRateReq.put("RateRequest", bldRateReq());
        return callUPSRate(jsonRateReq);

    }

    private JSONObject bldRateReq() {
        JSONObject jobj = new JSONObject();
        jobj.put("Request", bldRequest());
        jobj.put("Shipment", bldShipment());

        return jobj;
    }

    private JSONObject bldRequest() {
        JSONObject jobj = new JSONObject();
        jobj.put("RequestOption", "Rate");
        //jobj.put("Shipment", bldShipment());
        return jobj;
    }

    private JSONObject bldShipment() {
        JSONObject jobj = new JSONObject();
        jobj.put("Shipper", bldShipper());
        jobj.put("ShipTo", bldShipTo());
        jobj.put("ShipFrom", bldShipFrom());
        jobj.put("PaymentDetails", bldPaymentDtl());
        jobj.put("Service", bldServiceDtl());
        jobj.put("NumOfPieces", String.valueOf(rateParm.getPackageList().size()));
        jobj.put("Package", bldPackages());

        return jobj;
    }

    private JSONObject bldShipper() {
        JSONObject jobj = new JSONObject();
        jobj.put("Name", rateParm.getShprName());
        jobj.put("ShipperNumber", rateParm.getShprNo());
        jobj.put("Address", bldShprAddr());

        return jobj;

    }

    private JSONObject bldShprAddr() {
        JSONObject jobj = new JSONObject();
        jobj.put("AddressLine", bldShprAdrLines());
        jobj.put("City", rateParm.getShprCty());
        jobj.put("StateProvinceCode", rateParm.getShprStt());
        jobj.put("PostalCode", rateParm.getShprZip());
        jobj.put("CountryCode", rateParm.getShprCntry());

        return jobj;
    }

    private JSONArray bldShprAdrLines() {
        JSONArray jArr = new JSONArray();
        if (!rateParm.getShprAddr1().trim().isEmpty()) {
            JSONObject jobj = new JSONObject();
            jobj.put("ShipperAddressLine", rateParm.getShprAddr1().trim());
            jArr.add(jobj);
        }

        if (!rateParm.getShprAddr2().trim().isEmpty()) {
            JSONObject jobj = new JSONObject();
            jobj.put("ShipperAddressLine", rateParm.getShprAddr2().trim());
            jArr.add(jobj);
        }

        if (!rateParm.getShprAddr3().trim().isEmpty()) {
            JSONObject jobj = new JSONObject();
            jobj.put("ShipperAddressLine", rateParm.getShprAddr3().trim());
            jArr.add(jobj);
        }

        return jArr;
    }

    private JSONObject bldShipTo() {
        JSONObject jobj = new JSONObject();
        jobj.put("Name", rateParm.getToName());
        jobj.put("Address", bldToaddr());
        return jobj;
    }

    private JSONObject bldToaddr() {
        JSONObject jobj = new JSONObject();
        jobj.put("AddressLine", bldToAdrLines());
        jobj.put("City", rateParm.getToCity());
        jobj.put("StateProvinceCode", rateParm.getToStt());
        jobj.put("PostalCode", rateParm.getToZip());
        jobj.put("CountryCode", rateParm.getToCntry());

        return jobj;
    }

    private JSONArray bldToAdrLines() {
        JSONArray jArr = new JSONArray();
        if (!rateParm.getToAddr1().trim().isEmpty()) {
            JSONObject jobj = new JSONObject();
            jobj.put("ShipToAddressLine", rateParm.getToAddr1().trim());
            jArr.add(jobj);
        }

        if (!rateParm.getToAddr2().trim().isEmpty()) {
            JSONObject jobj = new JSONObject();
            jobj.put("ShipToAddressLine", rateParm.getToAddr2().trim());
            jArr.add(jobj);
        }

        if (!rateParm.getToAddr3().trim().isEmpty()) {
            JSONObject jobj = new JSONObject();
            jobj.put("ShipToAddressLine", rateParm.getToAddr3().trim());
            jArr.add(jobj);
        }

        return jArr;

    }

    private JSONObject bldShipFrom() {
        JSONObject jobj = new JSONObject();

        jobj.put("Name", rateParm.getFromName());
        jobj.put("Address", bldFromAddr());

        return jobj;
    }

    private JSONObject bldFromAddr() {
        JSONObject jobj = new JSONObject();
        jobj.put("AddressLine", bldFromAdrLines());
        jobj.put("City", rateParm.getFromCty());
        jobj.put("StateProvinceCode", rateParm.getFromStt());
        jobj.put("PostalCode", rateParm.getFromZip());
        jobj.put("CountryCode", rateParm.getFromCntry());

        return jobj;
    }

    private JSONArray bldFromAdrLines() {
        JSONArray jArr = new JSONArray();
        if (!rateParm.getFromAddr1().trim().isEmpty()) {
            JSONObject jobj = new JSONObject();
            jobj.put("ShipFromAddressLine", rateParm.getFromAddr1().trim());
            jArr.add(jobj);
        }

        if (!rateParm.getFromAddr2().trim().isEmpty()) {
            JSONObject jobj = new JSONObject();
            jobj.put("ShipFromAddressLine", rateParm.getFromAddr2().trim());
            jArr.add(jobj);
        }

        if (!rateParm.getFromAddr3().trim().isEmpty()) {
            JSONObject jobj = new JSONObject();
            jobj.put("ShipFromAddressLine", rateParm.getFromAddr3().trim());
            jArr.add(jobj);
        }

        return jArr;

    }

    private JSONObject bldPaymentDtl() {
        JSONObject jobj = new JSONObject();
        jobj.put("ShipmentCharge", bldShpmtChg());

        return jobj;

    }

    private JSONObject bldShpmtChg() {
        JSONObject jobj = new JSONObject();
        jobj.put("Type", "01");
        jobj.put("BillShipper", bldBillShipper());

        return jobj;
    }

    private JSONObject bldBillShipper() {
        JSONObject jobj = new JSONObject();
        jobj.put("AccountNumber", rateParm.getShprNo());

        return jobj;
    }

    private JSONObject bldService() {
        JSONObject jobj = new JSONObject();
        jobj.put("Service", bldServiceDtl());

        return jobj;
    }

    private JSONObject bldServiceDtl() {
        JSONObject jobj = new JSONObject();
        jobj.put("Code", rateParm.getSvcCode());
        jobj.put("Description", rateParm.getSvcCodeDesc());

        return jobj;

    }

    private JSONArray bldPackages() {
        JSONArray jarr = new JSONArray();
        JSONObject jobj = new JSONObject();

        rateParm.getPackageList().forEach(pkg -> {
            jobj.put("PackagingType", bldPackageTyp(pkg));
            if (!pkg.getDimUomDes().trim().isEmpty()) {
                jobj.put("Dimensions", bldPkgDim(pkg));
            }
            jobj.put("PackageWeight", bldPkgWght(pkg));
            jarr.add(jobj);

        });

        return jarr;
    }

    private JSONObject bldPackageTyp(Package pkg) {
        JSONObject jobj = new JSONObject();
        jobj.put("Code", pkg.getPkgTypCd());
        jobj.put("Description", pkg.getPkgDesc());

        return jobj;
    }

    private JSONObject bldPkgDim(Package pkg) {
        JSONObject jobj = new JSONObject();
        jobj.put("UnitOfMeasurement", bldDimUom(pkg));
        jobj.put("Length", pkg.getLength());
        jobj.put("Width", pkg.getWidth());
        jobj.put("Height", pkg.getHeight());

        return jobj;
    }

    private JSONObject bldDimUom(Package pkg) {
        JSONObject jobj = new JSONObject();
        jobj.put("Code", pkg.getDimUom());
        jobj.put("Description", pkg.getDimUomDes());
        return jobj;
    }

    private JSONObject bldPkgWght(Package pkg) {
        JSONObject jobj = new JSONObject();
        jobj.put("UnitOfMeasurement", bldWghUom(pkg));
        jobj.put("Weight", pkg.getWeight());

        return jobj;
    }

    private JSONObject bldWghUom(Package pkg) {
        JSONObject jobj = new JSONObject();
        jobj.put("Code", pkg.getWeightUom());
        jobj.put("Description", pkg.getWeightUomDes());

        return jobj;
    }

    private RateInfo callUPSRate(JSONObject jobj) {
        RateInfo rateInfo = initRateInfo();
        String input = jobj.toString();
        Client client = Client.create();
        MultivaluedMap<String, String> hdrLst;
        String url = upsParms.getUpsServer() + upsParms.getUpsRatePath();

        WebResource webResource = client.resource(url);

        ClientResponse response = webResource.accept("application/json")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + auth.getToken().trim())
                .post(ClientResponse.class, input);

        // return false;
        String output = response.getEntity(String.class);
        JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
        rateInfo.setHttpStatus(String.valueOf(response.getStatus()));

        //System.out.println("response=" + output);
        if (response.getStatus() != 200) {
            //System.out.println("http Failed status = " + response.getStatus());
            logger.log(Level.SEVERE, "URL={0}", url);
            logger.log(Level.SEVERE, "request={0}", input);
            logger.log(Level.SEVERE, "response={0}", output);
            logger.log(Level.SEVERE, "auth token ={0}", auth.getToken());
            rateInfo.setMessage(getErrMessage(jsonOutput));

            hdrLst = response.getHeaders();
            for (String key : hdrLst.keySet()) {
                System.out.println(key + "=" + hdrLst.getFirst(key));

            }
            logger.getLogger(GetUpsRate.class.getName()).log(Level.SEVERE, null, "Failed : HTTP error code :" + response.getStatus());
            return rateInfo;
//            throw new RuntimeException("Failed : HTTP error code : "
//                    + response.getStatus());
        }
//        String output = response.getEntity(String.class);
        if (upsParms.isDebug()) {
            logger.log(Level.INFO, "request =" + input);
            logger.log(Level.INFO, "response=" + output);
            logger.log(Level.INFO, "URL = " + url);
        }
        //  JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
        JSONObject ratedShipment = new JSONObject();
        JSONObject ratedPackage = new JSONObject();
//        if (jsonOutput.containsKey("Response")) {
//            JSONObject jsonResponse = jsonOutput.getJSONObject("Response");
//            if (jsonResponse.containsKey("ResponseStatus")) {
//                JSONObject jResponseStat = jsonResponse.getJSONObject("ResponseStatus");
//                if (jResponseStat.containsKey("Description")) {
//                    rateInfo.setMessage(jResponseStat.getString("Description"));
//                }
//            }
//        }

        if (jsonOutput.containsKey("RateResponse")) {
            JSONObject jsonRateResponse = jsonOutput.getJSONObject("RateResponse");
            if (jsonRateResponse.containsKey("Response")) {
                JSONObject jsonResponse = jsonRateResponse.getJSONObject("Response");
                if (jsonResponse.containsKey("ResponseStatus")) {
                    JSONObject jResponseStat = jsonResponse.getJSONObject("ResponseStatus");
                    if (jResponseStat.containsKey("Description")) {
                        rateInfo.setMessage(jResponseStat.getString("Description"));
                    }
                }
            }
            if (jsonRateResponse.containsKey("RatedShipment")) {
                ratedShipment = jsonRateResponse.getJSONObject("RatedShipment");
            }
        } else {
            if (jsonOutput.containsKey("RatedShipment")) {
                ratedShipment = jsonResponse.getJSONObject("RatedShipment");
            }
        }
        if (ratedShipment.containsKey("RatedPackage")) {
            ratedPackage = ratedShipment.getJSONArray("RatedPackage").getJSONObject(0);
        } else {
            ratedPackage = ratedShipment;
        }

        if (ratedPackage.containsKey("TransportationCharges")) {
            JSONObject jsonTrnCharges = ratedPackage.getJSONObject("TransportationCharges");
            if (jsonTrnCharges.containsKey("MonetaryValue")) {
                rateInfo.setTrnChg(jsonTrnCharges.getString("MonetaryValue"));
            }
            if (jsonTrnCharges.containsKey("CurrencyCode")) {
                rateInfo.setTrnCurr(jsonTrnCharges.getString("CurrencyCode"));
            }
        }

        if (ratedPackage.containsKey("ServiceOptionsCharges")) {
            JSONObject jsonSvcOptCharges = ratedPackage.getJSONObject("ServiceOptionsCharges");
            if (jsonSvcOptCharges.containsKey("MonetaryValue")) {
                rateInfo.setSvcOptChg(jsonSvcOptCharges.getString("MonetaryValue"));
            }
            if (jsonSvcOptCharges.containsKey("CurrencyCode")) {
                rateInfo.setSvcOptCurr(jsonSvcOptCharges.getString("CurrencyCode"));
            }
        }

        if (ratedPackage.containsKey("TotalCharges")) {
            JSONObject jsonTotCharges = ratedPackage.getJSONObject("TotalCharges");
            if (jsonTotCharges.containsKey("MonetaryValue")) {
                // String totChg = jsonTotCharges.getString("MonetaryValue");
                rateInfo.setTotRate(jsonTotCharges.getString("MonetaryValue"));
                // System.out.println("total charge =" + totChg );
            }
            if (jsonTotCharges.containsKey("CurrencyCode")) {
                rateInfo.setTotCurr(jsonTotCharges.getString("CurrencyCode"));
            }
        }

        if (ratedPackage.containsKey("BaseServiceCharge")) {
            JSONObject jsonBasChg = ratedPackage.getJSONObject("BaseServiceCharge");
            if (jsonBasChg.containsKey("MonetaryValue")) {
                // String totChg = jsonTotCharges.getString("MonetaryValue");
                rateInfo.setBasChg(jsonBasChg.getString("MonetaryValue"));
                // System.out.println("total charge =" + totChg );
            }
            if (jsonBasChg.containsKey("CurrencyCode")) {
                rateInfo.setBasCurr(jsonBasChg.getString("CurrencyCode"));
            }
        }

        return rateInfo;

    }
    /*
     the below method to initialize rate infor was needed for AS400 
    otherwise we get null pointer exceptions in AS400 when not all properties are not set
    */
    private RateInfo initRateInfo() {
        
        RateInfo rateInfo = new RateInfo();
        rateInfo.setBasChg("");
        rateInfo.setBasCurr("");
        rateInfo.setHttpStatus("");
        rateInfo.setMessage("");
        rateInfo.setSvcOptChg("");
        rateInfo.setSvcOptCurr("");
        rateInfo.setTotChg("");
        rateInfo.setTotCurr("");
        rateInfo.setTotRate("");
        rateInfo.setTrnChg("");
        rateInfo.setTrnCurr("");
        
        return rateInfo;
    }

    private String getErrMessage(JSONObject jobj) {
        String msg = "";
        if (jobj.containsKey("response")) {
            JSONObject jResp = jobj.getJSONObject("response");
            if (jResp.containsKey("errors")) {
                JSONArray jErrs = jResp.getJSONArray("errors");
                JSONObject jErr = jResp.getJSONArray("errors").getJSONObject(0);
                if (jErr.containsKey("message")) {
                    return jErr.getString("message");
                }
            }
        }

        return msg;
    }

}
