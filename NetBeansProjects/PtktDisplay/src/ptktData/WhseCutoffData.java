/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptktData;

/**
 *
 * @author Khatchik
 */
public class WhseCutoffData {
    private String whse;
    private Double cutOffTime;

    public WhseCutoffData(String whse, Double cutOffTime) {
        this.whse = whse;
        this.cutOffTime = cutOffTime;
    }
    
    

    public String getWhse() {
        return whse;
    }

    public void setWhse(String whse) {
        this.whse = whse;
    }

    public Double getCutOffTime() {
        return cutOffTime;
    }

    public void setCutOffTime(Double cutOffTime) {
        this.cutOffTime = cutOffTime;
    }
   
    
    
}
