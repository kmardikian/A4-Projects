/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upsrate;

//import as400Files.UPSAuth;
import model.UPSParms;
import com.sun.jersey.api.client.Client;            // jersey-client.jar
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import org.apache.commons.codec.binary.Base64;
//import java.net.http.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Khatchik
 */
public class GetAuth {

    //private final String host = "https://wwwcie.ups.com";
    //private final String pathname = "/security/v1/oauth/token";
    //private final JSONObject jsonObj = new JSONObject();
    private String tokenType;
    private String issueAt;
    private String clientId;
    private String accessToken;
    private long expiresIn;
    private String status;
    private final UPSParms upsParms;
    private final As400UPSAuth as400upsAuth;
    private final Logger logger;

    public GetAuth(UPSParms upsParms, As400UPSAuth as400UPSAuth, Logger logger) {
        this.upsParms = upsParms;
        this.as400upsAuth = as400UPSAuth;
        this.logger = logger;
    }

    public void getAuth() {
        //String input;

        // jsonObj.put("grant_type","client_credentials");
        String form = "grant_type=client_credentials";
//    form = parms.keySet().stream()
//                .map(key-> key + "=" + URLEncoder.encode(parms.get(key),
//                StandardCharsets.UTF_8))
//        .collect(Collectors.joining("&"));
//    
        Client client = Client.create();
        //WebResource webResource = client.resource(host + pathname);
        WebResource webResource = client.resource(upsParms.getUpsServer() + upsParms.getUpsAutPath());
        MultivaluedMap<String, String> hdrLst;

//HttpRequest.
        // input = jsonObj.toString();
        ClientResponse response = webResource.accept("application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("x-merchant-id", upsParms.getUpsShprNo().trim())
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(
                        (as400upsAuth.getClientId().trim() + ":"
                                + as400upsAuth.getUpsSecCd().trim()).getBytes()))
                .post(ClientResponse.class, form);

        if (response.getStatus() != 200) {
            //System.out.println("http Failed status = " + response.getStatus());
            if (upsParms.isDebug() == true) {
                hdrLst = response.getHeaders();
                for (String key : hdrLst.keySet()) {
                    System.out.println(key + "=" + hdrLst.getFirst(key));

                }
            }
            logger.log(Level.SEVERE, null, "Failed : HTTP error code :" + response.getStatus());
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        String output = response.getEntity(String.class);
//        AppParms.getLogger().log(Level.INFO, "respone is " + output);
        JSONObject jsonRsp = (JSONObject) JSONSerializer.toJSON(output);
        if (jsonRsp.containsKey("token_type")) {
            tokenType = jsonRsp.getString("token_type");
        }
        if (jsonRsp.containsKey("issued_at")) {
            this.issueAt = jsonRsp.getString("issued_at");
        }
        if (jsonRsp.containsKey("client_id")) {
            this.clientId = jsonRsp.getString("client_id");
        }
        if (jsonRsp.containsKey("access_token")) {
            this.accessToken = jsonRsp.getString("access_token");
        }
        if (jsonRsp.containsKey("expires_in")) {
            this.expiresIn = Long.parseLong(jsonRsp.getString("expires_in"));
        }
        if (jsonRsp.containsKey("status")) {
            this.status = jsonRsp.getString("status");
        }

        if (this.getStatus().equals("approved")) {
            // update file in AS400 
            DateTimeFormatter formatterYmd = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter formatterHms = DateTimeFormatter.ofPattern("HHmmss");
            //SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyyMMdd);"
            //        + " HH:mm:ss,SSS"

            LocalDateTime curDt = LocalDateTime.now();

            LocalDateTime expDt = LocalDateTime.now();
            if (this.expiresIn <= 1800) {
                expDt = expDt.plusSeconds(this.expiresIn);
            } else {
                expDt = expDt.plusSeconds(this.expiresIn - 1800);
            }

            String wrkDt = curDt.format(formatterYmd);
            BigDecimal issDt = new BigDecimal(wrkDt);
            as400upsAuth.setClientId(clientId);
            as400upsAuth.setIssDate(issDt);
            as400upsAuth.setExpIn(expiresIn);
            as400upsAuth.setIssTime(new BigDecimal(curDt.format(formatterHms)));
            as400upsAuth.setExpDate(new BigDecimal(expDt.format(formatterYmd)));
            as400upsAuth.setExpTime(new BigDecimal(expDt.format(formatterHms)));
            as400upsAuth.setIssAt(issueAt);
            as400upsAuth.setShipper(upsParms.getUpsShprNo());
            as400upsAuth.setStatus(status);
            as400upsAuth.setToken(this.accessToken);
            as400upsAuth.setTokenTp(tokenType);

            as400upsAuth.updateAs400();
        } else {
            Logger.getLogger(GetAuth.class.getName()).log(Level.SEVERE, null,
                    "token type "
                    + tokenType
                    + "\n issued at = " + issueAt
                    + "\n clientId = " + clientId
                    + "\n accessToken = " + accessToken
                    + "\n expiresIn = " + expiresIn
                    + "\n staatus = " + status
            );
        }
    }
//public String getHost() {
//        return host;
//    }
//
//    public String getPathname() {
//        return pathname;
//    }
//    

//    public JSONObject getJsonObj() {
//        return jsonObj;
//    }
    public String getTokenType() {
        return tokenType;
    }

    public String getIssueAt() {
        return issueAt;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "token type "
                + tokenType
                + "\n issued at = " + this.issueAt
                + "\n clientId = " + this.clientId
                + "\n accessToken = " + this.accessToken
                + "\n expiresIn = " + this.expiresIn
                + "\n staatus = " + this.status;
    }

}
