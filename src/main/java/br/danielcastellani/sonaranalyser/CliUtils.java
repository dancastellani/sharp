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

    static String exec(String command) {
        try {
            StringBuffer outputString = new StringBuffer();
            System.out.println("Executando:" + command);
            Process p = Runtime.getRuntime().exec(command);
//            PrintStream ps = new PrintStream(p.getOutputStream());
//            ps.println();

            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
                outputString.append(line).append("\n");
            }
            System.out.println("Done. -<:" + command);
            return outputString.toString();
        } catch (IOException ex) {
            throw new ShapException(ex);
        }
    }
}
