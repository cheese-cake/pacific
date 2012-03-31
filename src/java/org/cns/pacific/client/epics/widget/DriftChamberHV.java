/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.client.epics.widget;

import com.google.gwt.user.client.ui.HorizontalPanel;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.cns.pacific.client.epics.EpicsServiceMonitor;

/**
 *
 * @author ota
 */
public class DriftChamberHV extends HorizontalPanel {
    DriftChamberHV (HashMap<String,String> pvMap) throws ParserConfigurationException {
       DocumentBuilder builder = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
  
        EpicsServiceMonitor supcur = new EpicsServiceMonitor(pvMap.get("SupCur"));
        EpicsServiceMonitor monvol = new EpicsServiceMonitor(pvMap.get("MonVolCalc"));
        EpicsServiceMonitor moncur = new EpicsServiceMonitor(pvMap.get("MonCurCalc"));
        
        add(supcur);
        add(monvol);
        add(moncur);
    }
}
