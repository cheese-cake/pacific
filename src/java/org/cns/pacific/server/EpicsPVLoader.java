/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ota
 */
public class EpicsPVLoader {

    public static EpicsPVLoader getInstance() {
        return _loader;
    }
    
    public String[] getPVNames(String URI) throws ParserConfigurationException, SAXException, IOException {
        load(URI);
        NodeList list = doc.getElementsByTagName("pv");
        String[] ret = new String[list.getLength()];
        for (int i=0; i<list.getLength(); i++) {
            ret[i] = list.item(i).getTextContent();
        }
        // throw new UnsupportedOperationException("Not yet implemented")；
        return ret;
    }
    
    private void load(String URI) throws SAXException, IOException, ParserConfigurationException {
        currentFile = URI;
        DocumentBuilder builder = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        doc = builder.parse(URI);
    }
    private static Document doc = null;
    private static String currentFile = null;
    private static final EpicsPVLoader _loader = new EpicsPVLoader();
}
