/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Khatchik
 */
public class CustomerReference {
    
    private CustomerReferenceType customerReferenceType;
    private String value;

    public CustomerReference() {
    }

    
    public CustomerReference(CustomerReferenceType customerReferenceType, String value) {
        this.customerReferenceType = customerReferenceType;
        this.value = value;
    }

    public CustomerReferenceType getCustomerReferenceType() {
        return customerReferenceType;
    }

    public void setCustomerReferenceType(CustomerReferenceType customerReferenceType) {
        this.customerReferenceType = customerReferenceType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
    
}
