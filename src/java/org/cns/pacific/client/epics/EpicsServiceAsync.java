/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.client.epics;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.cns.pacific.server.EpicsServiceException;

/**
 *
 * @author ota
 */
public interface EpicsServiceAsync {

    public void getMonitorValue(String s, AsyncCallback<String> callback);
    public void initChannels(String URI,AsyncCallback callback) throws EpicsServiceException;
}
