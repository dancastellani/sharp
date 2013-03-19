/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser.exception;

import java.io.IOException;

/**
 *
 * @author DanCastellani
 */
public class SonarAnalyserException extends RuntimeException {

    public SonarAnalyserException(Throwable cause) {
        super(cause);
    }
}
