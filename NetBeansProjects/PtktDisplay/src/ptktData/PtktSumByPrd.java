/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Khatchik
 */
public class PtktSumByPrd  implements Comparable<PtktSumByPrd>{

    private Map<String, PtktSumByStat> sumByStat = new HashMap<>();
    private final String period;
    private final int row;
    private int prt;
    private int dis;
//    private int asg;
    private int pck;
    private int pak;
    private int cpk;
    private int wgh;
    private int shp;
    private int inv;
    

    public PtktSumByPrd(String period,int row) {
        this.period = period;
        this.row = row;
        prt = 0;
        dis = 0;
//        asg = 0;
        pck = 0;
        cpk = 0;
        pak = 0;
        wgh = 0;
        shp = 0;
        inv = 0;

    }
    public void addStat(String stat, BigDecimal units, BigDecimal dlrs, Integer crtns) {
        if (!sumByStat.containsKey(stat)) {
            sumByStat.put(stat,new PtktSumByStat(stat,BigDecimal.ZERO));
        }
        sumByStat.get(stat).addStat(units, dlrs, crtns);
        
    }

    public int getPak() {
        return pak;
    }

    public void setPak(int pak) {
        this.pak = pak;
    }
    public void incPak() {
        this.pak = this.pak + 1;
    }

    public String getPeriod() {
        return period;
    }

   
    public int getPrt() {
        return prt;
    }

    public void setPrt(int prt) {
        this.prt = prt;
    }

    public void incPrt() {
        this.prt = this.prt + 1;
    }

    public int getDis() {
        return dis;
    }
    public void incDis() {
        this.dis = this.dis + 1;
    }

    public void setDis(int dis) {
        this.dis = dis;
    }

//    public int getAsg() {
//        return asg;
//    }
//
//    public void setAsg(int asg) {
//        this.asg = asg;
//    }
//    public void incAsg() {
//        this.asg = this.asg + 1;
//    }

    public int getPck() {
        return pck;
    }

    public void setPck(int pck) {
        this.pck = pck;
    }
    public void incPck() {
        this.pck = this.pck + 1;
    }

    public int getCpk() {
        return cpk;
    }

    public void setCpk(int cpk) {
        this.cpk = cpk;
    }
    public void incCpk() {
        this.cpk = this.cpk + 1;
    }

    public int getWgh() {
        return wgh;
    }

    public void setWgh(int wgh) {
        this.wgh = wgh;
    }
    public void incWgh() {
        this.wgh = this.wgh + 1;
    }

    public int getShp() {
        return shp;
    }

    public void setShp(int shp) {
        this.shp = shp;
    }
    public void incShp() {
        this.shp = this.shp + 1;
    }

    public int getInv() {
        return inv;
    }

    public void setInv(int inv) {
        this.inv = inv;
    }
    public void incInv() {
        this.inv = this.inv + 1;
    }
    public int getTot() {
        return prt+dis+pck+pak+cpk+wgh+shp+inv;
    }
    public ArrayList<PtktSumByStat> getSumByStat() {
        ArrayList<PtktSumByStat> sumByStatList = new ArrayList<>();
        sumByStatList.addAll(sumByStat.values());
                
        return sumByStatList;
    }
    public PtktSumByStat getSumByStat(String stat ) {
        PtktSumByStat byStat = null;
        if (sumByStat.containsKey(stat)) {
            byStat = sumByStat.get(stat);
        }
        
        return byStat;
        
    }

    @Override
    public int compareTo(PtktSumByPrd o) {
        int result; 
        if (this.row < o.row) {
            result = -1;
        } else if (this.row == o.row) {
            result = 0;
        } else {
            result = 1;
        }
        return result;
    }

}
