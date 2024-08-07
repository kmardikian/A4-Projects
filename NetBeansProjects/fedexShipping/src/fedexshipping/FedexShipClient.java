/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fedexshipping;

import models.FedexResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
//import java.util.Base64;
import org.apache.commons.codec.binary.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import javax.imageio.ImageIO;
import javax.ws.rs.core.MultivaluedMap;
import models.FedexShipmentRequest;
import models.NotificationSeverityType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 *
 * @author Khatchik
 */
public class FedexShipClient {

    private final GetAuth auth;
    private FedexShipmentRequest request;
    private final FxAppParms appParms;
    private final Logger logger;
    private JSONObject jsonRsp;
    private FedexResponse response = new FedexResponse();

    public FedexShipClient(GetAuth auth,
            FxAppParms appParms,
            Logger logger) {
        this.auth = auth;
//        this.request = request;
        this.appParms = appParms;
        this.logger = logger;
    }

    public FedexResponse requestShipment(FedexShipmentRequest request) {
        this.request = request;
        //FedexResponse httpResponse = new FedexResponse();
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("requestedShipment", buildFxRequest());
        jsonRequest.put("labelResponseOptions", "LABEL");
        jsonRequest.put("accountNumber", buildAcctNo());

        String input = jsonRequest.toString();
        
        if (callHttp(input)) {
            prcFxResponse();
        }

        return response;

    }

    private JSONObject buildFxRequest() {
        JSONObject jobj = new JSONObject();

        jobj.put("shipper", buildShipper());
        jobj.put("recipients", buildRecipients());
        jobj.put("pickupType", request.getPickupType().toString());
        jobj.put("serviceType", request.getSrvTyp());
        jobj.put("packagingType", request.getPkgTyp());
        jobj.put("shippingChargesPayment", buildShippingCharg());
        jobj.put("labelSpecification", buildLblSpec());
        jobj.put("rateRequestType", buildRateReqType());
        jobj.put("requestedPackageLineItems", buildPkg());

        return jobj;

    }

    private JSONObject buildShipper() {
        JSONObject jobj = new JSONObject();
        jobj.put("address", buildShAddr());
        jobj.put("contact", buildShpContact());

        return jobj;

    }

    private JSONObject buildShAddr() {
        JSONObject jobj = new JSONObject();
        jobj.put("streetLines", buildShprStrLines());
        jobj.put("city", request.getShipperCity());
        jobj.put("stateOrProvinceCode", request.getShipperStat());
        jobj.put("postalCode", request.getShipperZip());
        jobj.put("countryCode", request.getShipperCountry());

        return jobj;

    }

    private JSONArray buildShprStrLines() {
        JSONArray jarr = new JSONArray();
        request.getShipperAdr().forEach(addr -> {
            if (addr.trim().length() > 0) {
                jarr.add(addr);
            }
        });

        return jarr;
    }

    private JSONObject buildShpContact() {
        JSONObject jobj = new JSONObject();
        if (!request.getShipperName().trim().isEmpty()) {
            jobj.put("personName", request.getShipperName().trim());
        }
        if (!request.getShipperPhone().trim().isEmpty()) {
            jobj.put("phoneNumber", request.getShipperPhone().trim());
        }
        if (!request.getShipperComp().trim().isEmpty()) {
            jobj.put("companyName", request.getShipperComp());
        }

        return jobj;
    }

    private JSONArray buildRecipients() {
        JSONArray jarr = new JSONArray();
        JSONObject jobj = new JSONObject();
        jobj.put("address", buildRecAddr());
        jobj.put("contact", buildRecContact());

        jarr.add(jobj);
        return jarr;

    }

    private JSONObject buildRecAddr() {
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        request.getRecAdr().forEach(adr -> {
            if (!adr.trim().isEmpty()) {
                jarr.add(adr);
            }
        });
        jobj.put("streetLines", jarr);
        jobj.put("city", request.getRecCity());
        jobj.put("stateOrProvinceCode", request.getRecStat());
        jobj.put("postalCode", request.getRecZip());
        jobj.put("countryCode", request.getRecCountry());
        if (request.getSrvTyp().equals("GROUND_HOME_DELIVERY")) {
            jobj.put("residential", "true");
        }
        return jobj;
    }

    private JSONObject buildRecContact() {
        JSONObject jobj = new JSONObject();
        if (!request.getRecName().trim().isEmpty()) {
            jobj.put("personName", request.getRecName().trim());
        }
        jobj.put("phoneNumber", request.getRecPhone());
        if (!request.getRecComp().trim().isEmpty()) {
            jobj.put("companyName", request.getRecComp().trim());
        }

        return jobj;
    }

    private JSONObject buildShippingCharg() {
        JSONObject jobj = new JSONObject();
        jobj.put("paymentType", request.getPaymentType());
        jobj.put("payor", buildPayor());

        return jobj;
    }

    private JSONObject buildPayor() {
        JSONObject jobj = new JSONObject();
        jobj.put("responsibleParty", buildRespPrty());

        return jobj;
    }

    private JSONObject buildRespPrty() {
        JSONObject jobj = new JSONObject();
        jobj.put("accountNumber", buildrespPrtyAct());

        return jobj;
    }

    private JSONObject buildrespPrtyAct() {
        JSONObject jobj = new JSONObject();
        jobj.put("value", request.getPayorAcctNo());

        return jobj;
    }

    private JSONObject buildLblSpec() {
        JSONObject jobj = new JSONObject();
        jobj.put("labelFormatType", "COMMON2D");
        jobj.put("labelStockType", "PAPER_4X6");
        jobj.put("imageType", "PDF");
        return jobj;
    }

    private JSONArray buildRateReqType() {
        JSONArray jarr = new JSONArray();
        jarr.add("LIST");

        return jarr;

    }

    private JSONArray buildPkg() {
        JSONArray jarr = new JSONArray();
        JSONObject jobj = new JSONObject();
        DecimalFormat df = new DecimalFormat("###");
        jobj.put("customerReferences", buildCustRef());
        jobj.put("weight", buildpkgWeight());
        jobj.put("dimensions", buildPkgDim());

        jarr.add(jobj);
        return jarr;
    }

    private JSONArray buildCustRef() {
        JSONArray jarr = new JSONArray();
        //JSONObject jobj = new JSONObject();
        request.getCustomerReference().forEach(ref -> {
            if (!ref.getValue().trim().isEmpty()) {
                JSONObject jobj = new JSONObject();
                jobj.put("customerReferenceType", ref.getCustomerReferenceType());
                jobj.put("value", ref.getValue());
                jarr.add(jobj);
            }
        });

        return jarr;
    }

    private JSONObject buildpkgWeight() {
        JSONObject jobj = new JSONObject();
        DecimalFormat df = new DecimalFormat("###.00");
        jobj.put("units", request.getPkgWeight().getUnits());
        jobj.put("value", df.format(request.getPkgWeight().getValue()));

        return jobj;
    }

    private JSONObject buildPkgDim() {
        JSONObject jobj = new JSONObject();
        jobj.put("length", request.getPkgDim().getLength());
        jobj.put("width", request.getPkgDim().getWidth());
        jobj.put("height", request.getPkgDim().getHeight());
        jobj.put("units", request.getPkgDim().getUnits());
        return jobj;
    }

    private JSONObject buildAcctNo() {
        JSONObject jobj = new JSONObject();

        jobj.put("value", appParms.getAccountNumber());
        return jobj;
    }

    private boolean callHttp(String input) {
        boolean ret = false;
        Client client1 = Client.create();
        client1.addFilter(new GZIPContentEncodingFilter(false));
        String url = appParms.getServer().trim()
                + appParms.getShipPath();
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
        if (httpResponse.getLength() > 0) {
            this.jsonRsp = (JSONObject) JSONSerializer.toJSON(output);
        }
        // String s1 = httpResponse.toString();
        InputStream is = httpResponse.getEntityInputStream();

        if (appParms.isDebug()) {
            Map<String, Object> respProp;
            respProp = httpResponse.getProperties();
            logger.log(Level.ALL, "request is {0}", input);
            logger.log(Level.ALL, "URL= {0}", url);
            logger.log(Level.ALL, "Token is {0}", authToken);
            logger.log(Level.ALL, "Response is {0}", output);
            System.out.println("return type = " + httpResponse.getType());
            System.out.println("IS =" + is.toString());
            System.out.println("has entity? " + httpResponse.hasEntity());
            System.out.println("Entity=" + httpResponse.toString());
            System.out.println("status = " + httpResponse.getStatus() + "client status=" + httpResponse.getClientResponseStatus()
                    + "Entity tag=" + httpResponse.getEntityTag());
            System.out.println("response =" + output);
            for (String key : respProp.keySet()) {
                System.out.println(key + "=" + respProp.get(key));

            }
        }
        //      this.jsonRsp = (JSONObject) JSONSerializer.toJSON(output);

        // System.out.println(httpResponse.getEntity(String.class).toString());
//        System.out.println("httpResponse ="+ s1);
        this.response.setResponceStatus(httpResponse.getStatus());
        

        if (httpResponse.getStatus() != 200) {
            hdrLst = httpResponse.getHeaders();
            this.response.setMsgSeverity(httpResponse.getClientResponseStatus().toString());
          //  this.response.setMessage(httpResponse.getClientResponseStatus().toString());
          this.response.setMessage(Utilities.rtvRspMessage(jsonRsp));
//            logger.log(Level.SEVERE, "Error retreiving fedex label respons status ={0} request ={1}",
//                    new Object[]{
//                        httpResponse.getStatus(), request.getPtkt()
//                    });
            logger.log(Level.SEVERE, "Error retreiving fedex label respons status ={0} Ptkt ={1} request= {2} token = {3} "
                    + " url ={4} response={5}",
                    new Object[]
                    {httpResponse.getStatus(), request.getPtkt(),input, authToken, url, output});
            //deCompress(output.getBytes());
            System.out.println("Ship client HTTP status = " + httpResponse.getStatus());
            System.out.println("URL = " + url);
            System.out.println("auth Token =" + authToken);
            System.out.println("request =" + input);
            System.out.println("respone =" + output);
            for (String key : hdrLst.keySet()) {
                System.out.println(key + "=" + hdrLst.getFirst(key));
            }
        } else {
            ret = true;
            //this.jsonRsp = (JSONObject) JSONSerializer.toJSON(output);
            this.response.setMsgSeverity(NotificationSeverityType._SUCCESS);
            this.response.setMessage("");
        }

        return ret;
    }

    private void prcFxResponse() {

        JSONObject jobjOutput = new JSONObject();
        JSONArray jTrxShpmts = new JSONArray();
        if (jsonRsp.containsKey("output")) {
            jobjOutput = jsonRsp.getJSONObject("output");
            if (jobjOutput.containsKey("transactionShipments")) {
                jTrxShpmts = jobjOutput.getJSONArray("transactionShipments");
                //JSONObject jobj = jTrxShpmts.getJSONObject(0);
                prcFxTrnShp(jTrxShpmts.getJSONObject(0));
//               jTrxShpmts.forEach(obj -> {
//                   if (obj instanceof JSONObject) {
//                       JSONObject jobj = (JSONObject) obj;
//                       prcFxTrnShp(jobj);
//                   }
//               });
            }
        }
    }

    private void prcFxTrnShp(JSONObject fxTrnShipment) {
        if (fxTrnShipment.containsKey("pieceResponses")) {
            JSONArray jarr = fxTrnShipment.getJSONArray("pieceResponses");
            JSONObject jobj = jarr.getJSONObject(0);
            prcFxPieceResp(jobj);
//            jarr.forEach(obj ->{
//                if (obj instanceof JSONObject) {
//                       JSONObject jobj = (JSONObject) obj;
//                       prcFxPieceResp(jobj);
//                   }
//            });
        }
    }

    private void prcFxPieceResp(JSONObject pieceResp) {
        if (pieceResp.containsKey("trackingNumber")) {
            response.setTrkNo(pieceResp.getString("trackingNumber"));
        }
        if (pieceResp.containsKey("netRateAmount")) {
            response.setTotChrg(BigDecimal.valueOf(pieceResp.getDouble("netRateAmount")));
        }
        if (pieceResp.containsKey("packageDocuments")) {
            JSONArray jarray = pieceResp.getJSONArray("packageDocuments");
            for (int i = 0; i < jarray.size(); i++) {
                JSONObject jobj = jarray.getJSONObject(i);
                if (jobj.containsKey("contentType")) {
                    String contType = jobj.getString("contentType");
                    if (contType.equals("LABEL")) {
                        prcFxDoc(jobj);

                    }
                }
            }
//            jarray.forEach(obj -> {
//                if (obj instanceof JSONObject) {
//                    JSONObject jobj = (JSONObject) obj;
//                    if (jobj.containsKey("contentType")) {
//                        String contType = jobj.getString("contentType");
//                        if (contType.equals("LABEL")) {
//                            prcFxDoc(jobj);
//
//                        }
//
//                    }
//                }
//            });
//            prcFxDoc(jarray.getJSONObject(0));
        }
        if (pieceResp.containsKey("baseRateAmount")) {
            response.setTotChrg(BigDecimal.valueOf(pieceResp.getDouble("baseRateAmount")));
        }
    }

    private void prcFxDoc(JSONObject pkgDoc) {

        String labelLocation = appParms.getFedexLblLoc();
        int imgWidthOffset = 30;
        int imgHeightOffset = 60;

        if (labelLocation == null) {
            labelLocation = "c:\\";
        }
        int lblNum = 0;
        String labelFileName = labelLocation + "\\"
                + request.getPtkt() + "_"
                + request.getCrtnId() + "_"
                + ++lblNum + ".pdf";

        String labelFileGif = new String(labelLocation + "\\"
                + request.getPtkt() + "_"
                + request.getCrtnId() + "_"
                // + shippingDocumentType
                + lblNum + ".gif");

        if (pkgDoc.containsKey("encodedLabel")) {
            response.setPdfLoc(labelFileName);
            File labelFile = new File(labelFileName);
            String strLbl = pkgDoc.getString("encodedLabel");
            //byte[] b = strLbl.getBytes();
            byte[] lbl = Base64.decodeBase64(strLbl);

            InputStream in = new ByteArrayInputStream(lbl);
            try {
                BufferedImage bImageLbl = ImageIO.read(in);
            } catch (IOException ex) {
                Logger.getLogger(FedexShipClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            FileOutputStream fos;
            try {
                fos = new FileOutputStream(labelFile);
                fos.write(lbl);
                fos.close();
                PDDocument document = PDDocument.load(labelFile);
                List<PDPage> pgList = document.getDocumentCatalog().getAllPages();
                PDPage page = pgList.get(0);
                File gifFile = new File(labelFileGif);
                for (PDPage pdfPage : pgList) {
                    BufferedImage bImageLbl = pdfPage.convertToImage();
                    BufferedImage croppedImage = bImageLbl.getSubimage(imgWidthOffset, imgHeightOffset, 610, 890);
                    ImageIO.write(croppedImage, "GIF", gifFile);
                    response.setLblLoc(labelFileGif);
                }
                document.close();
                labelFile.delete();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FedexShipClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FedexShipClient.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
}
