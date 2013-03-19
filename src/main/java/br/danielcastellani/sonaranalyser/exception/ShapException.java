/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.danielcastellani.sonaranalyser.exception;

/**
 *
 * @author DanCastellani
 */
public class ShapException extends RuntimeException {

    /**
     * Creates a new instance of <code>ShapException</code> without detail message.
     */
    public ShapException() {
    }


    /**
     * Constructs an instance of <code>ShapException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ShapException(String msg) {
        super(msg);
    }

    public ShapException(Throwable cause) {
        super(cause);
    }
}
