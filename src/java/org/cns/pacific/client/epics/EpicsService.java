/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.client.epics;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.cns.pacific.server.EpicsServiceException;

/**
 *
 * @author ota
 */
@RemoteServiceRelativePath("epicsservice")
public interface EpicsService extends RemoteService {

    public String getMonitorValue(String pvName);
    public void initChannels(String URI) throws EpicsServiceException;
}
