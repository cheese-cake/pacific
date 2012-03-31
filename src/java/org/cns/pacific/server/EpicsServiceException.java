/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cns.pacific.server;

/**
 *
 * @author ota
 */
public class EpicsServiceException extends Exception {
    public EpicsServiceException() {}
    public EpicsServiceException (Throwable ex) { super(ex); }
    public EpicsServiceException (String ex) { super(ex); }
}
