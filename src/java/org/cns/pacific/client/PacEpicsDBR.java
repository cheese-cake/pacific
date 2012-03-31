/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author ota
 */
public class PacEpicsDBR implements IsSerializable {
    String fValue;
    public PacEpicsDBR() {}
    public PacEpicsDBR(String value) {
        setValue(value);
    }
    
    public String getValue() {
        return fValue;
    }
    public void setValue(String value) {
        this.fValue = value;
    }
}
