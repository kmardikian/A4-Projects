/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktData;

/**
 *
 * @author khatchik
 */
public class PtktPrd {
    private final String prdLbl;
    private final int prdRow;
    private final Double prdTim;
    public PtktPrd(String lbl, int row, Double time) {
        this.prdLbl= lbl;
        this.prdRow= row;
        this.prdTim = time;
    }

    public String getPrdLbl() {
        return prdLbl;
    }

    public int getPrdRow() {
        return prdRow;
    }

    public Double getPrdTim() {
        return prdTim;
    }
    
}
