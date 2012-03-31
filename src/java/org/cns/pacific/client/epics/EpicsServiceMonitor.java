/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.client.epics;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;

/**
 *
 * @author ota
 */
public class EpicsServiceMonitor extends Label {
    private String pvName;
    final AsyncCallback<String> callback = new AsyncCallback<String>() {
        public void onSuccess(String result) {
            setText(result);
        }
        public void onFailure(Throwable caught) {
            setText(pvName);
        }
    };   
    public EpicsServiceMonitor (final String pvName) {
        this.pvName = pvName;
        setWidth("100 px");
        Timer t = new Timer() {
            public void run() {
                getService().getMonitorValue(pvName,callback);
            }
        };
        t.scheduleRepeating(1000);
    }
    
    public static EpicsServiceAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of the interface. The cast is always safe because the
        // generated proxy implements the asynchronous interface automatically.

        return GWT.create(EpicsService.class);
    }
}
