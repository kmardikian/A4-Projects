package upsqv_v2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import static com.a4.quantumview.QuantumViewMain.rtvQVData;
import as400Files.UpsqvMpk;
import as400Files.UpsqvMf;
import as400Files.Reference;
import as400Files.*;
import com.a4.utils.ConnectAs400;
//import com.ibm.as400.access.AS400;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.scene.control.Alert;
import javax.ws.rs.core.MultivaluedMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author khatchik
 */
public class QVGetData {

    private final QVParms parm;
    private Connection con;
    //  private DriverManager driverManager;
    //  private AS400 as400;
    private UpsqvOr upsqvOr;
    private UpsqvDl upsqvDl;
    private UpsqvMf upsqvMf;
    private UpsqvMpk upsqvMpk;
    private UpsqvGn upsqvGn;
    private String bookmark = " ";
    private String strReq;
    private UPSAuth upsAuth;
    private GetAuth auth;
    private final Logger logger;

    QVGetData(QVParms qvparms, Logger logger) {
        parm = qvparms;
        this.logger = logger;
    }

    public void qvPrcQv() {

        if (As400Connection() == true) {
            try {

                upsqvOr = new UpsqvOr(con, parm);
                upsqvDl = new UpsqvDl(con, parm);
                upsqvMf = new UpsqvMf(con, parm);
                upsqvGn = new UpsqvGn(con, parm);
                upsqvMpk = new UpsqvMpk(con, parm);
                upsAuth = new UPSAuth(con, parm);  // as400 auth table
                auth = new GetAuth(parm, upsAuth);
                auth.getAuth();

                do {
                    JSONObject qvResponse = rtvQVData();
                    JSONObject jsonResponse = qvResponse.getJSONObject("Response");
                    bookmark = " ";
                    bookmark = qvResponse.optString("Bookmark");
                    //System.out.println("Bookmark=" + bookmark);
                    String status = getResponseStat(jsonResponse);
                    if ("1".equals(status) && qvResponse.containsKey("QuantumViewEvents")) {
                        parseQVData(qvResponse);
                    }
                } while (!bookmark.trim().isEmpty());

            } catch (SQLException ex) {
                System.out.println("Error connecting to AS400 "
                        + ex.getMessage());
                logger.log(Level.SEVERE, "Error connecting to AS400 {0}", ex.getMessage());                
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.out.println("Error closing connection");
                    logger.log(Level.SEVERE, "Error closing connection AS400 {0}", ex.getMessage());  
                }
            }
        }
    }

    private boolean As400Connection() {
        boolean conOk = false;
        ConnectAs400.setJtopen();
        ConnectAs400.setConParms(parm.getAs400SystemName(), parm.getAs400UserId(),
                parm.getAs400Password(), parm.getAs400Lib());
        try {
            ConnectAs400.conAs400();
            ConnectAs400.As400JobInfo as400Info;
            as400Info = ConnectAs400.getAs400JobInfo();
            System.out.println("AS400Job started:" + as400Info.getJobnNum()
                    + "/" + as400Info.getJobUser()
                    + "/" + as400Info.getJobName() );
            logger.log(Level.INFO, "AS400Job started: {0}", as400Info.getJobnNum()
                    + "/" + as400Info.getJobUser()
                    + "/" + as400Info.getJobName() 
            );  
            con = ConnectAs400.getConnection();
            conOk = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            System.out.println("Error Connecting to AS400");
            logger.log(Level.INFO, "Error Connecting to AS400 {0}", ex.getMessage());
            conOk = false;
        }

//        try {
//            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
//            
//            con = DriverManager.getConnection("jdbc:as400://"
//                    + parm.getAs400SystemName(),
//                    parm.getAs400UserId(),
//                    parm.getAs400Password());
//            conOk = true;
//        } catch (ClassNotFoundException ex) {
//            System.out.println(ex.getMessage());
//            System.out.println("Error Connecting to AS400");
//            conOk = false;
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            System.out.println("Error Connecting to AS400");
//            conOk = false;
//        }
        return conOk;
    }

    private JSONObject rtvQVData() {

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("QuantumViewRequest", getQvRequest());
        // JSONObject jsonQvObj = new JSONObject();
//        JSONObject jsonQvReqReq = new JSONObject();
//        JSONObject jsonQvTranRef = new JSONObject();
//        JSONObject jsonQvReqReqTranRef = new JSONObject();
        MultivaluedMap<String, String> hdrLst;
        String input;
        Client client = Client.create();
        WebResource webResource = client.resource(parm.getUpsServer() + parm.getQvPath());
//        JSONObject jsonAccessReq = new JSONObject();
//        JSONObject jsonSubscReq = new JSONObject();
//        jsonAccessReq.put("AccessLicenseNumber", parm.getQvLicense());
//        jsonAccessReq.put("UserId", parm.getQvUserId());
//        jsonAccessReq.put("Password", parm.getQvPassWord());
//        jsonObj.put("AccessRequest", jsonAccessReq);
//
//        jsonQvTranRef.put("CustomerContext", "Your Customer Context");
//        jsonQvReqReqTranRef.put("TransactionReference", jsonQvTranRef);
//
//        //jsonQvTranRef.put("Request",jsonQvReqReqTranRef );
//        jsonQvReqReqTranRef.put("RequestAction", "QVEvents");
//
//        jsonQvReqReqTranRef.put("RequestOption", "01");
//        jsonQvReqReq.put("Request", jsonQvReqReqTranRef);
//
//        //jsonQvObj.put("QuantumViewReques", jsonQvTranRef);
//        jsonSubscReq.put("Name", "outbound");
//
//        jsonQvReqReq.put("SubscriptionRequest", jsonSubscReq);
//        if (!" ".equals(parm.getStartDt())) {
//            JSONObject jsonDatTimeRange = new JSONObject();
//            jsonDatTimeRange.put("BeginDateTime", parm.getStartDt());
//            jsonDatTimeRange.put("EndDateTime", parm.getEndDt());
//            jsonQvReqReq.put("DateTimeRange", jsonDatTimeRange);
//        }

//        jsonObj.put("QuantumViewRequest", jsonQvReqReq);
//        if (!" ".equals(bookmark)) {
//            jsonObj.put("Bookmark", bookmark);
//        }
        input = jsonObj.toString();
        strReq = jsonObj.toString();
        System.out.println("Request:" + input);
        logger.log(Level.INFO, "Request: {0}", input);  
        

        ClientResponse response = webResource.accept("application/json")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + upsAuth.getToken())
                .post(ClientResponse.class, input);

        if (response.getStatus() != 200) {
            String output = response.getEntity(String.class);
            System.out.println("http Failed status = " + response.getStatus());
            logger.log(Level.SEVERE, "http Failed status =: {0} \n URL= {1} , \n Token ={2} \n Response={3}",
                    new Object[] {response.getStatus()
                            ,parm.getUpsServer() + parm.getQvPath() 
                            ,upsAuth.getToken() 
                            ,output
                    });  
            hdrLst = response.getHeaders();
            for (String key : hdrLst.keySet()) {
                System.out.println(key + "=" + hdrLst.getFirst(key));
            }
            if (response.getStatus() >= 400 && response.getStatus() <= 429) {
               // String output = response.getEntity(String.class);
                System.out.println("Url =" + parm.getUpsServer() + parm.getQvPath());
                System.out.println("token =" + upsAuth.getToken());
                System.out.println("Response = " + output);
                
            }
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        String output = response.getEntity(String.class);
        logger.log(Level.INFO, "Response: {0}", output);  
        System.out.println("response=" + output );
        // System.out.println("output:" + output);
        JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
        JSONObject jsonQvResponse = jsonOutput.getJSONObject("QuantumViewResponse");
        return jsonQvResponse;
    }

    private JSONObject getQvRequest() {
        JSONObject request = new JSONObject();
        request.put("Request", bldRequest());
        if (!" ".equals(bookmark)) {
           request.put("Bookmark", bookmark);
        } else {
            if (!" ".equals(parm.getStartDt())) {
                request.put("DateTimeRange", getDateTimerange());
            }
        }
        request.put("SubscriptionRequest", getSubscription());
        //

        return request;
    }

    private JSONObject bldRequest() {
        JSONObject request = new JSONObject();
        request.put("TransactionReference", getCustContext());
        request.put("RequestAction", "QVEvents");

        return request;
    }

    private JSONObject getCustContext() {
        JSONObject context = new JSONObject();
        context.put("CustomerContext", "A4 context");
        context.put("XpciVersion", "1.0007");
        return context;
    }

    private JSONObject getSubscription() {
        JSONObject jobj = new JSONObject();
        jobj.put("Name", "outbound");
//        if (!" ".equals(bookmark)) {
//            jobj.put("Bookmark", bookmark);
//        } else {
//            if (!" ".equals(parm.getStartDt())) {
//                jobj.put("DateTimeRange", getDateTimerange());
//            }
//        }
        return jobj;

    }

    private JSONObject getDateTimerange() {
        JSONObject jobj = new JSONObject();
        jobj.put("BeginDateTime", parm.getStartDt());
        jobj.put("EndDateTime", parm.getEndDt());

        return jobj;
    }
//    private JSONObject getXpcVer() {
//        JSONObject xpcVer = new JSONObject();
//        xpcVer.put("XpciVersion", "1.0007");
//        return xpcVer;
//    }

    public String getResponseStat(JSONObject response) {
        String status;
        status = response.getString("ResponseStatusCode");
        System.out.println("Status is:" + status);
        System.out.println("Response Description: "
                + response.getString("ResponseStatusDescription"));
        if (!status.equals("1")) {
            JSONObject jsonError = response.getJSONObject("Error");
            System.out.println("Request:" + strReq);
            System.out.println("ErrorSeverity:" + jsonError.getString("ErrorSeverity"));
            System.out.println("ErrorCode:" + jsonError.getString("ErrorCode"));
            System.out.println("Error Description:" + jsonError.getString("ErrorDescription"));
        }
        return status;

    }

    private void parseQVData(JSONObject qvResponse) {

        JSONObject jsonQvEvents = qvResponse.getJSONObject("QuantumViewEvents");
        Object jsonObj;

        JSONArray jsonSubscipEventsArray;

        jsonObj = jsonQvEvents.get("SubscriptionEvents");
        if (jsonObj instanceof JSONArray) {
            jsonSubscipEventsArray = (JSONArray) jsonObj;
            for (int i = 0; i < jsonSubscipEventsArray.size(); i++) {
                JSONObject subsciptionEvent = jsonSubscipEventsArray.getJSONObject(i);
                prcSubsciptionEvent(subsciptionEvent);
            }
        } else if (jsonObj instanceof JSONObject) {
            //if (jsonQvEvents.containsKey("SubscriptionEvents")) {
            //    JSONObject subsciptionEvent = jsonQvEvents.getJSONObject("SubscriptionEvents");
            JSONObject subsciptionEvent = (JSONObject) jsonObj;
            prcSubsciptionEvent(subsciptionEvent);
            //  }
        }
        //     if (jsonSubscipEventsArray.isArray() ) {
        //         for (int i = 0; i< jsonSubscipEventsArray.size(); i++) {
        //             JSONObject subsciptionEvent = jsonSubscipEventsArray.getJSONObject(i);
        //             prcSubsciptionEvent(subsciptionEvent);
        //         }  
        //     } else  {
        //         if  (jsonQvEvents.containsKey("SubscriptionEvents")) {
        //             JSONObject subsciptionEvent = jsonQvEvents.getJSONObject("SubscriptionEvents");
        //             prcSubsciptionEvent(subsciptionEvent);
        //         }

        //     }
        //   for (int i = 0; i < jsonSubscipEventsArray.size(); i++) {
        //       JSONObject jsonSegment = jsonSubscipEventsArray.getJSONObject(i);
        //       JSONArray jsonSubscipFiles = jsonSegment.getJSONArray("SubscriptionFile");
        //       for (int j = 0; j < jsonSubscipFiles.size(); j++) {
        //           JSONObject jsonSubscrFile = jsonSubscipFiles.getJSONObject(j);
        //           String test1 = jsonSubscrFile.toString();
        //            System.out.println("Subscription file:" + j + " " + test1);
        //            fileName = jsonSubscrFile.getString("FileName");
        //            System.out.println("segment#" + i + test1);
        //            if (jsonSubscrFile.containsKey("Origin")) {
        //                JSONObject jsonOrigin = jsonSubscrFile.getJSONObject("Origin");
        //                parseOrigin(fileName, jsonOrigin);
        //            }
        //            if (jsonSubscrFile.containsKey("Delivery")) {
        //                JSONArray deliveryArray = jsonSubscrFile.getJSONArray("Delivery");
        //                parseDelivery(fileName, deliveryArray);
        //            }
        //            if (jsonSubscrFile.containsKey("Manifest")) {
        //                JSONArray manifestArray = jsonSubscrFile.getJSONArray("Manifest");
        //                 for (int x = 0; x < manifestArray.size(); x++) {
        //                    JSONObject jsonManifest = manifestArray.getJSONObject(x);
        //                    String tst = jsonManifest.toString();
        //                    System.out.println("Manifest rec# " + x + ":" + tst);
        //                    upsqvMf.setFile(fileName);
        //                    parseManifest(fileName, jsonManifest);
        //                }
//
        //             }
        //       }
        //     }
    }

    // process subscription Event
    private void prcSubsciptionEvent(JSONObject subsciptionEvent) {

        Object jsonObj;
        JSONObject jsonWrk;
        String fileName;

        jsonObj = subsciptionEvent.get("SubscriptionFile");
        if (jsonObj instanceof JSONArray) {
            JSONArray jsonSubscipFiles = (JSONArray) jsonObj;
            for (int j = 0; j < jsonSubscipFiles.size(); j++) {
                JSONObject jsonSubscrFile = jsonSubscipFiles.getJSONObject(j);
                prcSubscrFile(jsonSubscrFile);

                // System.out.println("segment#" + i + test1);
            }
        } else if (jsonObj instanceof JSONObject) {
            JSONObject jsonSubscrFile = (JSONObject) jsonObj;
            prcSubscrFile(jsonSubscrFile);
        }
    }

    private void prcSubscrFile(JSONObject jsonSubscrFile) {
        Object jsonObj;
        JSONObject jsonWrk;
        String fileName;
        String strTst;

        String test1 = jsonSubscrFile.toString();
        //System.out.println("Subscription file:" + test1);
        fileName = jsonSubscrFile.getString("FileName");

        if (jsonSubscrFile.containsKey("Origin")) {
            jsonObj = jsonSubscrFile.get("Origin");
            if (jsonObj instanceof JSONArray) {
                JSONArray originArray = (JSONArray) jsonObj;
                for (int x = 0; x < originArray.size(); x++) {
                    jsonWrk = originArray.getJSONObject(x);
                    parseOrigin(fileName, jsonWrk);
                }
            } else if (jsonObj instanceof JSONObject) {
                JSONObject jsonOrigin = (JSONObject) jsonObj;
                parseOrigin(fileName, jsonOrigin);
            } else {
                strTst = jsonObj.toString();
                System.out.println("Invalid Origin json " + strTst);
            }
        }
        if (jsonSubscrFile.containsKey("Delivery")) {
            jsonObj = jsonSubscrFile.get("Delivery");
            if (jsonObj instanceof JSONArray) {
                JSONArray deliveryArray = (JSONArray) jsonObj;
                for (int x = 0; x < deliveryArray.size(); x++) {
                    jsonWrk = deliveryArray.getJSONObject(x);
                    parseDelivery(fileName, jsonWrk);
                }
            } else if (jsonObj instanceof JSONObject) {
                JSONObject jsonDelivery = (JSONObject) jsonObj;
                parseDelivery(fileName, jsonDelivery);
            } else {
                strTst = jsonObj.toString();
                System.out.println("Invalid Delivery json " + strTst);
            }
        }
        if (jsonSubscrFile.containsKey("Manifest")) {
            jsonObj = jsonSubscrFile.get("Manifest");
            if (jsonObj instanceof JSONArray) {
                JSONArray manifestArray = (JSONArray) jsonObj;
                for (int x = 0; x < manifestArray.size(); x++) {
                    JSONObject jsonManifest = manifestArray.getJSONObject(x);
                    String tst = jsonManifest.toString();
                    // System.out.println("Manifest rec# " + x + ":" + tst);
                    parseManifest(fileName, jsonManifest);
                }
            } else if (jsonObj instanceof JSONObject) {
                JSONObject jsonManifest = (JSONObject) jsonObj;
                parseManifest(fileName, jsonManifest);
            }

        }
        if (jsonSubscrFile.containsKey("Generic")) {
            jsonObj = jsonSubscrFile.get("Generic");
            if (jsonObj instanceof JSONArray) {
                JSONArray genericArray = (JSONArray) jsonObj;
                for (int x = 1; x < genericArray.size(); x++) {
                    jsonWrk = genericArray.getJSONObject(x);
                    String tst = jsonWrk.toString();
                    // System.out.println("Generic #" + x + " " + tst);
                    parseGeneric(fileName, jsonWrk);
                }
            } else if (jsonObj instanceof JSONObject) {
                jsonWrk = (JSONObject) jsonObj;
                String tst = jsonWrk.toString();
                // System.out.println("Generic " + tst);
                parseGeneric(fileName, jsonWrk);
            } else {
                strTst = jsonObj.toString();
                System.out.println("Invalid Generic json " + strTst);
            }

        }
    }

    // }
    private void parseOrigin(String fileName, JSONObject jsonOrigin) {
        JSONObject jsonWrk;
        JSONObject jsonWrk2;
        JSONArray jsonArrayWrk;
        Reference referece;
        upsqvOr.setFile(fileName);

        if (jsonOrigin.containsKey("PackageReferenceNumber")) {
            try {
                jsonArrayWrk = jsonOrigin.getJSONArray("PackageReferenceNumber");
                for (int x = 0; x < jsonArrayWrk.size(); x++) {
                    jsonWrk = jsonArrayWrk.getJSONObject(x);
                    referece = new Reference(jsonWrk);
                    if (x == 0) {
                        upsqvOr.setPkNmTg1(referece.getNumber());
                        upsqvOr.setPkRfCd1(referece.getCode());
                        upsqvOr.setPkRfVa1(referece.getValue());
                    } else if (x == 1) {
                        upsqvOr.setPkNmTg2(referece.getNumber());
                        upsqvOr.setPkRfCd2(referece.getCode());
                        upsqvOr.setPkRfVa2(referece.getValue());
                    } else if (x == 2) {
                        upsqvOr.setPkNmTg3(referece.getNumber());
                        upsqvOr.setPkRfCd3(referece.getCode());
                        upsqvOr.setPkRfVa3(referece.getValue());
                    } else if (x == 3) {
                        upsqvOr.setPkNmTg4(referece.getNumber());
                        upsqvOr.setPkRfCd4(referece.getCode());
                        upsqvOr.setPkRfVa4(referece.getValue());
                    } else if (x == 4) {
                        upsqvOr.setPkNmTg5(referece.getNumber());
                        upsqvOr.setPkRfCd5(referece.getCode());
                        upsqvOr.setPkRfVa5(referece.getValue());
                    }
                }
            } catch (Exception ex) {
                jsonWrk = jsonOrigin.getJSONObject("PackageReferenceNumber");
                referece = new Reference(jsonWrk);
                upsqvOr.setPkNmTg1(referece.getNumber());
                upsqvOr.setPkRfCd1(referece.getCode());
                upsqvOr.setPkRfVa1(referece.getValue());

            }
            // if (jsonWrk.containsKey("Number")) {
            //      upsqvOr.setPkNmTg1(jsonWrk.getString("Number"));
            //  }
            //  if (jsonWrk.containsKey("Code")) {
            //      upsqvOr.setPkRfCd1(jsonWrk.getString("Code"));
            //  }
            //  if (jsonWrk.containsKey("Value")) {
            //      upsqvOr.setPkRfVa1(jsonWrk.getString("Value"));
            //  }
        }
        if (jsonOrigin.containsKey("ShipmentReferenceNumber")) {
            try {
                jsonArrayWrk = jsonOrigin.getJSONArray("ShipmentReferenceNumber");
                for (int x = 0; x < jsonArrayWrk.size(); x++) {
                    jsonWrk = jsonArrayWrk.getJSONObject(x);
                    referece = new Reference(jsonWrk);
                    if (x == 0) {
                        upsqvOr.setShNmTg1(referece.getNumber());
                        upsqvOr.setShRfCd1(referece.getCode());
                        upsqvOr.setShRfVa1(referece.getValue());
                    } else if (x == 1) {
                        upsqvOr.setShNmTg2(referece.getNumber());
                        upsqvOr.setShRfCd2(referece.getCode());
                        upsqvOr.setShRfVa2(referece.getValue());
                    } else if (x == 2) {
                        upsqvOr.setShNmTg3(referece.getNumber());
                        upsqvOr.setShRfCd3(referece.getCode());
                        upsqvOr.setShRfVa3(referece.getValue());
                    } else if (x == 3) {
                        upsqvOr.setShNmTg4(referece.getNumber());
                        upsqvOr.setShRfCd4(referece.getCode());
                        upsqvOr.setShRfVa4(referece.getValue());
                    } else if (x == 4) {
                        upsqvOr.setShNmTg5(referece.getNumber());
                        upsqvOr.setShRfCd5(referece.getCode());
                        upsqvOr.setShRfVa5(referece.getValue());
                    }
                }
            } catch (Exception ex) {
                jsonWrk = jsonOrigin.getJSONObject("ShipmentReferenceNumber");
                referece = new Reference(jsonWrk);
                upsqvOr.setShNmTg1(referece.getNumber());
                upsqvOr.setShRfCd1(referece.getCode());
                upsqvOr.setShRfVa1(referece.getValue());
            }
        }
        //jsonWrk = jsonOrigin.getJSONObject("ShipmentReferenceNumber");
        //if (jsonWrk.containsKey("Number")) {
        //    upsqvOr.setShNmTg1(jsonWrk.getString("Number"));
        // }
        // if (jsonWrk.containsKey("Code")) {
        //     upsqvOr.setShRfCd1(jsonWrk.getString("Code"));
        // }
        // if (jsonWrk.containsKey("Value")) {
        //     upsqvOr.setShRfVa1(jsonWrk.getString("Value"));
        // }
        // }
        if (jsonOrigin.containsKey("ShipperNumber")) {
            upsqvOr.setShprNo(jsonOrigin.getString("ShipperNumber"));
        }
        if (jsonOrigin.containsKey("TrackingNumber")) {
            upsqvOr.setTrk(jsonOrigin.getString("TrackingNumber"));
        }
        if (jsonOrigin.containsKey("Date")) {
            upsqvOr.setDat(jsonOrigin.getString("Date"));
        }
        if (jsonOrigin.containsKey("Time")) {
            upsqvOr.setTim(jsonOrigin.getString("Time"));
        }
        if (jsonOrigin.containsKey("ActivityLocation")) {
            jsonWrk = jsonOrigin.getJSONObject("ActivityLocation");
            if (jsonWrk.containsKey("AddressArtifactFormat")) {
                jsonWrk2 = jsonWrk.getJSONObject("AddressArtifactFormat");
                if (jsonWrk2.containsKey("PoliticalDivision2")) {
                    upsqvOr.setACty(jsonWrk2.getString("PoliticalDivision2"));
                }
                if (jsonWrk2.containsKey("PoliticalDivision1")) {
                    upsqvOr.setASt(jsonWrk2.getString("PoliticalDivision1"));
                }
                if (jsonWrk2.containsKey("CountryCode")) {
                    upsqvOr.setCntr(jsonWrk2.getString("CountryCode"));
                }
            }
        }
        if (jsonOrigin.containsKey("BillToAccount")) {
            jsonWrk = jsonOrigin.getJSONObject("BillToAccount");
            if (jsonWrk.containsKey("Option")) {
                upsqvOr.setBTAOp(jsonWrk.getString("Option"));
            }
            if (jsonWrk.containsKey("Number")) {
                upsqvOr.setBTANm(jsonWrk.getString("Number"));
            }
        }
        if (jsonOrigin.containsKey("ScheduledDeliveryDate")) {
            upsqvOr.setSDlDt(jsonOrigin.getString("ScheduledDeliveryDate"));
        }
        if (jsonOrigin.containsKey("ScheduledDeliveryTime")) {
            upsqvOr.setSDlTm(jsonOrigin.getString("ScheduledDeliveryTime"));
        }
        try {
            upsqvOr.write();
        } catch (SQLException ex) {
            System.out.println("Error writing to UPSQVOR ");
            System.out.println("Error Message:" + ex.getMessage());
            System.out.println("SQLSTATE:" + ex.getSQLState());
            System.out.println("File:" + fileName);
            System.out.println("JSON Rec:" + jsonOrigin.toString());
            ex.printStackTrace();
        }
    }

    private void parseDelivery(String fileName, JSONObject jsonObjDelivery) {

        JSONObject jsonWrk;
        JSONObject jsonWrk2;
        JSONObject jsonWrk3;
        JSONArray jsonArrayWrk;
        Object jsonObj;
        Reference reference;
        ExtendedAddress extendedAddress;

        //for (int i = 0; i < deliveryArray.size(); i++) {
        //   jsonObjDelivery = deliveryArray.getJSONObject(i);
        upsqvDl.setdFile(fileName);
        if (jsonObjDelivery.containsKey("PackageReferenceNumber")) {
            try {
                jsonArrayWrk = jsonObjDelivery.getJSONArray("PackageReferenceNumber");
                for (int x = 0; x < jsonArrayWrk.size(); x++) {
                    jsonWrk = jsonArrayWrk.getJSONObject(x);
                    reference = new Reference(jsonWrk);
                    if (x == 0) {
                        upsqvDl.setPkNmTg1(reference.getNumber());
                        upsqvDl.setPkRfCd1(reference.getCode());
                        upsqvDl.setPkRfVa1(reference.getValue());
                    } else if (x == 1) {
                        upsqvDl.setPkNmTg2(reference.getNumber());
                        upsqvDl.setPkRfCd2(reference.getCode());
                        upsqvDl.setPkRfVa2(reference.getValue());
                    } else if (x == 2) {
                        upsqvDl.setPkNmTg3(reference.getNumber());
                        upsqvDl.setPkRfCd3(reference.getCode());
                        upsqvDl.setPkRfVa3(reference.getValue());
                    } else if (x == 3) {
                        upsqvDl.setPkNmTg4(reference.getNumber());
                        upsqvDl.setPkRfCd4(reference.getCode());
                        upsqvDl.setPkRfVa4(reference.getValue());
                    } else if (x == 4) {
                        upsqvDl.setPkNmTg5(reference.getNumber());
                        upsqvDl.setPkRfCd5(reference.getCode());
                        upsqvDl.setPkRfVa5(reference.getValue());
                    }
                }
            } catch (Exception ex) {
                jsonWrk = jsonObjDelivery.getJSONObject("PackageReferenceNumber");
                reference = new Reference(jsonWrk);
                upsqvDl.setPkNmTg1(reference.getNumber());
                upsqvDl.setPkRfCd1(reference.getCode());
                upsqvDl.setPkRfVa1(reference.getValue());
            }
            //jsonWrk = jsonObjDelivery.getJSONObject("PackageReferenceNumber");
            //if (jsonWrk.containsKey("Number")) {
            //    upsqvDl.setPkNmTg1(jsonWrk.getString("Number"));
            // }
            // if (jsonWrk.containsKey("Code")) {
            //     upsqvDl.setPkRfCd1(jsonWrk.getString("Code"));
            // }
            // if (jsonWrk.containsKey("Value")) {
            //     upsqvDl.setPkRfVa1(jsonWrk.getString("Value"));
            // }
        }
        if (jsonObjDelivery.containsKey("ShipmentReferenceNumber")) {
            try {
                jsonArrayWrk = jsonObjDelivery.getJSONArray("ShipmentReferenceNumber");
                for (int x = 0; x < jsonArrayWrk.size(); x++) {
                    jsonWrk = jsonArrayWrk.getJSONObject(x);
                    reference = new Reference(jsonWrk);
                    if (x == 0) {
                        upsqvDl.setShNmTg1(reference.getNumber());
                        upsqvDl.setShRfCd1(reference.getCode());
                        upsqvDl.setShRfVa1(reference.getValue());
                    } else if (x == 1) {
                        upsqvDl.setShNmTg2(reference.getNumber());
                        upsqvDl.setShRfCd2(reference.getCode());
                        upsqvDl.setShRfVa2(reference.getValue());
                    } else if (x == 2) {
                        upsqvDl.setShNmTg3(reference.getNumber());
                        upsqvDl.setShRfCd3(reference.getCode());
                        upsqvDl.setShRfVa3(reference.getValue());
                    } else if (x == 3) {
                        upsqvDl.setShNmTg4(reference.getNumber());
                        upsqvDl.setShRfCd4(reference.getCode());
                        upsqvDl.setShRfVa4(reference.getValue());
                    } else if (x == 4) {
                        upsqvDl.setShNmTg5(reference.getNumber());
                        upsqvDl.setShRfCd5(reference.getCode());
                        upsqvDl.setShRfVa5(reference.getValue());
                    }
                }
            } catch (Exception ex) {
                jsonWrk = jsonObjDelivery.getJSONObject("ShipmentReferenceNumber");
                reference = new Reference(jsonWrk);
                upsqvDl.setShNmTg1(reference.getNumber());
                upsqvDl.setShRfCd1(reference.getCode());
                upsqvDl.setShRfVa1(reference.getValue());
            }
            //if (jsonWrk.containsKey("Number")) {
            //    upsqvDl.setShNmTg1(jsonWrk.getString("Number"));
            // }
            // if (jsonWrk.containsKey("Code")) {
            //     upsqvDl.setShRfCd1(jsonWrk.getString("Code"));
            // }
            // if (jsonWrk.containsKey("Value")) {
            //     upsqvDl.setShRfVa1(jsonWrk.getString("Value"));
            // }
        }
        if (jsonObjDelivery.containsKey("ShipperNumber")) {
            upsqvDl.setShprNo(jsonObjDelivery.getString("ShipperNumber"));
        }
        if (jsonObjDelivery.containsKey("TrackingNumber")) {
            upsqvDl.setTrk(jsonObjDelivery.getString("TrackingNumber"));
        }
        if (jsonObjDelivery.containsKey("Date")) {
            upsqvDl.setDat(jsonObjDelivery.getString("Date"));
        }
        if (jsonObjDelivery.containsKey("Time")) {
            upsqvDl.setTim(jsonObjDelivery.getString("Time"));
        }
        if (jsonObjDelivery.containsKey("DriverRelease")) {
            upsqvDl.setDrls(jsonObjDelivery.getString("DriverRelease"));
        }
        if (jsonObjDelivery.containsKey("ActivityLocation")) {
            jsonWrk = jsonObjDelivery.getJSONObject("ActivityLocation");
            if (jsonWrk.containsKey("AddressArtifactFormat")) {
                jsonWrk2 = jsonWrk.getJSONObject("AddressArtifactFormat");
                if (jsonWrk2.containsKey("PoliticalDivision2")) {
                    upsqvDl.setaCty(jsonWrk2.getString("PoliticalDivision2"));
                }
                if (jsonWrk2.containsKey("PoliticalDivision1")) {
                    upsqvDl.setdASt(jsonWrk2.getString("PoliticalDivision1"));
                }
                if (jsonWrk2.containsKey("CountryCode")) {
                    upsqvDl.setCntr(jsonWrk2.getString("CountryCode"));
                }
            }
        }
        if (jsonObjDelivery.containsKey("DeliveryLocation")) {
            jsonWrk = jsonObjDelivery.getJSONObject("DeliveryLocation");
            if (jsonWrk.containsKey("AddressArtifactFormat")) {
                jsonWrk2 = jsonWrk.getJSONObject("AddressArtifactFormat");
                if (jsonWrk2.containsKey("ConsigneeName")) {
                    upsqvDl.setCsNm(jsonWrk2.getString("ConsigneeName"));
                }
                if (jsonWrk2.containsKey("StreetNumberLow")) {
                    upsqvDl.setStrNo(jsonWrk2.getString("StreetNumberLow"));
                }
                if (jsonWrk2.containsKey("StreetPrefix")) {
                    upsqvDl.setStrPfx(jsonWrk2.getString("StreetPrefix"));
                }
                if (jsonWrk2.containsKey("StreetName")) {
                    upsqvDl.setStrNm(jsonWrk2.getString("StreetName"));
                }
                if (jsonWrk2.containsKey("StreetType")) {
                    upsqvDl.setStrTp(jsonWrk2.getString("StreetType"));
                }
                if (jsonWrk2.containsKey("StreetSuffix")) {
                    upsqvDl.setStrSfx(jsonWrk2.getString("StreetSuffix"));
                }
                if (jsonWrk2.containsKey("BuildingName")) {
                    upsqvDl.setBldNm(jsonWrk2.getString("BuildingName"));
                }
                // can be arrray 
                if (jsonWrk2.containsKey("AddressExtendedInformation")) {
                    jsonObj = jsonWrk2.get("AddressExtendedInformation");
                    if (jsonObj instanceof JSONObject) {
                        jsonWrk3 = (JSONObject) jsonObj;
                        extendedAddress = new ExtendedAddress(jsonWrk3);
                        upsqvDl.setExtAddr(extendedAddress);
                    } else if (jsonObj instanceof JSONArray) {
                        jsonArrayWrk = (JSONArray) jsonObj;
                        for (int x = 0; x < jsonArrayWrk.size(); x++) {
                            jsonWrk3 = jsonArrayWrk.getJSONObject(x);
                            extendedAddress = new ExtendedAddress(jsonWrk3);
                            upsqvDl.setExtAddr(extendedAddress);
                        }
                    }
                    //  jsonWrk3 = jsonWrk2.getJSONObject("AddressExtendedInformation");
                    //  if (jsonWrk3.containsKey("Type")) {
                    //      upsqvDl.setExAdTp(jsonWrk3.getString("Type"));
                    //  }
                    //   if (jsonWrk3.containsKey("Low")) {
                    //      upsqvDl.setExALow(jsonWrk3.getString("Low"));
                    //  }
                    //   if (jsonWrk3.containsKey("High")) {
                    //       upsqvDl.setExAHi(jsonWrk3.getString("High"));
                    //  }
                }
                if (jsonWrk2.containsKey("PoliticalDivision3")) {
                    upsqvDl.setPlDv3(jsonWrk2.getString("PoliticalDivision3"));
                }
                if (jsonWrk2.containsKey("PoliticalDivision2")) {
                    upsqvDl.setdCty(jsonWrk2.getString("PoliticalDivision2"));
                }
                if (jsonWrk2.containsKey("PoliticalDivision1")) {
                    upsqvDl.setdSt(jsonWrk2.getString("PoliticalDivision1"));
                }
                if (jsonWrk2.containsKey("CountryCode")) {
                    upsqvDl.setCntr(jsonWrk2.getString("CountryCode"));
                }
                if (jsonWrk2.containsKey("PostcodePrimaryLow")) {
                    upsqvDl.setPostP(jsonWrk2.getString("PostcodePrimaryLow"));
                }
                if (jsonWrk2.containsKey("PostcodeExtendedLow")) {
                    upsqvDl.setPostPE(jsonWrk2.getString("PostcodeExtendedLow"));
                }
                //if (jsonWrk2.containsKey("ResidentialAddressIndicator")) {
                //    jsonWrk3 = jsonWrk2.getJSONObject("ResidentialAddressIndicator");
                //    if (jsonWrk3.containsKey("Code")) {
                //        upsqvDl.setdRAdrCd(jsonWrk3.getString("Code"));
                //    }
                //    upsqvDl.setdPostPE(jsonWrk2.getString("PostcodeExtendedLow"));
                //}
            }
            if (jsonWrk.containsKey("Code")) {
                upsqvDl.setlCd(jsonWrk.getString("Code"));
            }
            if (jsonWrk.containsKey("Description")) {
                upsqvDl.setdlDes(jsonWrk.getString("Description"));
            }
            if (jsonWrk.containsKey("SignedForByName")) {
                upsqvDl.setSgnNm(jsonWrk.getString("SignedForByName"));
            }
        }
        //  if (jsonObjDelivery.containsKey("COD")) {
        //      System.out.println("COD:" + jsonObjDelivery.getString("COD")); //Test
        //      jsonWrk = jsonObjDelivery.getJSONObject("COD"); 
        //      if (jsonWrk.containsKey("CODAmount")) {
        //          jsonWrk2 = jsonWrk.getJSONObject("CODAmount"); 
        //          if (jsonWrk2.containsKey("CurrencyCode")) {
        //              upsqvDl.setdCodCC(jsonWrk2.getString("CurrencyCode"));
        //          }
        //          if (jsonWrk2.containsKey("MonetaryValue")) {
        //              upsqvDl.setdCodAmt(jsonWrk2.getString("MonetaryValue"));
        //         }
        //     }
        //  }
        if (jsonObjDelivery.containsKey("BillToAccount")) {
            jsonWrk = jsonObjDelivery.getJSONObject("BillToAccount");
            if (jsonWrk.containsKey("Option")) {
                upsqvDl.setbTopt(jsonWrk.getString("Option"));
            }
            if (jsonWrk.containsKey("Number")) {
                upsqvDl.setBldNm(jsonWrk.getString("Number"));
            }
        }
        if (jsonObjDelivery.containsKey("LastPickupDate")) {
            upsqvDl.setpDt(jsonObjDelivery.getString("LastPickupDate"));
        }
        if (jsonObjDelivery.containsKey("AccessPointLocationID")) {
            upsqvDl.setaPLc(jsonObjDelivery.getString("AccessPointLocationID"));
        }
        try {
            upsqvDl.write();
        } catch (SQLException ex) {
            Logger.getLogger(QVGetData.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error writing to UPSQVDL ");
            System.out.println("Error Message:" + ex.getMessage());
            System.out.println("SQLSTATE:" + ex.getSQLState());
            System.out.println("File:" + fileName);
            System.out.println("JSON Rec:" + jsonObjDelivery.toString());
            System.out.println("upsqvDl=" + upsqvDl);
            ex.printStackTrace();
        }
        //}
        //}

    }

    private void parseManifest(String fileName, JSONObject jsonObjManifest) {
        JSONObject jsonWrk;
        JSONObject jsonWrk2;
        JSONObject jsonWrk3;
        JSONArray jsonArrayWrk;
        Object objWrk;

        upsqvMf.setFile(fileName);

        if (jsonObjManifest.containsKey("Shipper")) {
            jsonWrk = jsonObjManifest.getJSONObject("Shipper");
            if (jsonWrk.containsKey("Name")) {
                upsqvMf.setCmp(jsonWrk.getString("Name"));
            }
            if (jsonWrk.containsKey("AttentionName")) {
                upsqvMf.setNam(jsonWrk.getString("AttentionName"));
            }
            if (jsonWrk.containsKey("TaxIdentificationNumber")) {
                upsqvMf.setTid(jsonWrk.getString("TaxIdentificationNumber"));
            }
            if (jsonWrk.containsKey("PhoneNumber")) {
                upsqvMf.setPhn(jsonWrk.getString("PhoneNumber"));
            }
            if (jsonWrk.containsKey("FaxNumber")) {
                upsqvMf.setFax(jsonWrk.getString("FaxNumber"));
            }
            if (jsonWrk.containsKey("ShipperNumber")) {
                upsqvMf.setShpr(jsonWrk.getString("ShipperNumber"));
            }
            if (jsonWrk.containsKey("EMailAddress")) {
                upsqvMf.setEml(jsonWrk.getString("EMailAddress"));
            }
            if (jsonWrk.containsKey("Address")) {
                jsonWrk2 = jsonWrk.getJSONObject("Address");
                if (jsonWrk2.containsKey("AddressLine1")) {
                    upsqvMf.setAd1(jsonWrk2.getString("AddressLine1"));
                }
                if (jsonWrk2.containsKey("AddressLine2")) {
                    upsqvMf.setAd2(jsonWrk2.getString("AddressLine2"));
                }
                if (jsonWrk2.containsKey("AddressLine3")) {
                    upsqvMf.setAd3(jsonWrk2.getString("AddressLine3"));
                }
                if (jsonWrk2.containsKey("City")) {
                    upsqvMf.setCty(jsonWrk2.getString("City"));
                }
                if (jsonWrk2.containsKey("StateProvinceCode")) {
                    upsqvMf.setSt(jsonWrk2.getString("StateProvinceCode"));
                }
                if (jsonWrk2.containsKey("PostalCode")) {
                    upsqvMf.setZip(jsonWrk2.getString("PostalCode"));
                }
                if (jsonWrk2.containsKey("CountryCode")) {
                    upsqvMf.setCntr(jsonWrk2.getString("CountryCode"));
                }
                if (jsonWrk2.containsKey("ResidentialAddressIndicator")) {
                    upsqvMf.setRAdr("Y");
                }
            }   // end of address 

        } // end of shipper
        if (jsonObjManifest.containsKey("ShipTo")) {
            jsonWrk = jsonObjManifest.getJSONObject("ShipTo");
            if (jsonWrk.containsKey("ShipperAssignedIdentificationNumber")) {
                upsqvMf.setShIdn(jsonWrk.getString("ShipperAssignedIdentificationNumb"));
            }
            if (jsonWrk.containsKey("CompanyName")) {
                upsqvMf.setShCmp(jsonWrk.getString("CompanyName"));
            }
            if (jsonWrk.containsKey("AttentionName")) {
                upsqvMf.setShNam(jsonWrk.getString("AttentionName"));
            }
            if (jsonWrk.containsKey("PhoneNumber")) {
                upsqvMf.setShPhn(jsonWrk.getString("PhoneNumber"));
            }
            if (jsonWrk.containsKey("TaxIdentificationNumber")) {
                upsqvMf.setShTid(jsonWrk.getString("TaxIdentificationNumber"));
            }
            if (jsonWrk.containsKey("FaxNumber")) {
                upsqvMf.setShFax(jsonWrk.getString("FaxNumber"));
            }
            if (jsonWrk.containsKey("EMailAddress")) {
                upsqvMf.setShEml(jsonWrk.getString("EMailAddress"));
            }
            if (jsonWrk.containsKey("Address")) {
                jsonWrk2 = jsonWrk.getJSONObject("Address");
                if (jsonWrk2.containsKey("ConsigneeName")) {
                    upsqvMf.setShCsNm(jsonWrk2.getString("ConsigneeName"));
                }
                if (jsonWrk2.containsKey("AddressLine1")) {
                    upsqvMf.setShAd1(jsonWrk2.getString("AddressLine1"));
                }
                if (jsonWrk2.containsKey("AddressLine2")) {
                    upsqvMf.setShAd2(jsonWrk2.getString("AddressLine2"));
                }
                if (jsonWrk2.containsKey("AddressLine3")) {
                    upsqvMf.setShAd3(jsonWrk2.getString("AddressLine3"));
                }
                if (jsonWrk2.containsKey("City")) {
                    upsqvMf.setShCty(jsonWrk2.getString("City"));
                }
                if (jsonWrk2.containsKey("City")) {
                    upsqvMf.setShCty(jsonWrk2.getString("City"));
                }
                if (jsonWrk2.containsKey("StateProvinceCode")) {
                    upsqvMf.setShSt(jsonWrk2.getString("StateProvinceCode"));
                }
                if (jsonWrk2.containsKey("PostalCode")) {
                    upsqvMf.setShZip(jsonWrk2.getString("PostalCode"));
                }
                if (jsonWrk2.containsKey("CountryCode")) {
                    upsqvMf.setShCntr(jsonWrk2.getString("CountryCode"));
                }
                if (jsonWrk2.containsKey("LocationID")) {
                    upsqvMf.setShLid(jsonWrk2.getString("LocationID"));
                }
                if (jsonWrk2.containsKey("ReceivingAddressName")) {
                    upsqvMf.setShRAdn(jsonWrk2.getString("ReceivingAddressName"));
                }
            }
        }

        if (jsonObjManifest.containsKey("ReferenceNumber")) {
            objWrk = jsonObjManifest.get("ReferenceNumber");
            if (objWrk instanceof JSONObject) {
                jsonWrk = (JSONObject) objWrk;
                Reference reference = new Reference(jsonWrk);
                upsqvMf.setRef(reference);
            } else {
                jsonArrayWrk = (JSONArray) objWrk;
                for (int x = 0; x < jsonArrayWrk.size(); x++) {
                    jsonWrk = jsonArrayWrk.getJSONObject(x);
                    Reference reference = new Reference(jsonWrk);
                    upsqvMf.setRef(reference);
                }
            }
            // if (jsonWrk.containsKey("Number")) {
            //     upsqvMf.setRnTg(jsonWrk.getString("Number"));
            // }
            //  if (jsonWrk.containsKey("Code")) {
            //       upsqvMf.setRnCd(jsonWrk.getString("Code"));
            //   }
            //   if (jsonWrk.containsKey("Value")) {
            //       upsqvMf.setRnVa(jsonWrk.getString("Value"));
            //   }

        }
        if (jsonObjManifest.containsKey("Service")) {
            jsonWrk = jsonObjManifest.getJSONObject("Service");
            if (jsonWrk.containsKey("Code")) {
                upsqvMf.setSvCd(jsonWrk.getString("Code"));
            }
            if (jsonWrk.containsKey("Description")) {
                upsqvMf.setSvDs(jsonWrk.getString("Description"));
            }
        }

        if (jsonObjManifest.containsKey("PickupDate")) {
            upsqvMf.setPDt(jsonObjManifest.getString("PickupDate"));
        }
        if (jsonObjManifest.containsKey("ScheduledDeliveryDate")) {
            upsqvMf.setSDvDt(jsonObjManifest.getString("ScheduledDeliveryDate"));
        }
        if (jsonObjManifest.containsKey("ScheduledDeliveryTime")) {
            upsqvMf.setSDvTm(jsonObjManifest.getString("ScheduledDeliveryTime"));
        }
        if (jsonObjManifest.containsKey("DocumentsOnly")) {
            upsqvMf.setDocO(jsonObjManifest.getString("DocumentsOnly"));
        }

        try {
            upsqvMf.write();

            //}
        } catch (SQLException ex) {
            System.out.println("Error writing to upsqvmf ");
            System.out.println("Error Message:" + ex.getMessage());
            System.out.println("SQLSTATE:" + ex.getSQLState());
            System.out.println("File:" + fileName);
            System.out.println("JSON Rec:" + jsonObjManifest.toString());
            ex.printStackTrace();
        }

        if (jsonObjManifest.containsKey("Package")) {
            objWrk = jsonObjManifest.get("Package");
            if (objWrk instanceof JSONArray) {
                jsonArrayWrk = (JSONArray) objWrk;
                for (int x = 0; x < jsonArrayWrk.size(); x++) {
                    jsonWrk = jsonArrayWrk.getJSONObject(x);
                    parseMfPk(fileName, jsonWrk);
                }
            } else if (objWrk instanceof JSONObject) {
                jsonWrk = (JSONObject) objWrk;
                parseMfPk(fileName, jsonWrk);
            }
        }

    }

    private void parseMfPk(String fileName, JSONObject jsonManifestPkg) {
        Object wrkObj;
        JSONObject jsonObj;
        JSONObject jsonObj2;
        JSONObject jsonObj3;
        JSONArray jsonArray;

        upsqvMpk.setFile(fileName);

        if (jsonManifestPkg.containsKey("Activity")) {
            wrkObj = jsonManifestPkg.get("Activity");
            if (wrkObj instanceof JSONArray) {
                jsonArray = (JSONArray) wrkObj;
                // get the first dates
                jsonObj = jsonArray.getJSONObject(0);
            } else {
                jsonObj = (JSONObject) wrkObj;
            }
            if (jsonObj.containsKey("Date")) {
                upsqvMpk.setaDt(jsonObj.getString("Date"));
            }
            if (jsonObj.containsKey("Time")) {
                upsqvMpk.setaTm(jsonObj.getString("Time"));
            }
        }
        if (jsonManifestPkg.containsKey("Description")) {
            upsqvMpk.setDes(jsonManifestPkg.getString("Description"));
        }

        if (jsonManifestPkg.containsKey("Dimensions")) {
            jsonObj = jsonManifestPkg.getJSONObject("Dimensions");
            if (jsonObj.containsKey("Length")) {
                upsqvMpk.setDln(jsonObj.getString("Length"));
            }
            if (jsonObj.containsKey("Width")) {
                upsqvMpk.setDwd(jsonObj.getString("Width"));
            }
            if (jsonObj.containsKey("Height")) {
                upsqvMpk.setDht(jsonObj.getString("Height"));
            }
        }
        if (jsonManifestPkg.containsKey("DimensionalWeight")) {
            jsonObj = jsonManifestPkg.getJSONObject("DimensionalWeight");
            if (jsonObj.containsKey("UnitOfMeasurement")) {
                jsonObj2 = jsonObj.getJSONObject("UnitOfMeasurement");
                if (jsonObj2.containsKey("Code")) {
                    upsqvMpk.setDwtUm(jsonObj2.getString("Code"));
                }
                if (jsonObj2.containsKey("Description")) {
                    upsqvMpk.setuDes(jsonObj2.getString("Description"));
                }
            }
            if (jsonObj.containsKey("Weight")) {
                upsqvMpk.setdWt(jsonObj.getString("Weight"));
            }
            // }
        }
        if (jsonManifestPkg.containsKey("PackageWeigh")) {
            jsonObj = jsonManifestPkg.getJSONObject("PackageWeigh");
            if (jsonObj.containsKey("Weight")) {
                upsqvMpk.setDpWt(jsonObj.getString("Weight"));
            }
        }

        if (jsonManifestPkg.containsKey("LargePackage")) {
            upsqvMpk.setlPkg(jsonManifestPkg.getString("LargePackage"));
        }

        if (jsonManifestPkg.containsKey("TrackingNumber")) {
            upsqvMpk.setTrk(jsonManifestPkg.getString("TrackingNumber"));
        }
        if (jsonManifestPkg.containsKey("ReferenceNumber")) {
            wrkObj = jsonManifestPkg.get("ReferenceNumber");
            if (wrkObj instanceof JSONArray) {
                jsonArray = (JSONArray) wrkObj;
                for (int x = 0; x < jsonArray.size(); x++) {
                    jsonObj = jsonArray.getJSONObject(x);
                    Reference ref = new Reference(jsonObj);
                    upsqvMpk.setRef(ref);
                }
            } else if (wrkObj instanceof JSONObject) {
                jsonObj = (JSONObject) wrkObj;
                Reference ref = new Reference(jsonObj);
                upsqvMpk.setRef(ref);
            }
        }
        if (jsonManifestPkg.containsKey("PackageServiceOptions")) {
            jsonObj = jsonManifestPkg.getJSONObject("PackageServiceOptions");
            if (jsonObj.containsKey("COD")) {
                wrkObj = jsonObj.get("COD");
                if (wrkObj instanceof JSONObject) {
                    jsonObj2 = (JSONObject) wrkObj;
                    if (jsonObj2.containsKey("CODCode")) {
                        upsqvMpk.setCodCd("CODCode");
                    }
                    if (jsonObj2.containsKey("CODAmount")) {
                        jsonObj3 = jsonObj2.getJSONObject("CODAmount");
                        if (jsonObj3.containsKey("CurrencyCode")) {
                            upsqvMpk.setCodCc(jsonObj3.getString("CurrencyCode"));
                        }
                        if (jsonObj3.containsKey("MonetaryValue")) {
                            upsqvMpk.setCodVa(jsonObj2.getString("MonetaryValue"));
                        }
                    }
                }
            }
            if (jsonObj.containsKey("InsuredValue")) {
                jsonObj2 = jsonObj.getJSONObject("InsuredValue");
                if (jsonObj2.containsKey("CurrencyCode")) {
                    upsqvMpk.setInsCC(jsonObj2.getString("CurrencyCode"));
                }
                if (jsonObj2.containsKey("MonetaryValue")) {
                    upsqvMpk.setInsVa(jsonObj2.getString("MonetaryValue"));
                }
            }
            if (jsonObj.containsKey("EarliestDeliveryTime")) {
                upsqvMpk.setEdvTm(jsonObj.getString("EarliestDeliveryTime"));
            }
            if (jsonObj.containsKey("HoldForPickup")) {
                jsonObj2 = jsonObj.getJSONObject("HoldForPickup");
                if (jsonObj2.containsKey("UPSPremiumCareIndicator")) {
                    upsqvMpk.setCrIn("Y");
                }
            }
            try {
                upsqvMpk.write();
            } catch (SQLException ex) {
                System.out.println("Error writing to upsqvMpk ");
                System.out.println("Error Message:" + ex.getMessage());
                System.out.println("SQLSTATE:" + ex.getSQLState());
                System.out.println("File:" + fileName);
                System.out.println("JSON Rec:" + jsonManifestPkg.toString());
                ex.printStackTrace();
            }
        }
    }

    private void parseGeneric(String fileName, JSONObject jsonObjGeneric) {
        Reference reference;
        JSONObject jsonWrk;
        JSONObject jsonWrk2;
        JSONArray jsonArray;
        Object jsonObj;

        upsqvGn.setFile(fileName);
        if (jsonObjGeneric.containsKey("ActivityType")) {
            upsqvGn.setAtp(jsonObjGeneric.getString("ActivityType"));
        }
        if (jsonObjGeneric.containsKey("TrackingNumber")) {
            upsqvGn.setTrk(jsonObjGeneric.getString("TrackingNumber"));
        }
        if (jsonObjGeneric.containsKey("ShipperNumber")) {
            upsqvGn.setShpr(jsonObjGeneric.getString("ShipperNumber"));
        }
        if (jsonObjGeneric.containsKey("ShipmentReferenceNumber")) {
            jsonObj = jsonObjGeneric.get("ShipmentReferenceNumber");
            if (jsonObj instanceof JSONArray) {
                jsonArray = (JSONArray) jsonObj;
                for (int x = 0; x < jsonArray.size(); x++) {
                    jsonWrk = jsonArray.getJSONObject(x);
                    reference = new Reference(jsonWrk);
                    upsqvGn.setShRef(reference);
                }
            } else {
                jsonWrk = (JSONObject) jsonObj;
                reference = new Reference(jsonWrk);
                upsqvGn.setShRef(reference);
            }
        }

        if (jsonObjGeneric.containsKey("PackageReferenceNumber")) {
            jsonObj = jsonObjGeneric.get("PackageReferenceNumber");
            if (jsonObj instanceof JSONArray) {
                jsonArray = (JSONArray) jsonObj;
                for (int x = 0; x < jsonArray.size(); x++) {
                    jsonWrk = jsonArray.getJSONObject(x);
                    reference = new Reference(jsonWrk);
                    upsqvGn.setPkRef(reference);
                }
            } else {
                jsonWrk = (JSONObject) jsonObj;
                reference = new Reference(jsonWrk);
                upsqvGn.setPkRef(reference);
            }
        }
        if (jsonObjGeneric.containsKey("Service")) {
            jsonWrk = jsonObjGeneric.getJSONObject("Service");
            if (jsonWrk.containsKey("Code")) {
                upsqvGn.setSvCd(jsonWrk.getString("Code"));
            }
        }

        if (jsonObjGeneric.containsKey("Activity")) {
            jsonWrk = jsonObjGeneric.getJSONObject("Activity");
            if (jsonWrk.containsKey("Date")) {
                upsqvGn.setpAdt(jsonWrk.getString("Date"));
            }
            if (jsonWrk.containsKey("Time")) {
                upsqvGn.setpAtm(jsonWrk.getString("Time"));
            }
        }
        if (jsonObjGeneric.containsKey("BillToAccount")) {
            jsonWrk = jsonObjGeneric.getJSONObject("BillToAccount");
            if (jsonWrk.containsKey("Option")) {
                upsqvGn.setBtOpt(jsonWrk.getString("Option"));
            }
            if (jsonWrk.containsKey("Number")) {
                upsqvGn.setBtAct(jsonWrk.getString("Number"));
            }

        }
        if (jsonObjGeneric.containsKey("ShipTo")) {
            jsonWrk = jsonObjGeneric.getJSONObject("ShipTo");
            if (jsonWrk.containsKey("LocationID")) {
                upsqvGn.setShLid(jsonWrk.getString("LocationID"));
            }
            if (jsonWrk.containsKey("ReceivingAddressName")) {
                upsqvGn.setShRadn(jsonWrk.getString("ReceivingAddressName"));
            }
            if (jsonWrk.containsKey("Bookmark")) {
                upsqvGn.setBkmrk("Bookmark");
            }
        }
        if (jsonObjGeneric.containsKey("RescheduledDeliveryDate")) {
            upsqvGn.setRsDt(jsonObjGeneric.getString("RescheduledDeliveryDate"));
        }
        if (jsonObjGeneric.containsKey("FailureNotification")) {
            jsonWrk = jsonObjGeneric.getJSONObject("FailureNotification");
            if (jsonWrk.containsKey("FailedEmailAddress")) {
                upsqvGn.setfEml(jsonWrk.getString("FailedEmailAddress"));
            }
            if (jsonWrk.containsKey("FailureNotificationCode")) {
                jsonWrk2 = jsonWrk.getJSONObject("FailureNotificationCode");
                if (jsonWrk2.containsKey("Code")) {
                    upsqvGn.setfNcd(jsonWrk2.getString("Code"));
                }
            }
        }
        try {
            upsqvGn.write();
        } catch (SQLException ex) {
            System.out.println("Error writing to upsqvGn ");
            System.out.println("Error Message:" + ex.getMessage());
            System.out.println("SQLSTATE:" + ex.getSQLState());
            System.out.println("File:" + fileName);
            System.out.println("JSON Rec:" + jsonObjGeneric.toString());
            ex.printStackTrace();
        }

    }

}
