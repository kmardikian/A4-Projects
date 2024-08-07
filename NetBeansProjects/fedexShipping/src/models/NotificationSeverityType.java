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
public class NotificationSeverityType {

    public NotificationSeverityType(String _ERROR1) {
    }
    public static final java.lang.String _ERROR = "ERROR";
    public static final java.lang.String _FAILURE = "FAILURE";
    public static final java.lang.String _NOTE = "NOTE";
    public static final java.lang.String _SUCCESS = "SUCCESS";
    public static final java.lang.String _WARNING = "WARNING";
    public static final NotificationSeverityType ERROR = new NotificationSeverityType(_ERROR);
    public static final NotificationSeverityType FAILURE = new NotificationSeverityType(_FAILURE);
    public static final NotificationSeverityType NOTE = new NotificationSeverityType(_NOTE);
    public static final NotificationSeverityType SUCCESS = new NotificationSeverityType(_SUCCESS);
    public static final NotificationSeverityType WARNING = new NotificationSeverityType(_WARNING);
    
}
