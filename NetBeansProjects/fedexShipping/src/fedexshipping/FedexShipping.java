/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fedexshipping;

import models.CustomerReference;
import models.Weight;
import models.FedexShipmentRequest;
import models.Dimensions;
import static fedexshipping.FxConstatns.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.logging.Logger;
import models.CustomerReferenceType;
import models.FedexResponse;
import models.LinearUnits;
import models.PaymentType;
import models.PickupTypes;
import models.WeightUnits;

/**
 *
 * @author Khatchik
 */
public class FedexShipping {

    private static FxAppParms appParms = new FxAppParms();
    private static FedexShipmentRequest shipmentData;
    private static Logger logger;

    /**
     * @param args the command line arguments
     */
    public FedexShipping(FxAppParms parms, Logger logger) {
        this.appParms = parms;
        this.logger = logger;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        getAppParms(args[0]);
        logger = Logger.getLogger("fxLogger" );
        FedexResponse response = new FedexResponse();
       // GetAuth tAuth = new GetAuth(); //Test 
        GetAuth auth = new GetAuth(appParms,logger);

        System.out.println("------------ calling Client Http ------------------ ");
        if (auth.clientHttpCall() == true) {
            auth.print();
        }
        bldTstShipData();
        FxAddrValClient addrValClient = new FxAddrValClient(auth, appParms, logger);
        addrValClient.requestAddrVal(shipmentData);
        FedexShipClient shipClient = new FedexShipClient(auth, appParms, logger);
        response = shipClient.requestShipment(shipmentData);
        FedexCancelShipmentClient shipCancel = new FedexCancelShipmentClient(auth,appParms, logger);
        shipmentData.setTrkId(response.getTrkNo());
        shipCancel.cancelShipment(shipmentData);
    }

    private static void getAppParms(String parmFile) {
        XMLDecoder decoder;
        XMLEncoder encoder;

        parmFile = parmFile;
        try {
            decoder = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream(parmFile)));
            appParms = (FxAppParms) decoder.readObject();
        } catch (FileNotFoundException ex) {
            //test
            appParms.setClientId("l7c5ae4cdeda0a4cb3a477c683ee882c0b");
            appParms.setClientSecret("8ebcb165365240e284529ee3925c530a");
            //appParms.setAuthServer("https://apis-sandbox.fedex.com");
            appParms.setAuthPath("/oauth/token");
            appParms.setServer("https://apis-sandbox.fedex.com");
            appParms.setShipPath("/ship/v1/shipments");
            appParms.setAddrValPath("/address/v1/addresses/resolve");
            appParms.setCxlShpPath("/ship/v1/shipments/cancel");
//            appParms.setKey("x6ULy6ES93771ESI");
//            appParms.setPassWord("SGCmJBbUgSQulElMNwnaVbOAq");
//            appParms.setMeterNumber("119179456");
            appParms.setAccountNumber("203549279");
//            appParms.setEndPoint("https://wsbeta.fedex.com:443/web-services");
            appParms.setFedexLblLoc("C:\\Users\\Khatchik");
            appParms.setDebug(true);
            //prod 
//            appParms.setKey("tIMhNR6CHhdWxvWY");
//            appParms.setPassWord("GQKRgx98InWaXunjnSQw4BVIR");
//            appParms.setMeterNumber("252509861");
//            appParms.setAccountNumber("791906970");
//            appParms.setEndPoint("http://fedex.com/ws/ship/v26");
//            appParms.setServer("https://apis.fedex.com");
        }

        // update Parameters file
        try {
            System.out.println("saving parms    ");
            encoder = new XMLEncoder(
                    new BufferedOutputStream(
                            new FileOutputStream(parmFile)));
            encoder.writeObject(appParms);
            encoder.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            //AppParms.getLogger().log(Level.SEVERE, null, ex.getLocalizedMessage());
        }

    }

    private static void bldTstShipData() {
        shipmentData = new FedexShipmentRequest();
        shipmentData.setShipperName("");
        shipmentData.setShipperComp("A4 Moshay");
        shipmentData.setShipperPhone("3235850550");
        shipmentData.getShipperAdr().add("6199 MalBurg");
        shipmentData.setShipperCity("Vernon");
        shipmentData.setShipperStat("CA");
        shipmentData.setShipperZip("90058");
        shipmentData.setShipperCountry("US");
        shipmentData.setRecName("Recipient Name");
        shipmentData.setRecComp("");
        shipmentData.setRecPhone("8185481234");
        shipmentData.getRecAdr().add("1436 Dorothy Dr");
        shipmentData.setRecCity("Glendale");
        shipmentData.setRecStat("CA");
        shipmentData.setRecZip("91202");
        shipmentData.setRecCountry("US");
        shipmentData.setRecRes(false);
        //shipmentData.setDropOffTyp(REGULAR_PICKUP);
        shipmentData.setPickupType(PickupTypes.USE_SCHEDULED_PICKUP);
        shipmentData.setSrvTyp("GROUND_HOME_DELIVERY");
        shipmentData.setPkgTyp("YOUR_PACKAGING");
        shipmentData.setPaymentType(PaymentType.SENDER);
        shipmentData.setPayorAcctNo(appParms.getAccountNumber());
        shipmentData.setPayorCountry(shipmentData.getShipperCountry());
        shipmentData.setPkgWeight(fmtWeight(BigDecimal.valueOf(25.2), WeightUnits.LB));
        shipmentData.setPkgDim(fmtDim("12", "8", "4", LinearUnits.IN));
        //shipmentData.setCustomerReference(new CustomerReference[]{fmtCustomerReference(CustomerReferenceType.P_O_NUMBER, "PO1234")});
        shipmentData.getCustomerReference().add(fmtCustomerReference(CustomerReferenceType.P_O_NUMBER, "PO1234"));
        shipmentData.setOriginCompany("A4/MOSHAY INC");
        shipmentData.setOriginPhone("3235850550");
        shipmentData.getOrgAdr().add("6199 MALBURG");
        shipmentData.setOrgCity("VERNON");
        shipmentData.setOrgState("CA");
        shipmentData.setOrgZip("90058");
        shipmentData.setOrgCountry("US");
        shipmentData.setPtkt("1643458");
        shipmentData.setCrtnId("2097630");
        shipmentData.setTrkId("275217285XXX");

    }

    public static CustomerReference fmtCustomerReference(CustomerReferenceType type, String value) {
        CustomerReference ref = new CustomerReference(type, value);
        //ref.setCustomerReferenceType(type);
        //ref.setValue(value);
        return ref;
    }

    public static Weight fmtWeight(BigDecimal value, WeightUnits unit) {
        Weight wt = new Weight();
        wt.setUnits(unit);
        wt.setValue(value);

        return wt;
    }

    public static Dimensions fmtDim(String length, String width, String height, LinearUnits linearUnits) {
        Dimensions dim = new Dimensions();
        dim.setLength(Integer.parseInt(length));
        dim.setWidth(Integer.parseInt(width));
        dim.setHeight(Integer.parseInt(height));
        dim.setUnits(linearUnits);
        return dim;
    }

}
