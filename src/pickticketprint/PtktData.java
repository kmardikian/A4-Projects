/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pickticketprint;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Khatchik
 */
public class PtktData {
    
    // shipper
    private String shprName;
    private String shprAttn;
    private String shprDspName;
    private String shprPh;
    private String shprShprNo;
    private ArrayList<String> shprAdr = new ArrayList<>();  
    private String shprCty;
    private String shprSt;
    private String shprZip;
    private String shprCntr;
    // Ship From 
    private String shpFrmNam="";
    private ArrayList<String> shpFrmAdr = new ArrayList<>();  
    private String shpFrmCty="";
    private String shpFrmSt="";
    private String shpFrmZip="";
    private String shpFrmCntr="";
    private String shpFrmAttn="";
    private String shpFrmPh="";
    private String shpFrmExt="";
    private String shpFrmEml="";
    private String shprNo="";
    // Ship to 
    private String shpToNam="";
    private ArrayList<String> shpToAdr = new ArrayList<>(); 
    private String shpToCty="";
    private String shpToSt="";
    private String shpToZip;
    private String shpToCntr;
    private String shpToAttn;
    private String shpToPh;
    private String shpToExt;
    private String shpToEml;
    // Payment information 
    private String pyrNam;
    private ArrayList<String> pyrAdr = new ArrayList<>(); 
    private String pyrCty;
    private String pyrSt;
    private String pyrZip;
    private String pyrCntr;
    private String pyrShprNo;
    private String pyrAttn;
    private String pyrPh;
    private String pyrExt;
    private String pyrEml;
    // payer information payer billing option
    private String pyrBillOpCd;
    // shipment service 
    private String svcCd;
    private int hdlu1Qty;   // quantity of handling unit
    private String hdlu1TypCd;
    
    // commadit 
    private String cmdId;
    private String cmdDes;
    private String cmdUom;
    private BigDecimal cmdWgt;
    
    private int cmdNPcs;   // number of pieces in handling unit????
    private String cmdPkgTypCd;
    private String CmdfrtCls;    // freight class   
    
    // Reference Number 
    private String refPo;
    private String refPtkt;
    
    //private ArrayList<PkgReference> reference = new ArrayList<>();
    
    // handling units 
    private String hndUntQty;
    private String hndUntTyp;
    private String hndUntDimUom;
    private String hndUntDimLen;
    private String hndUntDimWdth;
    private String hndUntDimHt;
    
    private String billOpt;   // "S" shipper "R" Receiver 3 Third party 
    private String pkgTyp;    // 02 = customer supplied.
    private String dimLen;
    private String dimWid;
    private String dimHt;
    private String crtnId;  // pick ticket carton ID
    private String soldTo;
    private String ptktNo;
    private String ordNo;
    private String terms;
    private BigDecimal orDat; 
    private BigDecimal orStDat;
    private BigDecimal orEnDat;
    private String orShVia;
    private String webOrd;
    private String soNam;
    private String soAd1;
    private String soAd2;
    private String soAd3;
    private String soCty;
    private String soSt;
    private String soZip;
    private String soCntr;
    private String soEml;
    private String soPh;
    private String orPo;
    private String orOty;
    private Date ptktDat;
    private String crtn128;
    private boolean shpCmp;  // ship complete
    private boolean resSrv;  // residental service 
    private int crtnCnt;     // total number of cartons for pick ticket
    private int crtnSeqNo;  // Carton # 1 of n
    private boolean mailInno=false;  // mail innovation flag 
    private String fxSvcTyp;
    private String pkCrCd;      // pick ticket carrier code.
    private String trkIdType;
    private String trkId;               // tracking number for deleting 

    public String getShprName() {
        if (shprName ==null ) {
            return "";
        } else {
        return shprName;
        }
    }

    public void setShprName(String shprName) {
        this.shprName = shprName;
    }

    public String getShprAttn() {
        if (shprAttn == null ) {
            return "";
        } else {
        return shprAttn;
        }
    }

    public void setShprAttn(String shprAttn) {
        this.shprAttn = shprAttn;
    }

    public String getShprDspName() {
        return shprDspName;
    }

    public void setShprDspName(String shprDspName) {
        this.shprDspName = shprDspName;
    }

    public String getShprPh() {
        return shprPh;
    }

    public void setShprPh(String shprPh) {
        this.shprPh = shprPh;
    }

    public String getShprShprNo() {
        return shprShprNo;
    }

    public void setShprShprNo(String shprShprNo) {
        this.shprShprNo = shprShprNo;
    }

    public ArrayList<String> getShprAdr() {
        return shprAdr;
    }

    public void setShprAdr(ArrayList<String> shprAdr) {
        this.shprAdr = shprAdr;
    }

    public String getShprCty() {
        return shprCty;
    }

    public void setShprCty(String shprCty) {
        this.shprCty = shprCty;
    }

    public String getShprSt() {
        return shprSt;
    }

    public void setShprSt(String shprSt) {
        this.shprSt = shprSt;
    }

    public String getShprZip() {
        return shprZip;
    }

    public void setShprZip(String shprZip) {
        this.shprZip = shprZip;
    }

    public String getShprCntr() {
        return shprCntr;
    }

    public void setShprCntr(String shprCntr) {
        this.shprCntr = shprCntr;
    }
    
    

    public String getShpFrmNam() {
        return shpFrmNam;
    }

    public void setShpFrmNam(String shpFrmNam) {
        this.shpFrmNam = shpFrmNam;
    }

    public ArrayList<String> getShpFrmAdr() {
        return shpFrmAdr;
    }

    public void setShpFrmAdr(ArrayList<String> shpFrmAdr) {
        this.shpFrmAdr = shpFrmAdr;
    }

    public String getShpFrmCty() {
        return shpFrmCty;
    }

    public void setShpFrmCty(String shpFrmCty) {
        this.shpFrmCty = shpFrmCty;
    }

    public String getShpFrmSt() {
        return shpFrmSt;
    }

    public void setShpFrmSt(String shpFrmSt) {
        this.shpFrmSt = shpFrmSt;
    }

    public String getShpFrmZip() {
        return shpFrmZip;
    }

    public void setShpFrmZip(String shpFrmZip) {
        this.shpFrmZip = shpFrmZip;
    }

    public String getShpFrmCntr() {
        return shpFrmCntr;
    }

    public void setShpFrmCntr(String shpFrmCntr) {
        this.shpFrmCntr = shpFrmCntr;
    }

    public String getShpFrmAttn() {
        return shpFrmAttn;
    }

    public void setShpFrmAttn(String shpFrmAttn) {
        this.shpFrmAttn = shpFrmAttn;
    }

    public String getShpFrmPh() {
        return shpFrmPh;
    }

    public void setShpFrmPh(String shpFrmPh) {
        this.shpFrmPh = shpFrmPh;
    }

    public String getShpFrmExt() {
        return shpFrmExt;
    }

    public void setShpFrmExt(String shpFrmExt) {
        this.shpFrmExt = shpFrmExt;
    }

    public String getShpFrmEml() {
        return shpFrmEml;
    }

    public void setShpFrmEml(String shpFrmEml) {
        this.shpFrmEml = shpFrmEml;
    }

    public String getShprNo() {
        return shprNo;
    }

    public void setShprNo(String shprNo) {
        this.shprNo = shprNo;
    }

    public String getShpToNam() {
        return shpToNam;
    }

    public void setShpToNam(String shpToNam) {
        this.shpToNam = shpToNam;
    }

    public ArrayList<String> getShpToAdr() {
        return shpToAdr;
    }

    public void setShpToAdr(ArrayList<String> shpToAdr) {
        this.shpToAdr = shpToAdr;
    }

    public String getShpToCty() {
        return shpToCty;
    }

    public void setShpToCty(String shpToCty) {
        this.shpToCty = shpToCty;
    }

    public String getShpToSt() {
        return shpToSt;
    }

    public void setShpToSt(String shpToSt) {
        this.shpToSt = shpToSt;
    }

    public String getShpToZip() {
        return shpToZip;
    }

    public void setShpToZip(String shpToZip) {
        this.shpToZip = shpToZip;
    }

    public String getShpToCntr() {
        return shpToCntr;
    }

    public void setShpToCntr(String shpToCntr) {
        this.shpToCntr = shpToCntr;
    }

    public String getShpToAttn() {
        return shpToAttn;
    }

    public void setShpToAttn(String shpToAttn) {
        this.shpToAttn = shpToAttn;
    }

    public String getShpToPh() {
        return shpToPh;
    }

    public void setShpToPh(String shpToPh) {
        this.shpToPh = shpToPh;
    }

    public String getShpToExt() {
        return shpToExt;
    }

    public void setShpToExt(String shpToExt) {
        this.shpToExt = shpToExt;
    }

    public String getShpToEml() {
        return shpToEml;
    }

    public void setShpToEml(String shpToEml) {
        this.shpToEml = shpToEml;
    }

    public String getPyrNam() {
        return pyrNam;
    }

    public void setPyrNam(String pyrNam) {
        this.pyrNam = pyrNam;
    }

    public ArrayList<String> getPyrAdr() {
        return pyrAdr;
    }

    public void setPyrAdr(ArrayList<String> pyrAdr) {
        this.pyrAdr = pyrAdr;
    }

    public String getPyrCty() {
        return pyrCty;
    }

    public void setPyrCty(String pyrCty) {
        this.pyrCty = pyrCty;
    }

    public String getPyrSt() {
        return pyrSt;
    }

    public void setPyrSt(String pyrSt) {
        this.pyrSt = pyrSt;
    }

    public String getPyrZip() {
        return pyrZip;
    }

    public void setPyrZip(String pyrZip) {
        this.pyrZip = pyrZip;
    }

    public String getPyrCntr() {
        return pyrCntr;
    }

    public void setPyrCntr(String pyrCntr) {
        this.pyrCntr = pyrCntr;
    }

    public String getPyrShprNo() {
        return pyrShprNo;
    }

    public void setPyrShprNo(String pyrShprNo) {
        this.pyrShprNo = pyrShprNo;
    }

    public String getPyrAttn() {
        return pyrAttn;
    }

    public void setPyrAttn(String pyrAttn) {
        this.pyrAttn = pyrAttn;
    }

    public String getPyrPh() {
        return pyrPh;
    }

    public void setPyrPh(String pyrPh) {
        this.pyrPh = pyrPh;
    }

    public String getPyrExt() {
        return pyrExt;
    }

    public void setPyrExt(String pyrExt) {
        this.pyrExt = pyrExt;
    }

    public String getPyrEml() {
        return pyrEml;
    }

    public void setPyrEml(String pyrEml) {
        this.pyrEml = pyrEml;
    }

    public String getPyrBillOpCd() {
        return pyrBillOpCd;
    }

    public void setPyrBillOpCd(String pyrBillOpCd) {
        this.pyrBillOpCd = pyrBillOpCd;
    }

    public String getSvcCd() {
        return svcCd;
    }

    public void setSvcCd(String svcCd) {
        this.svcCd = svcCd;
    }

    public int getHdlu1Qty() {
        return hdlu1Qty;
    }

    public void setHdlu1Qty(int hdlu1Qty) {
        this.hdlu1Qty = hdlu1Qty;
    }

    public String getHdlu1TypCd() {
        return hdlu1TypCd;
    }

    public void setHdlu1TypCd(String hdlu1TypCd) {
        this.hdlu1TypCd = hdlu1TypCd;
    }

    public String getCmdId() {
        return cmdId;
    }

    public void setCmdId(String cmdId) {
        this.cmdId = cmdId;
    }

    public String getCmdDes() {
        return cmdDes;
    }

    public void setCmdDes(String cmdDes) {
        this.cmdDes = cmdDes;
    }

    public String getCmdUom() {
        return cmdUom;
    }

    public void setCmdUom(String cmdUom) {
        this.cmdUom = cmdUom;
    }

    public BigDecimal getCmdWgt() {
        return cmdWgt;
    }

    public void setCmdWgt(BigDecimal cmdWgt) {
        this.cmdWgt = cmdWgt;
    }

    public int getCmdNPcs() {
        return cmdNPcs;
    }

    public void setCmdNPcs(int cmdNPcs) {
        this.cmdNPcs = cmdNPcs;
    }

    public String getCmdPkgTypCd() {
        return cmdPkgTypCd;
    }

    public void setCmdPkgTypCd(String cmdPkgTypCd) {
        this.cmdPkgTypCd = cmdPkgTypCd;
    }

    public String getCmdfrtCls() {
        return CmdfrtCls;
    }

    public void setCmdfrtCls(String CmdfrtCls) {
        this.CmdfrtCls = CmdfrtCls;
    }

   // public ArrayList<PkgReference> getReference() {
   //     return reference;
   // }

   // public void setReference(ArrayList<PkgReference> reference) {
   //     this.reference = reference;
   // }

    public String getHndUntQty() {
        return hndUntQty;
    }

    public void setHndUntQty(String hndUntQty) {
        this.hndUntQty = hndUntQty;
    }

    public String getHndUntTyp() {
        return hndUntTyp;
    }

    public void setHndUntTyp(String hndUntTyp) {
        this.hndUntTyp = hndUntTyp;
    }

    public String getHndUntDimUom() {
        return hndUntDimUom;
    }

    public void setHndUntDimUom(String hndUntDimUom) {
        this.hndUntDimUom = hndUntDimUom;
    }

    public String getHndUntDimLen() {
        return hndUntDimLen;
    }

    public void setHndUntDimLen(String hndUntDimLen) {
        this.hndUntDimLen = hndUntDimLen;
    }

    public String getHndUntDimWdth() {
        return hndUntDimWdth;
    }

    public void setHndUntDimWdth(String hndUntDimWdth) {
        this.hndUntDimWdth = hndUntDimWdth;
    }

    public String getHndUntDimHt() {
        return hndUntDimHt;
    }

    public void setHndUntDimHt(String hndUntDimHt) {
        this.hndUntDimHt = hndUntDimHt;
    }

    public String getRefPo() {
        return refPo;
    }

    public void setRefPo(String refPo) {
        this.refPo = refPo;
    }

    public String getRefPtkt() {
        return refPtkt;
    }

    public void setRefPtkt(String refPtkt) {
        this.refPtkt = refPtkt;
    }

    public String getBillOpt() {
        return billOpt;
    }

    public void setBillOpt(String billOpt) {
        this.billOpt = billOpt;
    }

    public String getDimLen() {
        return dimLen;
    }

    public void setDimLen(String dimLen) {
        this.dimLen = dimLen;
    }

    public String getDimWid() {
        return dimWid;
    }

    public void setDimWid(String dimWid) {
        this.dimWid = dimWid;
    }

    public String getDimHt() {
        return dimHt;
    }

    public void setDimHt(String dimHt) {
        this.dimHt = dimHt;
    }

    public String getPkgTyp() {
        return pkgTyp;
    }

    public void setPkgTyp(String pkgTyp) {
        this.pkgTyp = pkgTyp;
    }

    public String getCrtnId() {
        return crtnId;
    }

    public void setCrtnId(String crtnId) {
        this.crtnId = crtnId;
    }

    public String getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(String soldTo) {
        this.soldTo = soldTo;
    }

    public String getPtktNo() {
        return ptktNo;
    }

    public void setPtktNo(String ptktNo) {
        this.ptktNo = ptktNo;
    }

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public BigDecimal getOrDat() {
        return orDat;
    }

    public void setOrDat(BigDecimal orDat) {
        this.orDat = orDat;
    }

    public BigDecimal getOrStDat() {
        return orStDat;
    }

    public void setOrStDat(BigDecimal orStDat) {
        this.orStDat = orStDat;
    }

    public BigDecimal getOrEnDat() {
        return orEnDat;
    }

    public void setOrEnDat(BigDecimal orEnDat) {
        this.orEnDat = orEnDat;
    }

    public String getOrShVia() {
        return orShVia;
    }

    public void setOrShVia(String orShVia) {
        this.orShVia = orShVia;
    }

    public String getWebOrd() {
        return webOrd;
    }

    public void setWebOrd(String webOrd) {
        this.webOrd = webOrd;
    }

    public String getSoNam() {
        return soNam;
    }

    public void setSoNam(String soNam) {
        this.soNam = soNam;
    }

    public String getSoAd1() {
        return soAd1;
    }

    public void setSoAd1(String soAd1) {
        this.soAd1 = soAd1;
    }

    public String getSoAd2() {
        return soAd2;
    }

    public void setSoAd2(String soAd2) {
        this.soAd2 = soAd2;
    }

    public String getSoAd3() {
        return soAd3;
    }

    public void setSoAd3(String soAd3) {
        this.soAd3 = soAd3;
    }

    public String getSoCty() {
        return soCty;
    }

    public void setSoCty(String soCty) {
        this.soCty = soCty;
    }

    public String getSoSt() {
        return soSt;
    }

    public void setSoSt(String soSt) {
        this.soSt = soSt;
    }

    public String getSoZip() {
        return soZip;
    }

    public void setSoZip(String soZip) {
        this.soZip = soZip;
    }

    public String getSoCntr() {
        return soCntr;
    }

    public void setSoCntr(String soCntr) {
        this.soCntr = soCntr;
    }

    public String getSoEml() {
        return soEml;
    }

    public void setSoEml(String soEml) {
        this.soEml = soEml;
    }

    public String getSoPh() {
        return soPh;
    }

    public void setSoPh(String soPh) {
        this.soPh = soPh;
    }

    public String getOrPo() {
        return orPo;
    }

    public void setOrPo(String orPo) {
        this.orPo = orPo;
    }

    public Date getPtktDat() {
        return ptktDat;
    }

    public void setPtktDat(Date ptktDat) {
        this.ptktDat = ptktDat;
    }

    public String getCrtn128() {
        return crtn128;
    }

    public void setCrtn128(String crtn128) {
        this.crtn128 = crtn128;
    }

    public boolean isShpCmp() {
        return shpCmp;
    }

    public void setShpCmp(boolean shpCmp) {
        this.shpCmp = shpCmp;
    }

    public boolean isResSrv() {
        return resSrv;
    }

    public void setResSrv(boolean resSrv) {
        this.resSrv = resSrv;
    }

    public int getCrtnCnt() {
        return crtnCnt;
    }

    public void setCrtnCnt(int crtnCnt) {
        this.crtnCnt = crtnCnt;
    }

    public int getCrtnSeqNo() {
        return crtnSeqNo;
    }

    public void setCrtnSeqNo(int crtnSeqNo) {
        this.crtnSeqNo = crtnSeqNo;
    }

    public boolean isMailInno() {
        return mailInno;
    }

    public void setMailInno(boolean mailInno) {
        this.mailInno = mailInno;
    }

    public String getOrOty() {
        return orOty;
    }

    public void setOrOty(String orOty) {
        this.orOty = orOty;
    }

    public String getFxSvcTyp() {
        return fxSvcTyp;
    }

    public void setFxSvcTyp(String fxSvcTyp) {
        this.fxSvcTyp = fxSvcTyp;
    }

    public String getPkCrCd() {
        return pkCrCd;
    }

    public void setPkCrCd(String pkCrCd) {
        this.pkCrCd = pkCrCd;
    }

    public String getTrkIdType() {
        return trkIdType;
    }

    public void setTrkIdType(String trkIdType) {
        this.trkIdType = trkIdType;
    }

    public String getTrkId() {
        return trkId;
    }

    public void setTrkId(String trkId) {
        this.trkId = trkId;
    }
    
    
}
