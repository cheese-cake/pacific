/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.server.epics;

import gov.aps.jca.CAException;
import gov.aps.jca.CAStatus;
import gov.aps.jca.Channel;
import gov.aps.jca.Monitor;
import gov.aps.jca.dbr.DBRType;
import gov.aps.jca.event.MonitorEvent;
import gov.aps.jca.event.MonitorListener;
import java.lang.reflect.Array;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ota
 */
public class EpicsChannel {
    // hold channel to put the new value
    private Channel channel;
    private Monitor monitor;
    private Object  valueObj;
    private String  value = "";
    private String lastValue = "";
    
    @Override
    protected void finalize () throws CAException {
        destroy();
    }
    
    public void destroy() throws CAException {
        monitor.clear();
        channel.destroy();
        channel = null;
        monitor = null;
    }
    
    
    public Channel getChannel() {
        return channel;
    }
    
    public void setChannel(Channel channel) throws CAException {
        this.channel = channel;
        MonitorListenerImpl listener = new MonitorListenerImpl();
        listener.addEpicsChannel(this);
        this.monitor = channel.addMonitor(Monitor.VALUE,listener);
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public Object getValueObj() {
        return valueObj;
    }

    public void setValueObj(Object valueObj) throws CAException {
        this.valueObj = valueObj;
        DBRType type = channel.getFieldType();
        if (type.isDOUBLE()) {
            double val = Array.getDouble(valueObj, 0);
            this.value = String.format("%7.1f", val);
        } else if (type.isINT()) {
            int val = Array.getInt(valueObj, 0);
            this.value = String.format("%d",val);
        } else if (type.isFLOAT()) {
            float val = Array.getFloat(valueObj, 0);
            this.value = String.format("%7.1f",val);
        } else {
           // others 
        }
    }

    public synchronized void putValueObj(String valueObj) throws Throwable {
        DBRType type = channel.getFieldType();
        if (type.isDOUBLE()) {
            double val = Double.parseDouble(valueObj);
            channel.put(val);
        } else if (type.isINT()) {
            channel.put(Integer.parseInt(valueObj));
        } else if (type.isFLOAT()) {
            channel.put(Float.parseFloat(valueObj));
        } else if (type.isENUM()){
            channel.put(Byte.parseByte(valueObj));

           // others 
        }
        EpicsChannelLoader.getInstance().flushIO();
    }
    
    public String getValue(){ 
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    private static class MonitorListenerImpl implements MonitorListener {
        
        @Override
        public void monitorChanged(MonitorEvent event) {
            if (event.getStatus() == CAStatus.NORMAL) {
                try {
                    epicsChannel.setValueObj(event.getDBR().getValue());
                } catch (CAException ex) {
                    Logger.getLogger(EpicsChannel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
        
        public void addEpicsChannel(EpicsChannel epicsChannel) {
            this.epicsChannel = epicsChannel;
        }
        
        
        private EpicsChannel epicsChannel = null;
    }
}
