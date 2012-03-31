/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author ota
 */
@RemoteServiceRelativePath("pacepicsdbr")
public interface PacEpicsDBRService extends RemoteService {
    String initChannels(String URI);
    PacEpicsDBR getDBR(String pvName);
    void putDBR(String pvName, String pvValue);
}
