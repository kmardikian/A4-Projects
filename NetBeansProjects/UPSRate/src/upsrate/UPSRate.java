/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upsrate;

/**
 *
 * @author Khatchik
 */
public class UPSRate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RateClient rateClient = new RateClient(args[0]);
        rateClient.setRateData("A4 Moshay"
                , "6199 MALBURG WAY"
                , " "
                , " "
                , "VERNON"
                , "CA"
                , "90058"
                , "US"
                , "A4/MOSHAY INC"
                , "6199 MALBURG WAY"
                , ""
                , ""
                , "VERNON"
                , "CA"
                , "90058"
                , "US"
                , "816-542-0610" 
                , ""
                , "Khatchik"
                , "1454 Verdugo Rd"
                , ""
                , ""
                , "Los Angeles"
                , "CA"
                , "90065"
                , "US"
                , "828-548-5555"
                , " "
                , "02"
                , "03" 
                ,"UPS GROUND SERVICE"
            //    , "1");
        );
        
        rateClient.setPkgData("02"
                , "Package"
                , "IN"
                , "INCH"
                , "18"
                , "10"
                , "6"
                , "LBS"
                ,""

             //   , "Pounds"
                , "1");
        
        rateClient.callUpsRate();
        
        System.out.println("from main upsRate=" + rateClient.getUpsRate());
//       if (rateClient.callUpsRate().substring(0, 3).equals("200") ) {
//        System.out.println("from main upsRate =" + rateClient.getUpsRate() );
//        }
    }
    
}
