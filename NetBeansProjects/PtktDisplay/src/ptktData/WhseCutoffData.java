/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptktData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Khatchik
 */
public class WhseCutoffData {

    private String whse;
    private Double cutOffTime;

    LocalDateTime lDtm_DayCur;
    LocalDateTime lDtm_DayB4;

    public WhseCutoffData(String whse, String b4_5Pm_priDtm, String b4_5Pm_curDtm, Double cutOffTime) {
        this.whse = whse;
        this.cutOffTime = cutOffTime;

//        lDtm_DayCur = LocalDateTime.of(LocalDate.now(),
//                LocalTime.of(Integer.parseInt(db4CutOffHH),
//                        Integer.parseInt(db4CutOffMM))
//        );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        this.lDtm_DayB4=LocalDateTime.parse(b4_5Pm_priDtm, formatter);
        this.lDtm_DayCur=LocalDateTime.parse(b4_5Pm_curDtm, formatter);

        //DayOfWeek day = lDtm_DayCur.getDayOfWeek();
//        if (lDtm_DayCur.getDayOfWeek().compareTo(DayOfWeek.MONDAY) == 0) {
//            lDtm_DayB4 = lDtm_DayCur.minusDays(3);
//        } else {
//            lDtm_DayB4 = lDtm_DayCur.minusDays(1);
//        }
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

    public LocalDateTime getlDtm_DayCur() {
        return lDtm_DayCur;
    }

    public void setlDtm_DayCur(LocalDateTime lDtm_DayCur) {
        this.lDtm_DayCur = lDtm_DayCur;
    }

    public LocalDateTime getlDtm_DayB4() {
        return lDtm_DayB4;
    }

    public void setlDtm_DayB4(LocalDateTime lDtm_DayB4) {
        this.lDtm_DayB4 = lDtm_DayB4;
    }

}
