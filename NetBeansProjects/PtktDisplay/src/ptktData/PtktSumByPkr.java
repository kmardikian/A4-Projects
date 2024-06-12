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
public class PtktSumByPkr implements Comparable<PtktSumByPkr> {

    private final String picker;
    private Map<String, PtktSumByStat> sumByStat = new HashMap<>();

    public PtktSumByPkr(String picker) {
        this.picker = picker;

    }

    public void addStat(String stat, BigDecimal units, BigDecimal dlrs, Integer crtnCnt) {
        // if  status not found add to map
        if (!sumByStat.containsKey(stat)) {
            sumByStat.put(stat, new PtktSumByStat(stat, BigDecimal.ZERO));
        }

        sumByStat.get(stat).addStat(units, dlrs,crtnCnt );

    }

    public String getPicker() {
        return picker;
    }

    public ArrayList<PtktSumByStat> getSumByStat() {
        ArrayList<PtktSumByStat> sumByStatList = new ArrayList<>();
        sumByStatList.addAll(sumByStat.values());
                
        return sumByStatList;
    }
    

    @Override
    public int compareTo(PtktSumByPkr o) {
        return (this.picker.compareTo(o.picker));
    }

}
