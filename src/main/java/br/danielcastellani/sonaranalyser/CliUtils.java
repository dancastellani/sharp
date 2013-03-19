/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser;

import br.danielcastellani.sonaranalyser.exception.ShapException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author DanCastellani
 */
public class CliUtils {

    static void exec(String command) {
        try {
            System.out.println("Executando:" + command);
            Process p = Runtime.getRuntime().exec(command);
//            PrintStream ps = new PrintStream(p.getOutputStream());
//            ps.println();

            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = bf.readLine()) != null) {
                System.out.println("    " + line);
            }
            System.out.println("Done. -<:" + command);
        } catch (IOException ex) {
            throw new ShapException(ex);
        }
    }
}
