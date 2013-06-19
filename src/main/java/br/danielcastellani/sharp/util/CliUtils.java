/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sharp.util;

import br.danielcastellani.sharp.exception.ShapException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author DanCastellani
 */
public class CliUtils {

    public static String exec(String command, boolean verbose) {
        try {
            StringBuffer outputString = new StringBuffer();
            println(verbose, "Executando:" + command);
            Process p = Runtime.getRuntime().exec(command);

            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = bf.readLine()) != null) {
                if (verbose) {
                    System.out.println(line);
                }
                outputString.append(line).append("\n");
            }
            println(true, "Done. -<:" + command);
            return outputString.toString();
        } catch (IOException ex) {
            throw new ShapException(ex);
        }
    }

    public static void println(boolean verbose, String str) {
        print(verbose, str + "\n");
    }

    public static void print(boolean verbose, String str) {
        if (verbose) {
            System.out.println(str);
        }
    }
}
