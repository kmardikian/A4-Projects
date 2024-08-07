/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fedexshipping;

/**
 *
 * @author Khatchik
 */
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;

//import okhttp3.*;

//import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import javax.ws.rs.core.MultivaluedMap;

public class GetAuth {
    private final String clientId ="l7e231abde67a444d9b209ea334eb13f40";
    private final String clientSecret ="73e166edc3af40de8d21518cf47aa902";
    private final String prodServer = "https://apis-sandbox.fedex.com";
    private final String testServer ="https://apis.fedex.com";
    private final String path ="/oauth/token";
    private final FxAppParms appParms ;
    private final Logger logger; 

    private String url;
    //private String input;
    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private LocalDateTime authReqestTime;
    private LocalDateTime expTime;
//    public GetAuth() {
//        appParms = new FxAppParms();
//        logger = Logger.getLogger(GetAuth.class.getName());
//        okhttpCall();
//    }

    public GetAuth(FxAppParms appParms, Logger loger) {
        this.appParms = appParms;
        this.logger   = loger;
        this.url = appParms.getServer() +
                appParms.getAuthPath(); 
    }

    
//    public GetAuth() {
//        url = testServer + path;
//        input = "grant_type=client_credentials&client_id=" + appParms.getClientId()
//                + "&client_secret=" + clientSecret;
//    }

//    public void okhttpCall() {
//        JSONObject jobj = new JSONObject();
//        jobj.put("grant_type","client_credentials");
//        jobj.put("client_id",clientId);
//        jobj.put("client_secret",clientSecret);
//        
//        String jsonReq = jobj.toString();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType,jsonReq);
//        OkHttpClient client = new OkHttpClient();
//
//        Request  request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            if (response.code() != 200 ) {
//                System.out.println("error getting Fedex auth --code =" + response.code()  );
//                System.out.println("message = " + response.message());
//                System.out.println("body = " + response.body());
//            }
//        } catch(Exception ex ) {
//            System.out.println("IO exception  OKHTTP call ex = " + ex.getMessage());
//        }
//
//
//    }

    public boolean clientHttpCall() {
        
        MultivaluedMap<String, String> hdrLst;
        Client client1 = Client.create();
        String input = "grant_type=client_credentials&client_id=" + appParms.getClientId()
                + "&client_secret=" + appParms.getClientSecret();
        
        client1.addFilter(new GZIPContentEncodingFilter(false));

        WebResource webResource = client1.resource(url);

        ClientResponse httpResponse = webResource.accept("application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(ClientResponse.class, input );

        String output = httpResponse.getEntity(String.class);
       // System.out.println("response=" + output);
        if (httpResponse.getStatus() != 200) {
           // System.out.println("request =" + input);
           // System.out.println("error getting FEDEX auth 2 response = " + output);
            logger.log(Level.SEVERE, "Error retreiving fedex Auth token status ={0} request= {1} URL = {2} response= {3}",
                    new Object[]{httpResponse.getStatus(), input, url, output});
            hdrLst = httpResponse.getHeaders();
            for (String key : hdrLst.keySet()) {
                System.out.println(key + "=" + hdrLst.getFirst(key));

            }
            return false;
        }

        JSONObject jsonRsp = (JSONObject) JSONSerializer.toJSON(output);
        if (jsonRsp.containsKey("access_token")) {
            accessToken = jsonRsp.getString("access_token");
        }
        if (jsonRsp.containsKey("token_type")) {
            tokenType = jsonRsp.getString("token_type");
        }
        if (jsonRsp.containsKey("expires_in")) {
            expiresIn = jsonRsp.getString("expires_in");
        }
        this.authReqestTime =LocalDateTime.now();
        Long expInLong = Long.parseLong(this.expiresIn) ;
        if (Long.parseLong(this.expiresIn) >  600) {
            expInLong = Long.parseLong(this.expiresIn) - 600;
        }
        
        this.expTime = this.authReqestTime.plusSeconds(expInLong);

        return true;

    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public LocalDateTime getAuthReqestTime() {
        return authReqestTime;
    }

    public void setAuthReqestTime(LocalDateTime authReqestTime) {
        this.authReqestTime = authReqestTime;
    }

    public LocalDateTime getExpTime() {
        return expTime;
    }

    public void setExpTime(LocalDateTime expTime) {
        this.expTime = expTime;
    }

    public void print() {
        DateTimeFormatter formatterYmd = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        System.out.println("Access Toke = " + this.accessToken);
        System.out.println("token type = " + this.tokenType);
        System.out.println("request date and time " + this.authReqestTime.format(formatterYmd));
        System.out.println("expires in " + this.expiresIn);
        System.out.println("expires at " + this.expTime.format(formatterYmd));
    }
}