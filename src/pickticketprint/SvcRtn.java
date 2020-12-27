/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pickticketprint;

/**
 *
 * @author Khatchik
 */
public class SvcRtn {

    boolean fatalErr = false;
    String errMsg;

    public boolean isFatalErr() {
        return fatalErr;
    }

    public void setFatalErr(boolean fatalErr) {
        this.fatalErr = fatalErr;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
