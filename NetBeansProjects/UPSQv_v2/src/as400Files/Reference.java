/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as400Files;

import net.sf.json.JSONObject;

/**
 *
 * @author Khatchik
 */
public class Reference {

    private String number;
    private String code;
    private String value;

    public Reference(JSONObject jsonRef) {
        number = " ";
        code = " ";
        value = " ";
         if (jsonRef.containsKey("Number")) {
            setNumber(jsonRef.getString("Number"));
        }
        if (jsonRef.containsKey("Code")) {
            setCode(jsonRef.getString("Code"));
        }
        if (jsonRef.containsKey("Value")) {
            setValue(jsonRef.getString("Value"));
        }

    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}
