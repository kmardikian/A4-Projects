/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.BigDecimal;

/**
 *
 * @author Khatchik
 */
public class Weight {
    private WeightUnits units;
    private BigDecimal value; 

    
    public Weight() {
    }

    public Weight(WeightUnits units, BigDecimal value) {
        this.units = units;
        this.value = value;
    }
    

    public WeightUnits getUnits() {
        return units;
    }

    public void setUnits(WeightUnits units) {
        this.units = units;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setUnits() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    
    
    
       
}
