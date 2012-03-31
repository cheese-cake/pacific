/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.server.epics;

import com.google.gwt.dev.asm.tree.IntInsnNode;
import gov.aps.jca.CAException;
import gov.aps.jca.Channel;
import gov.aps.jca.Context;
import gov.aps.jca.JCALibrary;
import gov.aps.jca.Monitor;
import gov.aps.jca.TimeoutException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import javax.xml.parsers.ParserConfigurationException;
import org.cns.pacific.server.EpicsPVLoader;
import org.cns.pacific.server.epics.EpicsChannelLoader;
import org.xml.sax.SAXException;

/**
 *
 * @author ota
 */
public class EpicsChannelLoader {

    public static EpicsChannelLoader getInstance() throws CAException {
        EpicsChannelLoaderHolder.INSTANCE.initializeContext();
        return EpicsChannelLoaderHolder.INSTANCE;
    }
    
    private static class EpicsChannelLoaderHolder {
        private static final EpicsChannelLoader INSTANCE = new EpicsChannelLoader ();
    }
    
    @Override
    protected void finalize () throws Throwable {
        destroy();
        destroyContext();
        super.finalize();
    }

    private synchronized void initializeContext() throws CAException {
        if (fIsInitialized) return;
        JCALibrary jca = JCALibrary.getInstance();
        fContext = jca.createContext(JCALibrary.CHANNEL_ACCESS_JAVA);
        fIsInitialized = true;
    }
    
    private void destroyContext() throws CAException {
        fIsInitialized = false;
        fContext.destroy();
    }
    
    private void destroy () throws CAException {
        EpicsChannelContainer.getInstance().clear();
        fContext.flushIO();
    }
    
    public void reload(String URI) throws CAException, FileNotFoundException, IOException, TimeoutException, ParserConfigurationException, SAXException {
        destroy();
        load(URI);
    }
    
    public void load (String URI) throws FileNotFoundException, IOException, CAException, TimeoutException, ParserConfigurationException, SAXException {
        EpicsChannelContainer container = EpicsChannelContainer.getInstance();
        String[] pvNames = EpicsPVLoader.getInstance().getPVNames(URI);
        // @TODO 設定ファイルを読み込んでチャンネルを作る。
        for (int i = 0; i < pvNames.length; i++) {
            Channel channel = null;
            channel = fContext.createChannel(pvNames[i]);
            fContext.pendIO(5.);
            EpicsChannel epicsChannel = new EpicsChannel();
            epicsChannel.setChannel(channel);
            container.add(epicsChannel);
        }
        fContext.flushIO();
    }
    
    public void flushIO() throws CAException {
        fContext.flushIO();
    }
    
    private static Context fContext = null;
    private static Boolean fIsInitialized = false;
}
