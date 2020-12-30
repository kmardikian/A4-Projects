/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.SH2PYRNO
 */
package pickticketprint;

import UPS.UpsResponse;
import UPS.CreateUpsLabel;
import UPS.LblRspStat;
import UPS.VoidTracking;
import com.a4.utils.ConnectAs400;
import com.fedex.ship.stub.CustomerReferenceType;
import com.fedex.ship.stub.DropoffType;
import com.fedex.ship.stub.LinearUnits;
import com.fedex.ship.stub.NotificationEventType;
import com.fedex.ship.stub.NotificationSeverityType;
import com.fedex.ship.stub.PaymentType;
import com.fedex.ship.stub.WeightUnits;
import fedexshipclient.FedexResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import prtPtkt.PrintPtkt;
import fedexshipclient.FedexShipClient;
import fedexshipclient.FedexShipmentRequest;
import fedexshipclient.FxAppParms;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Khatchik
 */
public class RequestedPickTickets {

    private final Connection con;
    private final String dtaLib;
    private final UpsShprParms parms;
    private final PrintPtkt ptktPrint;
    private final SvcRtn svcRtn;

    ;

    public RequestedPickTickets(UpsShprParms parms) {
        con = ConnectAs400.getConnection();
        dtaLib = parms.getAs400Lib();
        this.parms = parms;
        this.ptktPrint = new PrintPtkt(parms);
        svcRtn = new SvcRtn();

    }

    public void rtvCrtnData() {
        String lbl = " ";
        String trkNo = " ";
        String bxDim;
        boolean fstTkt = true;
        BigDecimal bxLen;
        BigDecimal bxWid;
        BigDecimal bxHt;
        CreateUpsLabel createUpsLabel = new CreateUpsLabel();

        Date wDate;
        String qry = "Select "
                + "a.CHPCK#, a.CHCAR#,CHPUCC,CHCWGT,CHTOTQ, "
                + "CHPCKC,CHTRK,CHRQPTFL,CHCSEQ,CHCRCD,CHSVCD,h.chCnt,"
                + "SH2NAM,SH2CON, SH2AD1, SH2AD2, SH2CTY, SH2STT,"
                + "SH2ZIP, SH2PHN, SH2REF1, SH2REF2,SH2REF3,"
                + "SH2EML, FEDBDY, SH2BTIN, SH2SHPR,"
                + "SH2SHAD1, SH2SHAD2,SH2SHNAM,SH2SHPH,SH2SHCTY,SH2SHST,"
                + "SH2SHZIP,SH2SHCNTR,"
                + "SH2FRNAM, SH2FRATN, SH2FRAD1, SH2FRAD2,"
                + "SH2FRAD3, SH2FRCTY, SH2FRST, SH2FRZIP,"
                + "SH2FRCNTR, SH2FRPH,"
                + "SH2SHID,SH2FRNAM, SH2FRATN, SH2FRAD1, SH2FRAD2,"
                + "SH2FRAD3, SH2FRCTY, SH2FRST, SH2FRZIP,"
                + "SH2FRCNTR, SH2FRPH,SH2BTIN,SH2PYRNO,SH2SONAM,SH2SOAD1,"
                + "SH2SOAD2,SH2SOAD3,SH2SOCTY,SH2SOST,SH2SOZIP,SH2SOCNTR,"
                + "SH2SOEML,SH2SOPH,SH2SOCNTR,  "
                //  + "EPDLNG, EPDWID, EPDHGT, EPDDUM ,"
                + "ifnull(c.cnDes, ' ') as BXDIM, "
                + "PKHSOL,PKHVIA,PKHPDT,PKHPTM,PKUPSVCD, ORORD,ORPO#,"
                + "PKCRCD,PKFXSVCD,oroty"
                + ",ORDTE,ORSDT,ORTDT,orsi1,orsi2,orsi3,orsi4,orsi5,orsi6,"
                + "f.CNDES as OR_TERM,ORREM, substring(g.cnDes,1,30) as SVIAD "
                + ",ifnull(i.cndes,' ') as SVIA@V "
                + " from " + dtaLib + ".ccarph a "
                + " join " + dtaLib + ".upsord b "
                + " on a.chpck# = b.PCKTKT "
                //                + " join " + dtaLib + ".XEIPCL01 c "
                //                + " on c.epdcod = a.chpckc "
                + " left join " + dtaLib + ".CNTLP c "
                + " on c.cnelt = 'BT' "
                + " and c.cnelk = a.chpckc "
                + "join " + dtaLib + ".pkhdrp d "
                + "on PKHPCK= a.chpck# "
                + "join " + dtaLib + ".ordrph e "
                + "on orord = pkhord "
                + "join " + dtaLib + ".CNTLP f"
                + " on f.cnelt='TM' "
                + "and f.cnelk = e.ortrm "
                + " join " + dtaLib + ".CNTLP g "
                + " on g.cnelt = 'SV' "
                + "and g.cnelk = PKHVIA "
                + "join (select CHPCK#,int(max(CHCSEQ)) as chCnt "
                + "from " + dtaLib + ".ccarph "
                + "group by chpck#) h "
                + "on h.chpck# = a.chpck# "
                + "left join " + dtaLib + ".CNTLP i "
                + "on i.cnelt = '@V' "
                + "and i.cnelk = PKHVIA "
                + "where CHRQPTFL in('Y','R') "
                //+ "where CHPCK# = 1180221 "
                + "And PKHWRH ='" + parms.getWhse() + "' "
                + "Order by a.CHPCK#,CHCSEQ, a.CHCAR#";

        PtktData ptktData = new PtktData();
        ArrayList<String> frmAdr = new ArrayList<>();
        ArrayList<String> shprAdr = new ArrayList<>();
        ArrayList<String> toAdr = new ArrayList<>();
        ArrayList<String> orSi = new ArrayList<>();
        UpsResponse upsResponse;
        String pkRef;
        int crtnSeq = 0;
        BigDecimal svPck = BigDecimal.ZERO;
        DecimalFormat ptktNoFmt = new DecimalFormat("0000000");
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(qry);) {
            while (rs.next()) {
                shprAdr.clear();
                frmAdr.clear();
                toAdr.clear();
                orSi.clear();
                if (!rs.getBigDecimal("CHPCK#").equals(svPck)) {
                    crtnSeq = 0;
                }
                svPck = rs.getBigDecimal("CHPCK#");

                shprAdr.add(rs.getString("SH2SHAD1"));
                shprAdr.add(rs.getString("SH2SHAD2"));
                ptktData.setShprName(rs.getString("SH2SHNAM"));
                ptktData.setShprDspName(rs.getString("SH2SHNAM"));
                ptktData.setShprPh(rs.getString("SH2SHPH"));
                ptktData.setShprShprNo(rs.getString("SH2SHPR"));
                ptktData.setShprAdr(shprAdr);
                ptktData.setShprCty(rs.getString("SH2SHCTY"));
                ptktData.setShprSt(rs.getString("SH2SHST"));
                ptktData.setShprZip(rs.getString("SH2SHZIP"));
                ptktData.setShprCntr(rs.getString("SH2SHCNTR"));
                //ship from 
                frmAdr.add(rs.getString("SH2FRAD1"));
                frmAdr.add(rs.getString("SH2FRAD2"));
                ptktData.setShpFrmAdr(frmAdr);
                ptktData.setShpFrmNam(rs.getString("SH2FRNAM"));
                ptktData.setShpFrmCty(rs.getString("SH2FRCTY"));
                ptktData.setShpFrmSt(rs.getString("SH2FRST"));
                ptktData.setShpFrmZip(rs.getString("SH2FRZIP"));
                ptktData.setShpFrmCntr(rs.getString("SH2FRCNTR"));
                ptktData.setShpFrmPh(rs.getString("SH2FRPH"));
                // ship to
                ptktData.setShpToNam(rs.getString("SH2NAM"));
                toAdr.add(rs.getString("SH2AD1"));
                toAdr.add(rs.getString("SH2AD2"));
                ptktData.setShpToAdr(toAdr);
                ptktData.setShpToCty(rs.getString("SH2CTY"));
                ptktData.setShpToSt(rs.getString("SH2STT"));
                ptktData.setShpToZip(rs.getString("SH2ZIP"));
                ptktData.setShpToCntr("US");
                ptktData.setShpToAttn(rs.getString("SH2CON"));
                ptktData.setShpToPh(rs.getString("SH2PHN"));
                ptktData.setShpToEml(rs.getString("SH2EML"));

                // payer making same as shipper
                //ptktData.setPyrNam(ptktData.getShprName());
                // ptktData.setPyrAdr(ptktData.getShprAdr());
                // ptktData.setPyrCty(ptktData.getShpFrmAttn());
                // ptktData.setPyrSt(ptktData.getShprSt());
                // ptktData.setPyrZip(ptktData.getShprZip());
                // ptktData.setPyrCntr(ptktData.getShprCntr());
                // ptktData.setPyrShprNo(ptktData.getShprShprNo());
                // ptktData.setPyrPh(ptktData.getShprPh());
                ptktData.setPyrBillOpCd("40");      // 10 = prepaid; 30 = Bill to third Party; 40 = freight collect
                ptktData.setSvcCd(rs.getString("PKUPSVCD"));  // 03 = Ground ; 01 = next day air; 02 second day air

                ptktData.setRefPo(rs.getString("SH2REF2").substring(4));
                pkRef = ptktNoFmt.format(rs.getBigDecimal("ORORD"))
                        + "/"
                        + ptktNoFmt.format(rs.getBigDecimal("CHPCK#"));
                ptktData.setOrPo(rs.getString("ORPO#"));
                ptktData.setOrPo(ptktData.getRefPo());
                ptktData.setOrOty(rs.getString("OROTY"));
                ptktData.setRefPtkt(pkRef);
                ptktData.setCrtnId(rs.getBigDecimal("CHCAR#").toString());
                ptktData.setCrtn128(rs.getString("CHPUCC"));
                ptktData.setCmdDes("Apparel");
                ptktData.setCmdUom("LBS");
                ptktData.setCmdWgt(rs.getBigDecimal("CHCWGT"));
                ptktData.setCrtnCnt(rs.getInt("CHCNT"));
                //ptktData.setCrtnSeqNo(++crtnSeq);
                ptktData.setCrtnSeqNo(rs.getBigDecimal("CHCSEQ").intValue());
                ptktData.setTrkIdType(rs.getString("CHSVCD"));
                ptktData.setTrkId(rs.getString("CHTRK"));
                ptktData.setCmdPkgTypCd("CTN");

                ptktData.setPkgTyp("02");       // 02 = cusomer suppplied
                bxDim = rs.getString("BXDIM").substring(19);
//                System.out.println("dim = " + bxDim
//                        + "\t len =" + bxDim.substring(0, 5)
//                        + "\t wid =" + bxDim.substring(6, 11)
//                        + "\t ht = " + bxDim.substring(12, 17));
                bxLen = BigDecimal.valueOf(Double.valueOf(bxDim.substring(0, 5)));
                bxWid = BigDecimal.valueOf(Double.valueOf(bxDim.substring(6, 11)));
                bxHt = BigDecimal.valueOf(Double.valueOf(bxDim.substring(12, 17)));
//                ptktData.setDimLen(rs.getBigDecimal("EPDLNG").setScale(0, RoundingMode.HALF_UP).toString()); //23.5
//                ptktData.setDimWid(rs.getBigDecimal("EPDWID").setScale(0, RoundingMode.HALF_UP).toString());  // 15.25
//                ptktData.setDimHt(rs.getBigDecimal("EPDHGT").setScale(0, RoundingMode.HALF_UP).toString());     //9.5
                ptktData.setDimLen(bxLen.setScale(0, RoundingMode.HALF_UP).toString());
                ptktData.setDimWid(bxWid.setScale(0, RoundingMode.HALF_UP).toString());  // 15.25
                ptktData.setDimHt(bxHt.setScale(0, RoundingMode.HALF_UP).toString());     //9.5   
// ptktData.setCrtnId(rs.getBigDecimal("CHCAR#").toString());
                ptktData.setSoldTo(rs.getString("PKHSOL"));
                ptktData.setPtktNo(rs.getBigDecimal("CHPCK#").toString());
                ptktData.setOrdNo(rs.getBigDecimal("ORORD").toString());
                ptktData.setTerms(rs.getString("OR_TERM"));
                ptktData.setOrDat(rs.getBigDecimal("ORDTE"));
                ptktData.setOrStDat(rs.getBigDecimal("ORSDT"));
                ptktData.setOrEnDat(rs.getBigDecimal("ORTDT"));
                ptktData.setOrShVia(rs.getString("SVIAD"));
                ptktData.setWebOrd(rs.getString("ORREM"));

                // Sold to 
                ptktData.setSoNam(rs.getString("SH2SONAM"));
                ptktData.setSoAd1(rs.getString("SH2SOAD1"));
                ptktData.setSoAd2(rs.getString("SH2SOAD2"));
                ptktData.setSoAd3(rs.getString("SH2SOAD3"));
                ptktData.setSoCty(rs.getString("SH2SOCTY"));
                ptktData.setSoSt(rs.getString("SH2SOST"));
                ptktData.setSoZip(rs.getString("SH2SOZIP"));
                ptktData.setSoCntr(rs.getString("SH2SOCNTR"));
                ptktData.setSoEml(rs.getString("SH2SOEML"));
                ptktData.setSoPh(rs.getString("SH2SOPH")); 
                ptktData.setPkCrCd(rs.getString("PKCRCD"));
                orSi.add(rs.getString("ORSI1"));
                orSi.add(rs.getString("ORSI2"));
                orSi.add(rs.getString("ORSI3"));
                orSi.add(rs.getString("ORSI4"));
                orSi.add(rs.getString("ORSI5"));
                orSi.add(rs.getString("ORSI6"));

                if (orSi.contains("SC")) {
                    ptktData.setShpCmp(true);
                } else {
                    ptktData.setShpCmp(false);
                }
                ptktData.setResSrv(false);
//                if(orSi.contains("RS")) {
//                    ptktData.setResSrv(true); 
//                } else {
//                    ptktData.setResSrv(false);
//                }

                if (ptktData.getSvcCd().trim().length() != 0) {
                    if (rs.getString("SVIA@V").substring(35, 36).equals("I")) {
                        ptktData.setMailInno(true);
                        //ptktData.setSvcCd("M4");
                        //ptktData.setOrShVia("Mail Innovations Expedited");
                        if (ptktData.getCmdWgt().compareTo(BigDecimal.ONE) < 0) { // wight < 1 ?
                            ptktData.setCmdWgt(ptktData.getCmdWgt()
                                    .multiply(BigDecimal.valueOf(16)));
                            ptktData.setCmdUom("OZS");
                            ptktData.setPkgTyp("62");
                        } else {
                            ptktData.setPkgTyp("63");
                        }
                    } else {
                        ptktData.setMailInno(false);
                    }
                }

                if (rs.getString("SH2BTIN").equals("REC")) {
                    ptktData.setPyrShprNo(rs.getString("SH2PYRNO").trim());
                    ptktData.setBillOpt("3");
                } else {
                    ptktData.setBillOpt("S");       // S = shipper R= receiver 3 = third party
                }

                if (!rs.getString("CHTRK").trim().isEmpty()) {
                    if (rs.getString("CHCRCD").equals("F")) {
                        callFedexVoidShp(ptktData);
                    } else {
                    voidTrkUpsExp(rs.getString("CHTRK"));
                    }
                    VoidTracking.voidTracking(parms, rs.getString("CHTRK").trim());
                    }
                try {
                    wDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(
                            new DecimalFormat("00000000").format(rs.getBigDecimal("PKHPDT"))
                            + new DecimalFormat("000000").format(rs.getBigDecimal("PKHPTM")));
                } catch (ParseException ex) {
                    wDate = new Date();
                    AppParms.getLogger().log(Level.SEVERE, null, "Error parsing date for ptkt "
                            + ptktData.getPtktNo() + " " + ex);
                }

                ptktData.setPtktDat(wDate);
                lbl = " ";
                trkNo = " ";
                String lblMsg = " ";
                upsResponse = new UpsResponse(); // create empty ups Response
                if (!rs.getBigDecimal("CHTOTQ").equals(BigDecimal.ZERO)) {
                    if (!ptktData.getSvcCd().trim().isEmpty()) {
                        //upsResponse = CreateUpsLabel.createUpsLabel(parms, ptktData);
                        if (rs.getString("PKCRCD").equals("F")) {  //Ship via = Fedex
                            ptktData.setSvcCd(rs.getString("PKFXSVCD").trim());
                            upsResponse = callFedexShip(ptktData);
                        } else {
                            upsResponse = createUpsLabel.createUpsLabel(parms, ptktData);
                            upsResponse.setTrkTyp(ptktData.getSvcCd());
                        }
                        if (upsResponse.getLblRspStat().getErrStat().equals("1")) {
                            lbl = upsResponse.getLblLoc();
                            trkNo = upsResponse.getTrkNo();
                        }
                    }

                    if (!upsResponse.getLblRspStat().getErrStat().equals("1")) {
                        lblMsg = upsResponse.getLblRspStat().getErrMsg();
                    }
                    if (fstTkt) {
                        FileChannel srcChannel
                                = new FileInputStream(parms.getJasperLoc() + "pickTicket.jasper").getChannel();
                        FileChannel dstChannel
                                = new FileOutputStream(parms.getPdfLoc() + "pickTicket.jasper").getChannel();
                        dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
                        fstTkt = false;
                    }
                    boolean reprint = false;
                    if (rs.getString("CHRQPTFL").equals("R")) {
                        reprint = true;
                    }
                    //test 12/17
//                    upsResponse.setLblLoc("C:\\Users\\khatchik\\1643458_2097630_1.gif");
                    // end test 12/17
                    ptktPrint.print(ptktData, upsResponse, reprint);
                }
                 
                updCrtn(rs.getBigDecimal("CHPCK#"),
                        rs.getBigDecimal("CHCAR#"), trkNo,
                        lblMsg, ptktData.getPkCrCd(), upsResponse.getTrkTyp());

                if (upsResponse.getLblRspStat().getErrStat().equals("1")) {
                    updUpsExp(ptktData, upsResponse);
//                    updpxHdr(ptktData);
                }

            }

        } catch (SQLException ex) {
            AppParms.getLogger().log(Level.SEVERE, "Error retreiving PTKT Data. {0} SqlState ={1}", 
                    new Object[]{ex.getLocalizedMessage(), ex.getSQLState()});
            //Logger.getLogger(RequestedPickTickets.class.getName()).log(Level.SEVERE, null, ex);
            svcRtn.setFatalErr(true);
            svcRtn.setErrMsg("SQL Error retreiving Pick ticket Data. \n"
                    + ex.getLocalizedMessage());
        } catch (UnknownHostException ex) {
            AppParms.getLogger().log(Level.WARNING, "Unknown Host Exception. "
                    + "ptkt #" + svPck + " "
                    + ex.getLocalizedMessage());
            svcRtn.setFatalErr(false);
        } catch (Exception ex) {
            //Logger.getLogger(RequestedPickTickets.class.getName()).log(Level.SEVERE, null, ex);
            AppParms.getLogger().log(Level.SEVERE, "Error retreiving PTKT Data. "
                    + "ptkt #" + svPck + " "
                    + ex.getLocalizedMessage());
            svcRtn.setFatalErr(true);
            svcRtn.setErrMsg("Error processing Pick ticket Data. ptkt#" + svPck + " \n"
                    + ex.getLocalizedMessage());
        }

    }

    private UpsResponse callFedexShip(PtktData ptktData) {
        UpsResponse response = new UpsResponse();
        FxAppParms fxParms = setFxAppParms();

//        fxParms.setAccountNumber(parms.getFxAcctNo());
//        fxParms.setEndPoint(parms.getFxUrl());
//        fxParms.setFedexLblLoc(parms.getFxLblLoc());
//        fxParms.setKey(parms.getFxKey());
//        fxParms.setMeterNumber(parms.getFxMeterNo());
//        fxParms.setPassWord(parms.getFxPassWord());

        FedexShipClient fedexShipClient = new FedexShipClient(fxParms, AppParms.getLogger());
        FedexShipmentRequest fedexRequest = new FedexShipmentRequest();
        fedexRequest.setShipperComp(ptktData.getShpFrmNam());
        fedexRequest.setShipperName(ptktData.getShprDspName());
        fedexRequest.setShipperPhone(ptktData.getShprPh());
        for (String adr : ptktData.getShprAdr()) {
            if (!adr.trim().isEmpty()) {
                fedexRequest.getShipperAdr().add(adr);
            }
        }
        fedexRequest.setShipperCity(ptktData.getShprCty());
        fedexRequest.setShipperStat(ptktData.getShprSt());
        fedexRequest.setShipperZip(ptktData.getShprZip());
        fedexRequest.setShipperCountry(ptktData.getShprCntr());

        fedexRequest.setRecComp(ptktData.getShpToNam());
        fedexRequest.setRecName(ptktData.getShpToAttn());
        fedexRequest.setRecPhone(ptktData.getShpToPh());
        for (String adr : ptktData.getShpToAdr()) {
            if (!adr.trim().isEmpty()) {
                fedexRequest.getRecAdr().add(adr);
            }
        }
        fedexRequest.setRecCity(ptktData.getShpToCty());
        fedexRequest.setRecStat(ptktData.getShpToSt());
        fedexRequest.setRecZip(ptktData.getShpToZip());
        fedexRequest.setRecCountry(ptktData.getShpToCntr());

        fedexRequest.setRecRes(false);
        fedexRequest.setDropOffTyp(DropoffType.REGULAR_PICKUP);
        fedexRequest.setSrvTyp(ptktData.getSvcCd());
        fedexRequest.setPkgTyp("YOUR_PACKAGING");

        if (ptktData.getBillOpt().equals("3")) {
            fedexRequest.setPayorAcctNo(ptktData.getPyrShprNo());
            fedexRequest.setPayorCountry(ptktData.getPyrCntr());
            fedexRequest.setPaymentType(PaymentType.THIRD_PARTY);
        } else {
            fedexRequest.setPaymentType(PaymentType.SENDER);
            fedexRequest.setPayorAcctNo(ptktData.getShprShprNo());
        }
        fedexRequest.setPkgWeight(FedexShipClient.fmtWeight(ptktData.getCmdWgt(), WeightUnits.LB));
        fedexRequest.setPkgDim(FedexShipClient.fmtDim(ptktData.getDimLen(), ptktData.getDimWid(), ptktData.getDimHt(), LinearUnits.IN));
        fedexRequest.getCustomerReference().add(FedexShipClient.fmtCustomerReference(CustomerReferenceType.P_O_NUMBER, ptktData.getOrPo()));
        fedexRequest.getCustomerReference().add(FedexShipClient.fmtCustomerReference(CustomerReferenceType.CUSTOMER_REFERENCE, ptktData.getRefPtkt()));
        fedexRequest.setPtkt(ptktData.getPtktNo());
        fedexRequest.setCrtnId(ptktData.getCrtnId());
        
        fedexRequest.setRecPhone("9XXXXXXX21");
        FedexResponse fedexResponse = FedexShipClient.requestShipment(fedexRequest);

        response.setBlgWgt(fedexResponse.getBlgWgt());
        response.setLblLoc(fedexResponse.getLblLoc());
        response.setNgsChrg(fedexResponse.getNgsChrg());
        response.setSvcChrg(fedexResponse.getSvcChrg());
        response.setTotChrg(fedexResponse.getTotChrg());
        response.setTpxChrg(fedexResponse.getTpxChrg());
        response.setTrkNo(fedexResponse.getTrkNo());
        response.setTrkTyp(fedexResponse.getTrkTyp());
        LblRspStat lblRspStat = new LblRspStat();

        if (fedexResponse.getMsgSeverity().equals(NotificationSeverityType._SUCCESS )||
                fedexResponse.getMsgSeverity().equals(NotificationSeverityType._WARNING)  ||
                fedexResponse.getMsgSeverity().equals(NotificationSeverityType._NOTE)) {
            lblRspStat.setErrStat("1");
        } else {
            lblRspStat.setErrCode(fedexResponse.getMsgSeverity());
            lblRspStat.setErrMsg(fedexResponse.getMessage());
        }
        response.setLblRspStat(lblRspStat);

        return response;
    }
    private void callFedexVoidShp(PtktData ptktData) {
        FedexResponse response = new FedexResponse();
        FxAppParms fxParms = setFxAppParms(); 
        FedexShipClient fedexShipClient = new FedexShipClient(fxParms, AppParms.getLogger());
        FedexShipmentRequest fedexRequest = new FedexShipmentRequest();
        fedexRequest.setTrkId(ptktData.getTrkId());
        fedexRequest.setTrkIdType(ptktData.getTrkIdType());
        response = FedexShipClient.requestDeleteShipment(fedexRequest);
        
    }
    private FxAppParms setFxAppParms() {
        FxAppParms fxParms = new FxAppParms();

        fxParms.setAccountNumber(parms.getFxAcctNo());
        fxParms.setEndPoint(parms.getFxUrl());
        fxParms.setFedexLblLoc(parms.getFxLblLoc());
        fxParms.setKey(parms.getFxKey());
        fxParms.setMeterNumber(parms.getFxMeterNo());
        fxParms.setPassWord(parms.getFxPassWord());
        
        return fxParms;
        
    }

    private void updCrtn(BigDecimal ptkt, BigDecimal crtn, String trkNo,
            String rspMsg, String pkCrCd, String svCd) {
        BigDecimal curDt_cymd;
        BigDecimal curTm;
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        DateFormat sdf_tm = new SimpleDateFormat("HHmmss");
        Date curDt = new Date();
        curDt_cymd = new BigDecimal(sdf.format(curDt));
        curTm = new BigDecimal(sdf_tm.format(curDt));

        String qry = "Update " + dtaLib + ".ccarph a "
                + "set CHRQPTFL = ? , CHTRK =?, CHPTPRDT = ? , CHPTPRMT = ? "
                + ",CHUERR=?, CHCRCD=?, CHSVCD=? "
                + "where CHPCK# = ? "
                + "and CHCAR# = ?";
        try (PreparedStatement pStmt = con.prepareStatement(qry);) {
            pStmt.setString(1, "N");
            pStmt.setString(2, trkNo);
            pStmt.setBigDecimal(3, curDt_cymd);
            pStmt.setBigDecimal(4, curTm);
            if (rspMsg.length() > 100) {
                pStmt.setString(5, rspMsg.substring(0, 100));
            } else {
                pStmt.setString(5, rspMsg);     // UPS error  
            }
            pStmt.setString(6,pkCrCd);
            pStmt.setString(7, svCd);
            pStmt.setBigDecimal(8, ptkt);
            pStmt.setBigDecimal(9, crtn);
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            AppParms.getLogger().log(Level.SEVERE, "Error updating carton detail. ptkt ="
                    + ptkt + " Carton=" + crtn + " Trk#=" + trkNo + " Msg=" + rspMsg
                    + ex.getLocalizedMessage() + " SqlState =" + ex.getSQLState());
            //Logger.getLogger(RequestedPickTickets.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void updUpsExp(PtktData ptktData, UpsResponse upsResponse) {
        String qry = "Insert into " + parms.getUpsExpLib() + ".UPSEXP "
                + "(PCKTKT,SH2TRK,SH2WGT,SH2CHG,SH2REF1,SH2REF2,SH2REF3,SHINTPFL,SHVOID) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(qry);) {
            pstmt.setBigDecimal(1, new BigDecimal(ptktData.getPtktNo()));
            pstmt.setString(2, upsResponse.getTrkNo());
            pstmt.setBigDecimal(3, ptktData.getCmdWgt());
            pstmt.setBigDecimal(4, upsResponse.getTotChrg());
            pstmt.setString(5, ptktData.getRefPtkt());
            pstmt.setString(6, "PO: " + ptktData.getRefPo());
            pstmt.setString(7, ptktData.getSoldTo());
            pstmt.setString(8, "Y");
            pstmt.setString(9, "N");
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(RequestedPickTickets.class.getName()).log(Level.SEVERE, null, ex);
            AppParms.getLogger().log(Level.SEVERE, "Error writing to UPSEXP. Ptkt =" + ptktData.getPtktNo()
                    + " Carton=" + ptktData.getCrtnId()
                    + " Trk number = " + upsResponse.getTrkNo()
                    + " Weight=" + ptktData.getCmdWgt() + " totChrg=" + upsResponse.getTotChrg()
                    + ex.getLocalizedMessage() + " SqlState =" + ex.getSQLState());
        }
    }
//    private void updpxHdr(PtktData ptktData) {
//        String qry = "update " + dtaLib + ".pkhdrp a set PKOCRCD = ? " +
//                "where PKHPCK = ? and pkhOrd = ?";
//        
//        try (PreparedStatement pstmt = con.prepareStatement(qry)) 
//        { 
//            pstmt.setString(1, ptktData.getPkCrCd());
//            pstmt.setBigDecimal(2, new BigDecimal(ptktData.getPtktNo()));
//            pstmt.setBigDecimal(3, new BigDecimal(ptktData.getOrdNo()));
//            pstmt.execute();
//            
//        } catch(SQLException ex) {
//            AppParms.getLogger().log(Level.SEVERE, "Error updating PKHDRP File. Ptkt ={0} Carton={1}{2} SqlState ={3}", 
//                    new Object[]{ptktData.getPtktNo(), ptktData.getCrtnId(), ex.getLocalizedMessage(), ex.getSQLState()});
//        }
//    }

    private void voidTrkUpsExp(String trk) {
        String qry = "update " + parms.getUpsExpLib() + ".UPSEXP "
                + "set SHVOID = ? "
                + "where SH2TRK = ?";
        try (PreparedStatement pStmt = con.prepareStatement(qry);) {
            pStmt.setString(1, "Y");
            pStmt.setString(2, trk);
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            AppParms.getLogger().log(Level.SEVERE, "Error voiding upsexp tkr#=" + trk + " "
                    + ex.getLocalizedMessage() + " SqlState =" + ex.getSQLState());
            // Logger.getLogger(RequestedPickTickets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void voidCartons() {
        String qry = "Select CLPCK#, CLTRK, CLCAR# "
                + "from " + dtaLib + ".CCARDEL "
                + "where clpwrh = '" + parms.getWhse() + "'";
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(qry)) {
            while (rs.next()) {
                voidTrkUpsExp(rs.getString("CLTRK"));
                VoidTracking.voidTracking(parms, rs.getString("CLTRK"));
                delVoidCarton(rs.getBigDecimal("CLCAR#"));
            }
        } catch (SQLException ex) {
            AppParms.getLogger().log(Level.SEVERE, "Error retrieving deleting Ctns "
                    + ex.getLocalizedMessage() + " SqlState =" + ex.getSQLState());
            //Logger.getLogger(RequestedPickTickets.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void delVoidCarton(BigDecimal carNo) {
        String qry = "delete from " + dtaLib + ".CCARDEL "
                + "where clcar# = ?";
        try (PreparedStatement pstmt = con.prepareCall(qry)) {
            pstmt.setBigDecimal(1, carNo);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            AppParms.getLogger().log(Level.SEVERE, "Error deleting voided carton ccardel rec "
                    + ex.getLocalizedMessage() + " SqlState =" + ex.getSQLState());
            //Logger.getLogger(RequestedPickTickets.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public SvcRtn getSvcRtn() {
        return svcRtn;
    }

}
