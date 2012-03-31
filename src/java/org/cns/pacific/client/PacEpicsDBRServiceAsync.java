/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author ota
 */
public interface PacEpicsDBRServiceAsync {
    void initChannels(String URI,AsyncCallback<String> callback);
    void getDBR(String pvName, AsyncCallback<PacEpicsDBR> callback);
    void putDBR(String pvName,String pvObject, AsyncCallback callback);
}
