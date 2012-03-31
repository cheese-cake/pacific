/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.server.epics;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import org.cns.pacific.client.epics.EpicsService;
import org.cns.pacific.server.EpicsServiceException;
import org.cns.pacific.server.epics.EpicsChannelContainer;
import org.cns.pacific.server.epics.EpicsChannelLoader;

/**
 *
 * @author ota
 */
public class EpicsServiceImpl extends RemoteServiceServlet implements EpicsService {

    @Override
    public String getMonitorValue(String pvName) {
        // Do something interesting with 's' here on the server.
        return EpicsChannelContainer.getInstance().getValue(pvName);
    }
    
    @Override
    public void initChannels(String URI) throws EpicsServiceException {
        try {
            EpicsChannelLoader.getInstance().reload(URI);
        } catch (Throwable e) {
            throw new EpicsServiceException("initChannels() : "+e);
        }
    }
    
}
