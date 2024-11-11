/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptktdisplay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Khatchik
 */
public class KpiTblRow implements Comparable<KpiTblRow> {

    private SimpleStringProperty description;
    private SimpleIntegerProperty numPrt;
    private SimpleIntegerProperty numOpen;
    private SimpleIntegerProperty numShp;
    private SimpleDoubleProperty pctShp;

    public KpiTblRow(String description, Integer numOpen, Integer numShp) {
        this.description = new SimpleStringProperty(description);
        this.numOpen = new SimpleIntegerProperty(numOpen);
        this.numShp = new SimpleIntegerProperty(numShp);
        this.numPrt = new SimpleIntegerProperty(numOpen + numShp);

        BigDecimal pctShip = BigDecimal.ZERO;
        if (numOpen > 0) {
            pctShip = BigDecimal.valueOf(numShp)
                    .multiply(BigDecimal.valueOf(100)
                            .divide(BigDecimal.valueOf(numOpen + numShp), 2, RoundingMode.HALF_EVEN));
        }
        this.pctShp = new SimpleDoubleProperty(pctShip.doubleValue());
    }

    public SimpleStringProperty getDescription() {
        return description;
    }

    public void setDescription(SimpleStringProperty description) {
        this.description = description;
    }

    public SimpleIntegerProperty getNumPrt() {
        return numPrt;
    }

    public Integer getNumPrtValue() {
        return numPrt.getValue();
    }
    public String getNumPrtString() {
        return getNumPrtValue().toString();
    }
    public void setNumPrt(SimpleIntegerProperty numPrt) {
        this.numPrt = numPrt;
    }

    public SimpleIntegerProperty getNumShp() {
        return numShp;
    }
    public String getNumShpString() {
        return getNumShpValue().toString();
    }
    public Integer getNumShpValue() {
        return numShp.getValue();
    }

    public void setNumShp(SimpleIntegerProperty numShp) {
        this.numShp = numShp;
    }

    public SimpleDoubleProperty getPctShp() {
        return pctShp;
    }
    public Double getPctShpValue() {
        return pctShp.getValue();
    }
    public String getPctShpString() {
        return getPctShpValue().toString();
    }

    public void setPctShp(SimpleDoubleProperty pctShp) {
        this.pctShp = pctShp;
    }

    public SimpleIntegerProperty getNumOpen() {
        return numOpen;
    }
    public Integer getNumOpenValue() {
        return numOpen.getValue();
    }
    public String getNumOpenString() {
        return getNumOpenValue().toString();
    }

    public void setNumOpen(SimpleIntegerProperty numOpen) {
        this.numOpen = numOpen;
    }
    

    @Override
    public int compareTo(KpiTblRow o) {

        return this.description.getValue().compareTo(o.description.getValue());

    }

}
