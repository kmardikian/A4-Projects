/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fedexshipping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.FedexResponse;
import models.FedexShipmentRequest;

/**
 *
 * @author Khatchik
 */
public class FedexClient {

    private final FxAppParms appParms;
    private final Logger logger;
    private final GetAuth auth;

    public FedexClient(FxAppParms appParms, Logger logger) {
        this.appParms = appParms;
        this.logger = logger;
        this.auth = new GetAuth(this.appParms, logger);
        this.auth.clientHttpCall();
    }

    public FedexResponse getShipLabel(FedexShipmentRequest request) {
        ckAuthExp();
        FedexShipClient shipClient = new FedexShipClient(auth, appParms, logger);
        return shipClient.requestShipment(request);
    }

    public FedexResponse validateAddress(FedexShipmentRequest request) {
        ckAuthExp();
        FxAddrValClient addrValClient = new FxAddrValClient(auth, appParms, logger);
        return addrValClient.requestAddrVal(request);
    }

    public void cancelShipLabel(FedexShipmentRequest request) {
        ckAuthExp();
        FedexCancelShipmentClient cancelShip = new FedexCancelShipmentClient(auth, appParms, logger);
        cancelShip.cancelShipment(request);
    }

    private void ckAuthExp() {
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter formatterYmd = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        if (timeNow.isAfter(auth.getExpTime())) {
//            logger.log(Level.SEVERE,"Auth expired expTime is {0} curretn time is {1}",
//                    new Object[]{ formatterYmd.format(auth.getExpTime())
//                            , formatterYmd.format(timeNow)
//                    } );
            auth.clientHttpCall();
        }

    }

}
