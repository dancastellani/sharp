/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sharp.util;

import br.danielcastellani.sharp.exception.ShapException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author DanCastellani
 */
public class FileUtils {

    public static void copyFile(File originalFile, File propertiesFile) throws ShapException {
        try {
            InputStream in = new FileInputStream(originalFile);
            //For Append the file.
            //OutputStream out = new FileOutputStream(f2,true);
            //For Overwrite the file.
            OutputStream out = new FileOutputStream(propertiesFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException ex) {
            throw new ShapException(ex.getMessage() + " in the specified directory.");
        } catch (IOException e) {
            throw new ShapException(e);
        }
    }
    
    public static boolean delete(File file){
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                FileUtils.delete(subFile);
            }
        }
        return file.delete();
    }
}
