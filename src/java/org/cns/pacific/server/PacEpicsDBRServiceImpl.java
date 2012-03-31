/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.JCALibrary;
import gov.aps.jca.TimeoutException;
import gov.aps.jca.dbr.DBR;
import gov.aps.jca.dbr.DBRType;
import gov.aps.jca.dbr.STRING;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.cns.pacific.client.PacEpicsDBR;
import org.cns.pacific.client.PacEpicsDBRService;
import org.cns.pacific.server.epics.EpicsChannelContainer;
import org.cns.pacific.server.epics.EpicsChannelLoader;
import org.xml.sax.SAXException;

/**
 *
 * @author ota
 */
public class PacEpicsDBRServiceImpl extends RemoteServiceServlet implements PacEpicsDBRService {
    private static final long serialVertionUID = 1L;
    // monitor を singleton で作成しておいて、それがもつ値をこのクラスが参照するように改造する。
    @Override
    public String initChannels(String URI) {
        try {
            EpicsChannelLoader.getInstance().reload(URI);
            return "Created";
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PacEpicsDBRServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PacEpicsDBRServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CAException ex) {
            Logger.getLogger(PacEpicsDBRServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(PacEpicsDBRServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(PacEpicsDBRServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PacEpicsDBRServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Failed";
    }
    
    public void putDBR(String pvName, String pvValue) {
        try {
            EpicsChannelContainer.getInstance().putValueObject(pvName, pvValue);
        } catch (Throwable ex) {
            Logger.getLogger(PacEpicsDBRServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return;
    }
    
    @Override
    public PacEpicsDBR getDBR(String pvName) {
        EpicsChannelContainer container = EpicsChannelContainer.getInstance();
        String value = container.getValue(pvName);
        PacEpicsDBR pacEpicsDBR = new PacEpicsDBR();
        pacEpicsDBR.setValue(value);
        /*
        PacEpicsDBR pacEpicsDBR = new PacEpicsDBR();
        try {
            JCALibrary jca = JCALibrary.getInstance();
            fContext = jca.createContext(JCALibrary.CHANNEL_ACCESS_JAVA);
            Channel ch;
            DBR dbr = null;
            ch = fContext.createChannel(pvName);
            fContext.pendIO(5.);
            dbr = ch.get(DBRType.STRING,1);
            fContext.pendIO(5.);
            pacEpicsDBR.setValue(((STRING)dbr).getStringValue()[0]);
            ch.destroy();
            fContext.destroy();
        } catch (Exception ex) {
            Logger.getLogger(PacEpicsDBRServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 

         */
        return pacEpicsDBR;
    }
}
