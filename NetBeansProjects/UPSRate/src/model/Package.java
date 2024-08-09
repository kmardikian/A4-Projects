/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Khatchik
 */
public class Package {
    String pkgTypCd;        //package type code
    String pkgDesc;         // description
    String dimUom;          // dimension Unit of measure in, cm
    String dimUomDes;       // Dimension Unit of measure description
    String length;
    String width;
    String height;
    String weightUom;       // weight UOM LBS, KGS
    String weightUomDes;    // weight UOM description. 
    String weight;          // Weight. 

    public Package(String pkgTypCd, String pkgDesc, String dimUom, String dimUomDes, String length, String width, String height, String weightUom, String weightUomDes, String weight) {
        this.pkgTypCd = pkgTypCd;
        this.pkgDesc = pkgDesc;
        this.dimUom = dimUom;
        this.dimUomDes = dimUomDes;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weightUom = weightUom;
        this.weightUomDes = weightUomDes;
        this.weight = weight;
    }

    public Package() {
    }

    public String getPkgTypCd() {
        return pkgTypCd;
    }

    public void setPkgTypCd(String pkgTypCd) {
        this.pkgTypCd = pkgTypCd;
    }

    public String getPkgDesc() {
        return pkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc = pkgDesc;
    }

    public String getDimUom() {
        return dimUom;
    }

    public void setDimUom(String dimUom) {
        this.dimUom = dimUom;
    }

    public String getDimUomDes() {
        return dimUomDes;
    }

    public void setDimUomDes(String dimUomDes) {
        this.dimUomDes = dimUomDes;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeightUom() {
        return weightUom;
    }

    public void setWeightUom(String weightUom) {
        this.weightUom = weightUom;
    }

    public String getWeightUomDes() {
        return weightUomDes;
    }

    public void setWeightUomDes(String weightUomDes) {
        this.weightUomDes = weightUomDes;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    
    
    
            
    
}
