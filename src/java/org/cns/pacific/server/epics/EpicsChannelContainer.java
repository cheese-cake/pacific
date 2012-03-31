package org.cns.pacific.server.epics;

import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.JCALibrary;
import gov.aps.jca.Monitor;
import gov.aps.jca.TimeoutException;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ota
 */
public class EpicsChannelContainer {

    private EpicsChannelContainer() {
            container = new HashMap<String,EpicsChannel>();
    }
    
    public static EpicsChannelContainer getInstance() {
        return EpicsChannelContainerHolder.INSTANCE;
    }
    
    private static class EpicsChannelContainerHolder {

        private static final EpicsChannelContainer INSTANCE = new EpicsChannelContainer();
    }
    
    public void add(EpicsChannel channel) {
        String pvName = channel.getChannel().getName();
        if (container.containsKey(pvName)) return;
        container.put(pvName, channel);
    }
    
    public String getValue(String pvName) {
        if (container.get(pvName) != null)  return container.get(pvName).getValue();
        return pvName+" not found";
    }
    
    public void setValueObject(String pvName, Object pvValue) throws CAException {
        container.get(pvName).setValueObj(pvValue);
    }

    public void putValueObject(String pvName, String pvValue) throws Throwable {
        container.get(pvName).putValueObj(pvValue);
    }
    
    public void clear() throws CAException {
        Iterator it = container.keySet().iterator();
        while (it.hasNext()){
            String pvName = (String)it.next();
            EpicsChannel ec = (EpicsChannel) container.get(pvName);
            ec.destroy();
        }
        container.clear();
    }
    
    private HashMap<String,EpicsChannel> container;
}
