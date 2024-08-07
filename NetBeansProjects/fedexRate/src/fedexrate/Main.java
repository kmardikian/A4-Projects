/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fedexrate;

import model.RateInfo;

/**
 *
 * @author Khatchik
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RateClient rateClient = new RateClient(args[0]);
        rateClient.setRateData("A4/MOSHAY INC"
                , "6199 MALBURG"
                , ""
                , ""
                , "VERNON"
                , "CA"
                , "90058"
                , "US"
                , "Khatchik Mardikian"
                , "806 Elm St"
                , ""
                ,""
                , "Glendale"
                , "CA"
                , "91205"
                , "US"
                , "USE_SCHEDULED_PICKUP"
                , "FEDEX_GROUND"
                , "2.15"
                , "LB");
       String rateInfo = rateClient.callRate();
        System.out.println("rateOnfo =" + rateInfo);
        // TODO code application logic here
    }
    
}
