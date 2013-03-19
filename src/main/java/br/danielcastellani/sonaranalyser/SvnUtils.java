/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.danielcastellani.sonaranalyser;

import br.danielcastellani.sonaranalyser.exception.ShapException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author DanCastellani
 */
public class SvnUtils {

    static void checkout(Properties props, int revisionNumber) {
        final String svnHome = props.getProperty(Main.SVN_HOME);
        final String projectUrl = props.getProperty(Main.PROJECT_URL);
        final String destinationFolder = props.getProperty(Main.DESTINATION_FOLDER);

        final String command = svnHome + "\\bin\\svn checkout " + projectUrl + " " + destinationFolder;
        CliUtils.exec(command);
    }


}
