package ptktData;


import java.math.BigDecimal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Khatchik
 */
public class PtktStat {

    private final String stat;
    private final BigDecimal statNum;

    public PtktStat(String stat, BigDecimal statNum) {
        this.stat = stat;
        this.statNum = statNum;
    }

    public String getStat() {
        return stat;
    }

    public BigDecimal getStatNum() {
        return statNum;
    }
    
}
