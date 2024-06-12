/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktFilter;

import java.util.EventObject;

/**
 *
 * @author Khatchik
 */
public class ChangeFilterEvent extends EventObject {
    private PtktFilter ptktFilter;
    
    public ChangeFilterEvent(Object source, PtktFilter ptktFilter) {
        super(source);
        this.ptktFilter=ptktFilter;
    }

    public PtktFilter getPtktFilter() {
        return ptktFilter;
    }
    
    
}
