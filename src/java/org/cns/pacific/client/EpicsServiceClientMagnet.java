/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.client;

import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ClickListener;

/**
 * Example class using the EpicsServiceClientMagnet service.
 *
 * @author ota
 */
public class EpicsServiceClientMagnet extends VerticalPanel {

    private Label lblServerReply = new Label();
    private TextBox txtUserInput = new TextBox();
    private Button btnSend = new Button("Send to server");
    
    public EpicsServiceClientMagnet() {
        
    }
    
    public static PacEpicsDBR getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of the interface. The cast is always safe because the
        // generated proxy implements the asynchronous interface automatically.

        return GWT.create(PacEpicsDBR.class);
    }
}
