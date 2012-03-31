/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.server.epics;

/**
 * bean for XML parse
 * @author ota
 */
public class EpicsChannelSet {
    private String pvName;
    private String name;
    private String unit;
    
    public String getPvName() {
        return pvName;
    }

    public void setPvName(String pvName) {
        this.pvName = pvName;
    }
    
}
