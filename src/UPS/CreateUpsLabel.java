/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UPS;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.ws.rs.core.MultivaluedMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.imgscalr.Scalr;
import pickticketprint.AppParms;
import pickticketprint.PtktData;
import pickticketprint.UpsShprParms;

/**
 *
 * @author Khatchik
 */
public class CreateUpsLabel {

    private static UpsShprParms parms;
    private static String shAddr1;
    private static String Shaddr2;
    private static String ShAddr3;
    private static String strReq;

    public  UpsResponse createUpsLabel(UpsShprParms shprParms, PtktData ptktData) {
        parms = shprParms;
        JSONObject jsonShpRsp = new JSONObject();
        UpsResponse upsResponse = new UpsResponse();

        //test 
        // lblFilName = "Labels/" +  ptktData.getRefPtkt().replace("/", "_").trim()
        String lblFilName;
        // prtPtkt2 = new PrintPtkt(lblFilName);

        // end TEst 
        jsonShpRsp = reqShpmt(ptktData);
        LblRspStat rspStat = getResponseStat(jsonShpRsp);

        if (rspStat.getErrStat().equals("1")) {
            if (jsonShpRsp.containsKey("ShipmentResults")) {
                upsResponse = getUpsResponse(jsonShpRsp.getJSONObject("ShipmentResults"));
               // upsResponse.print();
                lblFilName = shprParms.getUpsLblLoc() +  ptktData.getRefPtkt().replace("/", "_").trim()
                        + "_" + ptktData.getCrtnId().trim() + ".GIF";
                try {
                    byte[] lbl = Base64.decodeBase64(upsResponse.getLblImage());
                    InputStream in = new ByteArrayInputStream(lbl);
                    BufferedImage bImageLbl = ImageIO.read(in);
                    BufferedImage lblRot = Scalr.rotate(bImageLbl, Scalr.Rotation.CW_90, Scalr.OP_ANTIALIAS);
                    ImageIO.write(lblRot, "GIF", new File(lblFilName));
                    upsResponse.setLblLoc(lblFilName);
                    //FileOutputStream fos = new FileOutputStream(new File(lblFilName));
                    //fos.write(Base64.decodeBase64(upsResponse.getLblImage()));
                    //fos.close();  
                    //PrintPtkt prtPtkt = new PrintPtkt(lblFilName);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CreateUpsLabel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    //System.out.println("IO exception writting label " + ex.getLocalizedMessage());
                    //} catch (IOException ex) {
                        Logger.getLogger(CreateUpsLabel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        upsResponse.setLblRspStat(rspStat);
        return upsResponse;

    }

    private static JSONObject reqShpmt(PtktData ptktData) {
        DecimalFormat formatter = new DecimalFormat("###.##");

        JSONObject jsonObj = new JSONObject();
        JSONObject jsonWrk = new JSONObject();
        JSONObject jsonWrk2 = new JSONObject();
        JSONObject jsonWrk3 = new JSONObject();
        JSONArray jsonWrkArray = new JSONArray();
        JSONObject jsonShpr = new JSONObject();
        JSONObject jsonShpmt = new JSONObject();
        JSONObject jsonShpFrm = new JSONObject();
        JSONObject jsonShpTo = new JSONObject();
        JSONObject jsonPkg = new JSONObject();
        JSONObject jsonLblSpc = new JSONObject();
        JSONObject jsonPyr = new JSONObject();
        JSONObject jsonPayInf = new JSONObject();
        JSONObject jsonBilSeg = new JSONObject();
        JSONObject jsonHndlU1 = new JSONObject();
        JSONObject jsonShReq = new JSONObject();
        JSONObject jsonShReqReq = new JSONObject();
        JSONObject jsonCmd = new JSONObject();

        MultivaluedMap<String, String> hdrLst;
        String input;
        Client client = Client.create();
        WebResource webResource = client.resource(parms.getShUrl());
        //System.out.println("Web resource URI= " + weWebRbResource.getURI());
        JSONObject jsonAccessReq = new JSONObject();

        // JSONObject jsonSubscReq = new JSONObject();
        //jsonAccessReq.put("AccessLicenseNumber", parms.getShLicense());
        jsonWrk.put("Username", parms.getShUserId());
        jsonWrk.put("Password", parms.getShPassWord());
        jsonAccessReq.put("UsernameToken", jsonWrk);
        jsonWrk.clear();
        jsonWrk.put("AccessLicenseNumber", parms.getShLicense());
        jsonAccessReq.put("ServiceAccessToken", jsonWrk);

        jsonObj.put("UPSSecurity", jsonAccessReq);

        // request 
        jsonShReqReq.put("RequestOption", "nonvalidate");
        jsonShReq.put("Request", jsonShReqReq);
        // shipper 
        jsonShpr.put("Name", ptktData.getShprName());
        if (!ptktData.getShprAttn().trim().isEmpty()) {
            jsonShpr.put("AttentionName", ptktData.getShprAttn());
        }
        if (!ptktData.getShprDspName().isEmpty()) {
            jsonShpr.put("CompanyDisplayableName", ptktData.getShprDspName());
        }
        if (!ptktData.getShprPh().trim().isEmpty()) {
            jsonWrk.clear();
            jsonWrk.put("Number", ptktData.getShprPh());
            jsonShpr.put("Phone", jsonWrk);
        }
        jsonShpr.put("ShipperNumber", ptktData.getShprShprNo());
        jsonWrk.clear();
        jsonWrk.put("AddressLine", ptktData.getShprAdr());
        jsonWrk.put("City", ptktData.getShprCty());
        jsonWrk.put("StateProvinceCode", ptktData.getShprSt());
        jsonWrk.put("PostalCode", ptktData.getShprZip());
        jsonWrk.put("CountryCode", ptktData.getShprCntr());
        jsonShpr.put("Address", jsonWrk);
        jsonShpmt.put("Shipper", jsonShpr);

        // ship to  
        jsonShpTo.put("Name", ptktData.getShpToNam());
        if (!ptktData.getShpToAttn().trim().isEmpty()) {
            jsonShpTo.put("AttentionName", ptktData.getShpToAttn());
        }
        if (!ptktData.getShpToPh().trim().isEmpty()) {
            jsonWrk.clear();
            jsonWrk.put("Number", ptktData.getShpToPh());
            jsonShpTo.put("Phone", jsonWrk);
        }

        if (!ptktData.getShpToEml().trim().isEmpty()) {
            jsonShpTo.put("EMailAddress", ptktData.getShpToEml());
        }

        jsonWrkArray.clear();
        jsonWrk.clear();
        jsonWrkArray.addAll(ptktData.getShpToAdr());
        jsonWrk.put("AddressLine", jsonWrkArray);
        jsonWrk.put("City", ptktData.getShpToCty());
        jsonWrk.put("StateProvinceCode", ptktData.getShpToSt());
        jsonWrk.put("PostalCode", ptktData.getShpToZip());
        jsonWrk.put("CountryCode", ptktData.getShpToCntr());
        if(ptktData.isResSrv()) {
            jsonWrk.put("ResidentialAddressIndicator", "Y");
        }
        jsonShpTo.put("Address", jsonWrk);

        jsonShpmt.put("ShipTo", jsonShpTo);

        // ShipFrom 
        jsonShpFrm.put("Name", ptktData.getShpFrmNam());
        if (!ptktData.getShpFrmAttn().trim().isEmpty()) {
            jsonShpFrm.put("AttentionName", ptktData.getShpFrmAttn());
        }
        jsonWrk.clear();
        jsonWrk.put("Number", ptktData.getShpFrmPh());
        jsonShpFrm.put("Phone", jsonWrk);

//        // jsonShpFrm.put("ShipperNumber", ptktData.getShprNo());
        jsonWrkArray.clear();
        jsonWrk.clear();
        jsonWrkArray.addAll(ptktData.getShpFrmAdr());
        jsonWrk.put("AddressLine", jsonWrkArray);
        jsonWrk.put("City", ptktData.getShpFrmCty());
        jsonWrk.put("StateProvinceCode", ptktData.getShpFrmSt());
        jsonWrk.put("PostalCode", ptktData.getShpFrmZip());
        jsonWrk.put("CountryCode", ptktData.getShpFrmCntr());
        jsonShpFrm.put("Address", jsonWrk);
        jsonShpmt.put("ShipFrom", jsonShpFrm);

        jsonWrkArray.clear();
        jsonWrk.clear();
        jsonWrk2.clear();
        jsonWrk.put("Type", "01");

        switch (ptktData.getBillOpt()) {
            case "S":
                jsonWrk.put("BillShipper", getShipperBillOp(ptktData));
                break;
            case "R":
                break;
            case "3":
                jsonWrk.put("BillThirdParty", getShipper3rdPrty(ptktData));
                break;
            default:

        }
        jsonWrkArray.add(jsonWrk);
        jsonWrk2.put("ShipmentCharge", jsonWrkArray);
        jsonShpmt.put("PaymentInformation", jsonWrk2);

        jsonWrk.clear();
        // Service
        jsonWrk.put("Code", ptktData.getSvcCd());
        jsonShpmt.put("Service", jsonWrk);
        
        if (ptktData.isMailInno()) {
            jsonWrk.clear();
            jsonShpmt.put("USPSEndorsement", "1");
            jsonShpmt.put("CostSenter", ptktData.getShpToNam());
            jsonShpmt.put("PackageID", ptktData.getCrtn128());
        }

        // packagging 
        jsonWrk.clear();
        jsonWrk3.clear();
        jsonWrk.put("Code", ptktData.getPkgTyp());      //02 = customer supplied package
        jsonPkg.put("Packaging", jsonWrk);

        // Dimension
        jsonWrk.clear();
        jsonWrk.put("Code", "IN");

        jsonWrk2.clear();
        jsonWrk2.put("UnitOfMeasurement", jsonWrk);
        jsonWrk2.put("Length", ptktData.getDimLen());
        jsonWrk2.put("Width", ptktData.getDimWid());
        jsonWrk2.put("Height", ptktData.getDimHt());

        jsonPkg.put("Dimensions", jsonWrk2);
        jsonWrk.clear();
        jsonWrk.put("Code", ptktData.getCmdUom());
        jsonWrk2.clear();
        jsonWrk2.put("UnitOfMeasurement", jsonWrk);
        jsonWrk2.put("Weight", formatter.format(ptktData.getCmdWgt()));
        jsonPkg.put("PackageWeight", jsonWrk2);  
        jsonWrkArray.clear();
        jsonWrk.put("Code", " ");
        jsonWrk.put("Value", ptktData.getRefPtkt());
        jsonWrkArray.add(jsonWrk);
        jsonWrk.clear();
        if (!ptktData.getRefPo().trim().isEmpty()) {
            jsonWrk.put("Code", "PO");
            jsonWrk.put("Value", ptktData.getRefPo());
            jsonWrkArray.add(jsonWrk);
        }
        jsonPkg.put("ReferenceNumber", jsonWrkArray);
        jsonShpmt.accumulate("Package", jsonPkg);
        jsonShReq.put("Shipment", jsonShpmt);

        // Label specification 
        jsonWrk.clear();
        jsonWrk.put("Code", "GIF");
        jsonLblSpc.put("LabelImageFormat", jsonWrk);
        jsonWrk.clear();
        jsonWrk.put("Height", "6");
        jsonWrk.put("Width", "4");
        jsonLblSpc.put("LabelStockSize", jsonWrk);
        //jsonShpmt.put("LabelSpecification", jsonLblSpc);

        jsonWrk.clear();

        jsonObj.put("ShipmentRequest", jsonShReq);
        jsonObj.put("LabelSpecification", jsonLblSpc);

        input = jsonObj.toString();
        strReq = jsonObj.toString();
        //System.out.println("Request:" + input);

        ClientResponse response = webResource.accept("application/json")
                .header("Content-Type", "application/json;charset=UTF-8")
                .post(ClientResponse.class, input);

        if (response.getStatus() != 200) {
            System.out.println("http Failed status = " + response.getStatus());
            hdrLst = response.getHeaders();
            for (String key : hdrLst.keySet()) {
                System.out.println(key + "=" + hdrLst.getFirst(key));
                
            }
            AppParms.getLogger().log(Level.INFO,"Failed : HTTP error code :" + response.getStatus());
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        String output = response.getEntity(String.class);
        //System.out.println("output:" + output);
        JSONObject jsonOutput = (JSONObject) JSONSerializer.toJSON(output);
        JSONObject jsonShprRsp = new JSONObject();
        if (jsonOutput.containsKey("ShipmentResponse")) {
            jsonShprRsp = jsonOutput.getJSONObject("ShipmentResponse");
        } else {
            jsonShprRsp = jsonOutput;
        }
        return jsonShprRsp;

    }

    private static JSONObject getShipperBillOp(PtktData ptktData) {
        JSONObject jsonBillOp = new JSONObject();
        jsonBillOp.put("AccountNumber", ptktData.getShprShprNo());

        return jsonBillOp;
    }

    private static JSONObject getShipper3rdPrty(PtktData ptktData) {
        JSONObject json3PtyOp = new JSONObject();
        JSONObject jsonWrk = new JSONObject();
        json3PtyOp.put("AccountNumber", ptktData.getPyrShprNo());
        jsonWrk.put("PostalCode", ptktData.getSoZip());
        jsonWrk.put("CountryCode", ptktData.getSoCntr());

        json3PtyOp.put("Address", jsonWrk);
        return json3PtyOp;
    }

    private static LblRspStat getResponseStat(JSONObject response) {
        String status;
        LblRspStat rspStat = new LblRspStat();
        JSONObject jsonRsp = new JSONObject();
        JSONObject jsonRspStat = new JSONObject();
        JSONObject jsonWrk1 = new JSONObject();
        JSONObject jsonWrk2 = new JSONObject();
        JSONObject jsonWrk3 = new JSONObject();
        if (!response.containsKey("Response")) {
            rspStat.setErrStat("0");
            if (response.containsKey("Fault")) {
                jsonWrk1 = response.getJSONObject("Fault");
                if (jsonWrk1.containsKey("detail")) {
                    jsonWrk2 = jsonWrk1.getJSONObject("detail");
                    if (jsonWrk2.containsKey("Errors")) {
                        jsonWrk3 = jsonWrk2.getJSONObject("Errors");
                        if (jsonWrk3.containsKey("ErrorDetail")) {
                            jsonWrk1 = jsonWrk3.getJSONObject("ErrorDetail");
                            if (jsonWrk1.containsKey("Severity")) {
                                rspStat.setSeverity(jsonWrk1.getString("Severity"));
                            }
                            if (jsonWrk1.containsKey("PrimaryErrorCode")) {
                                jsonWrk2 = jsonWrk1.getJSONObject("PrimaryErrorCode");
                                if (jsonWrk2.containsKey("Code")) {
                                    rspStat.setErrCode(jsonWrk2.getString("Code"));
                                }

                                if (jsonWrk2.containsKey("Description")) {
                                    rspStat.setErrMsg(jsonWrk2.getString("Description"));
                                }
                            }
                        }
                    }
                }
            }
            //rspStat.setErrMsg(response.toString());
        } else {
            jsonRsp = response.getJSONObject("Response");
            if (!jsonRsp.containsKey("ResponseStatus")) {
                rspStat.setErrStat("0");
                rspStat.setErrMsg("Response Status not found");
            } else {
                jsonRspStat = jsonRsp.getJSONObject("ResponseStatus");
                if (jsonRspStat.containsKey("Code")) {
                    rspStat.setErrStat(jsonRspStat.getString("Code"));
                } else {
                    //System.out.println("RspStat:" + jsonRspStat.toString());
                    rspStat.setErrStat("0");
                    rspStat.setErrMsg("Response Code not found ");
                }

                if (jsonRspStat.containsKey("Description")) {
                    rspStat.setErrMsg(jsonRspStat.getString("Description"));
                } else {
                    //System.out.println("RspStat:" + jsonRspStat.toString());
                    rspStat.setErrMsg("Description Not found");
                }
            }
        }
        //System.out.println("Status is:" + rspStat.getErrStat());
        //System.out.println("Response Description: "
        //        + rspStat.getErrMsg());
        if (!rspStat.getErrStat().equals("1")) {
            // JSONObject jsonError = response.getJSONObject("Error");
            System.out.println("Request:" + strReq);
            System.out.println("ErrorSeverity:" + rspStat.getSeverity());
            System.out.println("ErrorCode:" + rspStat.getErrCode());
            System.out.println("Error Description:" + rspStat.getErrMsg());
            AppParms.getLogger().log(Level.INFO,"Request=" + strReq + "\nErrorCode:" 
            + rspStat.getErrCode() + " Message:" + rspStat.getErrMsg());
        }
        return rspStat;

    }

    private static UpsResponse getUpsResponse(JSONObject response) {
        UpsResponse upsResponse = new UpsResponse();
        JSONObject jsonShpChg = new JSONObject();
        JSONObject jsonWrk1 = new JSONObject();
        JSONObject jsonWrk2 = new JSONObject();
        if (response.containsKey("ShipmentCharges")) {
            jsonWrk1 = response.getJSONObject("ShipmentCharges");
            if (jsonWrk1.containsKey("TransportationCharges")) {
                jsonWrk2 = jsonWrk1.getJSONObject("TransportationCharges");
                if (jsonWrk2.containsKey("MonetaryValue")) {
                    upsResponse.setTpxChrg(new BigDecimal(jsonWrk2.getString("MonetaryValue")));
                }
            }
            if (jsonWrk1.containsKey("ServiceOptionsCharges")) {
                jsonWrk2 = jsonWrk1.getJSONObject("ServiceOptionsCharges");
                if (jsonWrk2.containsKey("MonetaryValue")) {
                    upsResponse.setSvcChrg(new BigDecimal(jsonWrk2.getString("MonetaryValue")));
                }
            }
            if (jsonWrk1.containsKey("TotalCharges")) {
                jsonWrk2 = jsonWrk1.getJSONObject("TotalCharges");
                if (jsonWrk2.containsKey("MonetaryValue")) {
                    upsResponse.setTotChrg(new BigDecimal(jsonWrk2.getString("MonetaryValue")));
                }
            }

        }
        if (response.containsKey("NegotiatedRateCharges")) {
            jsonWrk1 = response.getJSONObject("NegotiatedRateCharges");
            if (jsonWrk1.containsKey("TotalCharge")) {
                jsonWrk2 = jsonWrk1.getJSONObject("TotalCharge");
                if (jsonWrk2.containsKey("MonetaryValue")) {
                    upsResponse.setNgsChrg(new BigDecimal(jsonWrk2.getString("MonetaryValue")));
                }
            }
        }
        if (response.containsKey("BillingWeight")) {
            jsonWrk1 = response.getJSONObject("BillingWeight");
            if (jsonWrk1.containsKey("TotalCharge")) {
                jsonWrk2 = jsonWrk1.getJSONObject("TotalCharge");
                if (jsonWrk2.containsKey("Weight")) {
                    upsResponse.setBlgWgt(new BigDecimal(jsonWrk2.getString("Weight")));
                }
            }
        }

        if (response.containsKey("PackageResults")) {
            jsonWrk1 = response.getJSONObject("PackageResults");
            if (jsonWrk1.containsKey("TrackingNumber")) {
                upsResponse.setTrkNo(jsonWrk1.getString("TrackingNumber"));
            }
            if (jsonWrk1.containsKey("ShippingLabel")) {
                jsonWrk2 = jsonWrk1.getJSONObject("ShippingLabel");
                if (jsonWrk2.containsKey("GraphicImage")) {
                    upsResponse.setLblImage(jsonWrk2.getString("GraphicImage"));
                }
            }
        }

        return upsResponse;
    }

}
