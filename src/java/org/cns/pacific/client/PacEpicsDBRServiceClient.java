/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HandlesAllMouseEvents;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.Double;
import java.lang.Integer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Example class using the GWTService service.
 *
 * @author ota
 */
public class PacEpicsDBRServiceClient extends VerticalPanel {
    public class EpicsPvCtrlText extends TextBox {
        final AsyncCallback callback = new AsyncCallback() {
            @Override
            public void onFailure(Throwable caught) {
                setText("Communication Failed");
            }

            @Override
            public void onSuccess(Object result) {

            }
        };
        final AsyncCallback<PacEpicsDBR> callbackDBR = new AsyncCallback<PacEpicsDBR>() {
            @Override
            public void onFailure(Throwable caught) {
                setText("Communication Failed");
            }

            @Override
            public void onSuccess(PacEpicsDBR result) {
                setText(result.getValue());
            }
        };
        EpicsPvCtrlText(final String pvName) {
            setWidth("80px");
            setAlignment(TextAlignment.RIGHT);
            getService().getDBR(pvName,callbackDBR);
            addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    getService().putDBR(pvName, event.getValue(), callback);
                }
            });
        }
    }
    
    
    //@TODO iPad から操作できていない。
    public class EpicsPvButton extends Button {
        private String pvName;
        final AsyncCallback callback = new AsyncCallback() {
            @Override
            public void onFailure(Throwable caught) {
            }
            @Override
            public void onSuccess(Object result) {
            }
        };
        EpicsPvButton (final String pvName) {
            setText("SET");
            addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    getService().putDBR(pvName, "1", callback);
                }
            });
        }
    }
    
    // @TODO この仕様では動かないように見えている。。。
    public class EpicsPvStatusBox extends Label {
        private String pvName;
        private Label retval = new Label();
        final String goodStateStyle = "goodState";
        final String alertStateStyle = "alertState";
        final String badStateStyle = "badState";
        final AsyncCallback<PacEpicsDBR> callback = new AsyncCallback<PacEpicsDBR>() {
            @Override
            public void onFailure(Throwable caught) {
                    removeStyleDependentName(goodStateStyle);
                    removeStyleDependentName(alertStateStyle);
                    addStyleDependentName(badStateStyle);
            }
            @Override
            public void onSuccess(PacEpicsDBR result) {
                String str = result.getValue();
                str = str.trim();
                if (str.equals("0.0")) {
                    removeStyleDependentName(alertStateStyle);
                    removeStyleDependentName(badStateStyle);
                    addStyleDependentName(goodStateStyle);
                } else {
                    removeStyleDependentName(goodStateStyle);
                    removeStyleDependentName(badStateStyle);
                    addStyleDependentName(alertStateStyle);
                }
            }
        };
        
        EpicsPvStatusBox(final String pvName) {
            setWidth("30px");
            // setText(getStylePrimaryName());
            setText("　");
            Timer t = new Timer() {
                public void run() {
                    getService().getDBR(pvName,callback);
                }
            };
            t.scheduleRepeating(2000);                
        }
                
    }
    public class EpicsPV  extends Label{
        // @TODO callback を工夫すればモニター系のスーパークラスになれそう。
        private String pvName;
        private String lastValue;
        final AsyncCallback<PacEpicsDBR> callbackDBR = new AsyncCallback<PacEpicsDBR>() {
            public void onSuccess(PacEpicsDBR result) {
                    setText(result.getValue());
            }
            public void onFailure(Throwable caught) {
                setText("Cannot get pv" + pvName);
            }
        };   

        EpicsPV(final String pvName){
            lastValue = "";
            setWidth("80px");
            setAutoHorizontalAlignment(ALIGN_CONTENT_END);
            Timer t = new Timer() {
                public void run() {
                    getService().getDBR(pvName,callbackDBR);
                }
            };
            t.scheduleRepeating(2000);
        }
    }
    
    public class MWDCPanel extends HorizontalPanel {
        MWDCPanel (String name, String pvBaseNameHV, FlexTable table) {
            final String[] pvHVlist = {
                ":hvMonVolCalc",
                ":hvMonCurCalc",
                ":hvSupCur",

            };
            int nRow = table.getRowCount();
            int nCol = 0;
            Label nameLabel = new Label(name);
            nameLabel.setWidth("65px");
            add(nameLabel);
            table.setWidget(nRow, nCol++, nameLabel);
            for (int i=1;i<=2;i++) {
                final Label monVol = new EpicsPV(pvBaseNameHV+i+":hvMonVolCalc");
                final Label supVol = new EpicsPV(pvBaseNameHV+i+":hvSupCur");
                final Label monCur = new EpicsPV(pvBaseNameHV+i+":hvMonCurCalc");
                final Label changed = new EpicsPvStatusBox(pvBaseNameHV+i+":hvChangeFinishSelect");
                final TextBox input = new EpicsPvCtrlText(pvBaseNameHV+i+":hvSup");
                final Button setBt =  new EpicsPvButton(pvBaseNameHV+i+":hvSetBt");
                table.setWidget(nRow, nCol++, monVol);
                table.setWidget(nRow, nCol++, monCur);
                table.setWidget(nRow, nCol++, supVol);
                table.setWidget(nRow, nCol++, changed);
                table.setWidget(nRow, nCol++, input);
                table.setWidget(nRow, nCol++, setBt);
                /*
                add(monVol);
                add(monCur);
                add(supVol);
                add(changed);
                add(input);
                add(setBt);                // add(new EpicsPV(pvBaseNameHV+i+":hvSup"));
                 * *
                 */
                Timer t = new Timer() {
                    @Override
                    public void run() {
                        String mon = monVol.getText().trim();
                        String sup = supVol.getText().trim();
                        if (Double.parseDouble(sup) * 0.9 > Double.parseDouble(mon)) {
                            monVol.addStyleDependentName("badState");
                        } else {
                            monVol.removeStyleDependentName("badState");
                        }
                    }
                };
                t.scheduleRepeating(2000);
            }
        }
    }
    
    
        // Create an asynchronous callback to handle the result.    
    public PacEpicsDBRServiceClient() {
        final TextBox uriInput = new TextBox();
        Button reload = new Button("reload");
       
        reload.addClickHandler(new ClickHandler (){
            @Override
            public void onClick(ClickEvent event) {
                final Label loading = new Label("loding...");
                add(loading);
                getService().initChannels(uriInput.getText(), new AsyncCallback<String> () {

                    @Override
                    public void onFailure(Throwable caught) {
                        loading.setText("Communication falied");
                    }

                    @Override
                    public void onSuccess(String result) {
                        remove(loading);
                    }
                    
                });
            }
        });
        
        reload.click();
        Timer t = new Timer() {
            @Override
            public void run() {
            }
        };
        t.schedule(1000);
        
        HorizontalPanel panel = new HorizontalPanel();
        panel.add(uriInput);
        panel.add(reload);
        add(panel);
        
        String[][] a = {
            {"DC31","DC31HV"},
            {"DC32","DC32HV"},
            {"DC71","FH71HV"},
            {"DC72","FH72HV"},
            {"DC91","FH91HV"},
            {"DCX1","FHX1HV"},
            {"DCX0","FHX0HV"},
            {"DCX2","FHX2HV"},
            {"S21","S21HV"},
            {"S22","S22HV"}
        };

        String[] headerSet = {
            "Device",
            "Vol1(V)",
            "Cur1(uA)",
            "Set1(V)",
            "",
            "Input1(V)",
            "",
            "Vol2(V)",
            "Cur2(uA)",
            "Set2(V)",
            "",
            "Input2(V)",
            "",
            "Pressure(kPa)",
            "Gas Flow(cc/min)"
        };
        FlexTable table = new FlexTable();
        for (int i = 0; i < headerSet.length; i++) {
            table.setText(0,i,headerSet[i]);
        }
        VerticalPanel vpanel = new VerticalPanel();
        add(vpanel);
        vpanel.add(table);
        for (int i=0; i<a.length; i++) {
            vpanel.add(new MWDCPanel(a[i][0],a[i][1],table));
        }
        Label dc31Pres = new EpicsPV("DCF6:gasPressure");
        Label dc71Pres = new EpicsPV("DCFH71:gasPressure");
        Label dc91Pres = new EpicsPV("DCFH91:gasPressure");
        Label dcX1Pres = new EpicsPV("DCFHX1:gasPressure");
        Label FPDS2Pres = new EpicsPV("FPDS2:gasPressure");

        Label dc31Flow = new EpicsPV("DCF6:gasFlowMonitor");
        Label dc71Flow = new EpicsPV("DCFH71:gasFlowMonitor");
        Label dc91Flow = new EpicsPV("DCFH91:gasFlowMonitor");
        Label dcX1Flow = new EpicsPV("DCFHX1:gasFlowMonitor");
        Label FPDS2Flow = new EpicsPV("FPDS2:gasFlowMonitor");

        table.setWidget(1,13,dc31Pres);
        table.setWidget(3,13,dc71Pres);
        table.setWidget(5,13,dc91Pres);
        table.setWidget(7,13,dcX1Pres);
        table.setWidget(9,13,FPDS2Pres);

        table.setWidget(1,14,dc31Flow);
        table.setWidget(3,14,dc71Flow);
        table.setWidget(5,14,dc91Flow);
        table.setWidget(7,14,dcX1Flow);
        table.setWidget(9,14,FPDS2Flow);
        
        table.getRowFormatter().setStyleName(0, "my-th");
        String trStyles[] = {"my-tr-even","my-tr-odd"};
        for (int i=1;i<table.getRowCount(); i++) {
            table.getRowFormatter().setStyleName(i,trStyles[i%2]);
        }

        uriInput.setWidth("200px");
        uriInput.setText("/usr/share/tomcat7/monitorChannel.xml");
        add(table);
    }
    
    public static PacEpicsDBRServiceAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of the interface. The cast is always safe because the
        // generated proxy implements the asynchronous interface automatically.

        return GWT.create(PacEpicsDBRService.class);
    }
}
