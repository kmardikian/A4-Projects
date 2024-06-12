/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktData;

/**
 *
 * @author Khatchik
 */
public class WrhData {
    private String wrhCode;
    private String wrhName;

    public WrhData(String wrhCode, String wrhName) {
        this.wrhCode = wrhCode;
        this.wrhName = wrhName;
    }

    public String getWrhCode() {
        return wrhCode;
    }

    public void setWrhCode(String wrhCode) {
        this.wrhCode = wrhCode;
    }

    public String getWrhName() {
        return wrhName;
    }

    public void setWrhName(String wrhName) {
        this.wrhName = wrhName;
    }
    
    
    
}
