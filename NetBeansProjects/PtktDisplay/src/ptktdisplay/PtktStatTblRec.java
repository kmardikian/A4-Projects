/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktdisplay;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Khatchik
 */
public class PtktStatTblRec {
    private final SimpleStringProperty  stat;
    private final SimpleIntegerProperty count;
    private final SimpleStringProperty units;
    private final SimpleStringProperty dlrs;

public PtktStatTblRec (String stat, Integer count, BigDecimal units, BigDecimal dlrs) {
    Locale enUSLocale =
    new Locale.Builder().setLanguage("en").setRegion("US").build();
    NumberFormat currencyFormatter = 
        NumberFormat.getCurrencyInstance(enUSLocale);
    DecimalFormat numFormatter = new DecimalFormat("###,###,###");
    this.stat = new SimpleStringProperty(stat);
    this.count = new SimpleIntegerProperty(count);
    this.units =  new SimpleStringProperty(numFormatter.format(units));
    this.dlrs = new SimpleStringProperty(currencyFormatter.format(dlrs));
}  

    public String getStat() {
        return stat.get();
    }

    public Integer getCount() {
        return count.get();
    }

    public String getUnits() {
        return units.get();
    }

    public String getDlrs() {
        return dlrs.get();
    }
    public SimpleStringProperty StatProperty() {
        return stat;
    }
    public SimpleIntegerProperty CountProperty() {
        return count;
    }
    public SimpleStringProperty UnitsProperty() {
        return units;
    }
    public SimpleStringProperty DlrsProperty() {
        return dlrs;
    }

    

    

   
    }

   