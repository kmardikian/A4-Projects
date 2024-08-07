/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fedexshipping;

import net.sf.json.JSONObject;

/**
 *
 * @author Khatchik
 */
public class Utilities {
    
    static public String rtvRspMessage(JSONObject jResp) {
        String sMessage = null;
        
        if(jResp.containsKey("errors")) {
            JSONObject jErrors = jResp.getJSONArray("errors").getJSONObject(0);
            if (jErrors.containsKey("message")) {
                sMessage = jErrors.getString("message");
            }
        }
                
        return sMessage;
    }
    
}
