/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import models.LinearUnits;

/**
 *
 * @author Khatchik
 */
public class Dimensions {
    
    private int length;
    private int width;
    private int height;
    private LinearUnits units;

    public Dimensions(int length, int width, int height, LinearUnits units) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.units = units;
    }

    public Dimensions() {
    }
    

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public LinearUnits getUnits() {
        return units;
    }

    public void setUnits(LinearUnits units) {
        this.units = units;
    }
    
    
}
