/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Main entry point.
 *
 * @author ota
 */
public class MainEntryPoint implements EntryPoint {


    /** 
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {
    }

    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    @Override
    public void onModuleLoad() {
        /*
        final Label label = new Label("Hello, GWT!!!");
        final Button button = new Button("Click me!");
        final Label value = new Label();

        button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                label.setVisible(!label.isVisible());
            }
        });
        RootPanel.get().add(button);
        RootPanel.get().add(label);
         * 
         */
        // RootPanel.get().add(value);
        RootPanel.get().add(new PacEpicsDBRServiceClient());
                /*
        Timer t = new Timer() {
            public void run() {
                getDbr(value,"FH71HV1:hvMonVolCalc");
            }
        };
         * *
         
        t.scheduleRepeating(5000);
         * 
         */
        // getDbr(value,"psld:SHQ1:cnvcur");
        // getDbr(value,"FH71HV1:hvMonVolCalc");

    }
    // フラグを渡してチェックするなど排他制御が必要になる。もしくは外側で呼び出しを禁止するか。。。
    // モニターにして値は更新しつづけるという手もある。複数のクライアントからの要求には答えやすそう。
    // 更新は単に現在の値を読みに行くだけにしたら分かりやすい。
    /*
    public void getDbr (final Label val, String pvName) {
        PacEpicsDBRServiceAsync service = (PacEpicsDBRServiceAsync)GWT.create(PacEpicsDBRService.class);
        ServiceDefTarget endPoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "pacepicsdbr";
        endPoint.setServiceEntryPoint(moduleRelativeURL);
        service.getDBR(pvName,new AsyncCallback<PacEpicsDBR>(){
            @Override
           public void onFailure(Throwable caught) {
               GWT.log("Can't access channel",caught);
           }
           
            @Override
           public void onSuccess(PacEpicsDBR result) {
               val.setText(result.getValue());
           }
                   
        });
    }
     *
     */
}
