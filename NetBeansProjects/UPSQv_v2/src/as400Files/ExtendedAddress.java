/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as400Files;

import net.sf.json.JSONObject;

/**
 *
 * @author Khatchik
 */
public class ExtendedAddress {

    private String type;            // secondary address type 
    private String low;             // low number 
    private String high;            // hight number 

    
    public ExtendedAddress(JSONObject jsonExtObj) {
        
        this.type = " "; 
        this.low = " ";
        this.high = " ";
        
        if (jsonExtObj.containsKey("Type")) {
            setType(jsonExtObj.getString("Type"));
        }
        if (jsonExtObj.containsKey("Low")) {
            setLow(jsonExtObj.getString("Low"));
        }
        if (jsonExtObj.containsKey("High")) {
            setHigh(jsonExtObj.getString("High"));
        }
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param low the low to set
     */
    public void setLow(String low) {
        this.low = low;
    }

    /**
     * @param high the high to set
     */
    public void setHigh(String high) {
        this.high = high;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the low
     */
    public String getLow() {
        return low;
    }

    /**
     * @return the high
     */
    public String getHigh() {
        return high;
    }

}
